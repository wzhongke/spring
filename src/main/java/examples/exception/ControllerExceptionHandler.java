package examples.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String failedException (Exception fa) {
		fa.printStackTrace();
		return "{'success': 'false', 'cause' : '" + fa + "'}";
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String commonException (Exception e) {
		e.printStackTrace();
		return "{'success': 'false', 'cause' : '" + e + "'}";
	}
}

