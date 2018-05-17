package examples.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebLogAspect {

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
	@Pointcut("execution(public * com.kfit.*.web..*.*(..))")
	public void webLog() {}
}
