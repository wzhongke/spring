package examples.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WebLogAspect {

	private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
	/**
	 * 定义一个切入点.
	 * 解释下：
	 *
	 * ~ 第一个 * 代表任意修饰符及任意返回值.
	 * ~ 第二个 * 任意包名
	 * ~ 第三个 * 代表任意方法.
	 * ~ 第四个 * 定义在web包或者子包
	 * ~ 第五个 * 任意方法
	 * ~ .. 匹配任意数量的参数.
	 */

	@Pointcut("execution(* examples.controller.*.*(..))")
	public void webLog() {}

	@Before("webLog()")
	public void doBefore (JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		request.setAttribute("startTime", System.currentTimeMillis());
	}

	@After("webLog()")
	public void doAfterReturning(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		logger.info("[Sogou-Observer, cost=" + String.valueOf(System.currentTimeMillis() - (long) request.getAttribute("startTime")) + " ,Owner=OP]");
	}
}
