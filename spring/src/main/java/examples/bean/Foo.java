package examples.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;

/**
 * @author wangzhongke
 */
public class Foo implements BeanPostProcessor, Ordered{

	public void init() {
		System.out.println("Foo init");
	}

	public void cleanup () {
		System.out.println("Foo cleanup");
	}

	@Override
	public int getOrder() {
		return 1;
	}

	@Nullable
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Nullable
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println(beanName + " : " + bean);
		return null;
	}
}
