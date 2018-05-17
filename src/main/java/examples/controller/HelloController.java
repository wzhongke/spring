package examples.controller;

import examples.configuration.PropertiesConfig;
import examples.instantiating.ExampleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.security.Principal;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@RestController
public class HelloController {

	@Autowired
	private PropertiesConfig config;

	@RequestMapping(value = "/hello")
	public String hello (String name) {
		return "Hello " + name + "!" + config.getBaseDir();
	}

	/** 使用@PathVariable绑定annotation */
	@RequestMapping(value="/owners/{ownerId}")
	public String findOwner(@PathVariable String ownerId) {
		return ownerId;
	}

	@GetMapping("/owners/{ownerId}/pets/{petId}")
	public String findPet(@PathVariable String ownerId, @PathVariable String petId) {
		return "ownerId: " + ownerId + " petId:" + petId;
	}

	/**
	 *  使用正则表达式匹配URL {varName:regex}
	 * /example/version/spring-web-3.0.5.jar
	 * */
	@RequestMapping("/version/{symbolicName:[a-z-]+}-{version:\\d\\.\\d\\.\\d}{extension:\\.[a-z]+}")
	public String handle(@PathVariable String version, @PathVariable String extension, Model model) {
		return "version:" + version + " extension: " + extension;
	}

	/** example: http://localhost:8080/example/2013-10-18 */
	@RequestMapping(value = "/day/{day}", method= RequestMethod.GET)
	public String getForDay(@PathVariable @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date day) {
		return day.toString();
	}

	/**
	 *  GET /owners/42;q=11/pets/21;q=22
	 * 需要配置： <mvc:annotation-driven enable-matrix-variables="true"/>
	 * */
	@GetMapping("/person/{ownerId}/pets/{petId}")
	public String findPetMatrix(
		@MatrixVariable(name="q", pathVar="ownerId", required = false, defaultValue = "1") int q1,
		@MatrixVariable(name="q", pathVar="petId") int q2) {
		return "q: " + q1 + "q2: " + q2;
	}

	@GetMapping(path = "/person/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ExampleBean getExampleBean () {
		return new ExampleBean(2018, "test");
	}


	/** @RequestMapping支持的参数 */
	@RequestMapping("/supportParam")
	public void supportParam(
		WebRequest webRequest,  //Allows for generic request parameter access as well as request/session attribute access,
		NativeWebRequest nativeWebRequest,
		Locale locale, // 是指定的locale resolver
		TimeZone timeZone, //for the time zone associated  with the current request, as determined by a LocaleContextResolver.
		InputStream inputStream, Reader reader, // 访问reques的内容， 其中的内容是原始的内容
		OutputStream outputStream, Writer writer, // 生成的response的内容
		HttpMethod httpMethod, // http 方法
		Principal principal, // 包含着目前授权的用户
		Model model, // view处理相关
		RedirectAttributes redirect, // 重定向相关
		SessionStatus sessionStatus, // 标记完成表单处理，触发清除由@SessionAttributes指定的session attributes
		UriComponentsBuilder uriComponentsBuilder, // 准备与当前请求host，port，scheme，路径相关的URL
		HttpSession session,

		@ModelAttribute ExampleBean exampleBean, // 在方法参数上使用，表明该参数是从model中获取的；如果model中不存在，则初始化变量并添加到model中
		BindingResult result, // 必须紧跟被绑定的对象

		@PathVariable String pathVariable,
		@MatrixVariable int matrixVariable,
		@RequestParam String requestParam, // annotated parameters for access to specific Servlet request parameters. Parameter
		//values are converted to the declared method argument type.
		@RequestHeader String requestHeader, // 请求的头部
		@RequestHeader("Accept-Encoding") String encoding, // 请求头部的部分内容
		@RequestBody String requestBody, // 请求的body
		@RequestPart String requestPart, // annotated parameters for access to the content of a "multipart/form-data" request part
		@SessionAttribute(name="name") String name,
		@RequestAttribute(name="personName") String personName,
		@CookieValue("JSESSIONID") String cookie // 获取 名称为JSESSIONID的cookie值

	) {
		System.out.println("requestHeader: " + requestHeader);
	}
}
