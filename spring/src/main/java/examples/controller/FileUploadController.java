package examples.controller;

import examples.service.StorageFileNotFoundException;
import examples.service.StorageService;
import org.apache.catalina.core.ApplicationPart;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;
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
    @RequestMapping(value = "/uploadFiles1",method = RequestMethod.POST)
    public String handleFileUpload1(@RequestParam("files")MultipartFile[] files, RedirectAttributes redirect) {
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

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    @RequestMapping(value = "/uploadFiles",method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request) throws IOException, ServletException {

        Part p = request.getPart("files");
        ApplicationPart part = (ApplicationPart) p;
        String fname = part.getSubmittedFileName();
        int path_idx=fname.lastIndexOf("\\")+1;
        String path= request.getServletContext().getRealPath("./");
        System.out.println(path);
        String fname2=fname.substring(path_idx,fname.length());
        p.write(path+"/upload/"+fname2);

//        if (!ServletFileUpload.isMultipartContent(request)) {
//            return "";
//        }
//
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        factory.setSizeThreshold(MEMORY_THRESHOLD);
//        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        // 设置最大文件上传值
//        upload.setFileSizeMax(MAX_FILE_SIZE);
//
//        // 设置最大请求值 (包含文件和表单数据)
//        upload.setSizeMax(MAX_REQUEST_SIZE);
//
//        // 中文处理
//        upload.setHeaderEncoding("UTF-8");
//        // 构造临时路径来存储上传的文件
//        // 这个路径相对当前应用的目录
//        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + "upload";
//        // 如果目录不存在则创建
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        try {
//            // 解析请求的内容提取文件数据
//            @SuppressWarnings("unchecked")
//            List<FileItem> formItems = upload.parseRequest(request);
//
//            if (formItems != null && formItems.size() > 0) {
//                // 迭代表单数据
//                for (FileItem item : formItems) {
//                    // 处理不在表单中的字段
//                    if (!item.isFormField()) {
//                        String fileName = new File(item.getName()).getName();
//                        String filePath = uploadPath + File.separator + fileName;
//                        File storeFile = new File(filePath);
//                        // 在控制台输出文件的上传路径
//                        System.out.println(filePath);
//                        // 保存文件到硬盘
//                        item.write(storeFile);
//                        request.setAttribute("message",
//                            "文件上传成功!");
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            request.setAttribute("message",
//                "错误信息: " + ex.getMessage());
//        }
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
