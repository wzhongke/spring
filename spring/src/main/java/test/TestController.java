package test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class TestController {

	@RequestMapping("/fileUpload")
	public String uploadFiles(){
		System.out.println("uploadFiles");
		return "uploadForm";
	}

	@RequestMapping(value = "/uploadFiles",method = RequestMethod.POST)
	public void upload (HttpServletRequest request) throws IOException {
		ServletInputStream fileStream = request.getInputStream();
		long now = System.currentTimeMillis();
		File file = new File("E:/", "file-" + now + ".txt");
		file.createNewFile();

		FileOutputStream outputStream = new FileOutputStream(file);

		byte temp[] = new byte[1024];
		int size = -1;
		while ((size = fileStream.read(temp)) != -1) { // 每次读取1KB，直至读完
			outputStream.write(temp, 0, size);
		}
	}
}
