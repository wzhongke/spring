package examples.controller;

import examples.service.StorageFileNotFoundException;
import examples.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    @Qualifier("fileSystemStorageService")
    private StorageService storageService;

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                "serveFile", path.getFileName().toString()).build().toString())
            .collect(Collectors.toList()));

        return "uploadForm";
    }


    @RequestMapping("/fileUpload")
    public String uploadFiles(){
        System.out.println("uploadFiles");
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /* redirect: 浏览器的url也会改变 */
    @RequestMapping(value = "/uploadFiles",method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("files")MultipartFile[] files, RedirectAttributes redirect) {
        StringBuilder buffer = new StringBuilder();
        for(MultipartFile file : files) {
            storageService.store(file);
            buffer.append(file.getOriginalFilename());
        }
        /* flash attribute 在重定向之间存储数据， 将message参数带到重定向之后的响应中 */
        redirect.addFlashAttribute("message",
                "You successfully uploaded " + buffer.toString() + "!");
        return "redirect:/files/";
    }

    /* redirect: 浏览器的url也会改变 */
    @RequestMapping(value = "/uploadFilesAsy",method = RequestMethod.POST)
    public Callable<String> handleFileUploadAsy(@RequestParam("files") final MultipartFile[] files) {
       return () -> {
           for (MultipartFile file : files) {
               storageService.store(file);
           }
           return "redirect:/files/";
       };
    }

    @PostMapping("/uploadImage")
    @ResponseBody
    public String saveImage(@RequestParam("imageName") String imageName, @RequestParam("imageData") String imageData) {
        System.out.println("------------------ " + imageName + "--------------------------");
        if (null == imageData || imageData.length() < 100) {
            return "{fail: too small data}";
        }
        imageData = imageData.substring(30);
        try {
            imageData = URLDecoder.decode(imageData,"UTF-8");
            byte[] data = decode(imageData);
            int len = data.length;
            int len2 = imageData.length();
            storageService.saveImage(data, imageName);
        } catch (IOException e) {
            e.printStackTrace();
            return "{fail: " + e.getMessage() + "}";
        }
        return "{success:success}";
    }

    /* redirect: 浏览器的url也会改变 */
    @RequestMapping(value = "/uploadFilesAsy",method = RequestMethod.GET)
    @ResponseBody
    public DeferredResult<String> handleFileUploadDef() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        // Save the deferredResult somewhere..
        storageService.handleDif(deferredResult);
        return deferredResult;
    }

    @RequestMapping(value="/deleteFile", method = RequestMethod.GET)
    @ResponseBody
    public String deleteFile(@SessionAttribute("username") String username, @RequestParam("filename") String filename){
        return storageService.deleteFile(username, filename);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private byte[] decode(String imageData) throws IOException{
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] data = decoder.decodeBuffer(imageData);
        for(int i=0;i<data.length;++i) {
            if(data[i]<0) {
                //调整异常数据
                data[i]+=256;
            }
        }
        return data;
    }

}
