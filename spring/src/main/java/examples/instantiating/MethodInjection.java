package examples.instantiating;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MethodInjection implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void process () {
		ExampleBean bean = createExampleBean();
		// do something
	}

	protected ExampleBean createExampleBean () {
		// notice the Spring API dependency!
		return this.applicationContext.getBean("exampleBeanInject", ExampleBean.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
