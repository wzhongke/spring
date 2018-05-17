package examples.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * @author wangzhongke
 */
public class BeanApplicationContextAware implements ApplicationContextAware, BeanNameAware, ApplicationEventPublisherAware {

	private String name;
	private ApplicationContext context;

	private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		applicationContext.getApplicationName();
		this.context = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		// 获取到定义的name
		this.name = name;
		System.out.println(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

	}
}
