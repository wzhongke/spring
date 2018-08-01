package rpc.wang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author wangzhongke
 */
@SpringBootApplication
@ServletComponentScan
public class Application implements CommandLineRunner {


	public static void main(String [] args) {
		System.setProperty("dubbo.application.logger","slf4j");
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 以线程的方式运行其他内容，若 netty
	 */
	@Override
	public void run(String... args) throws Exception {
	}
}
