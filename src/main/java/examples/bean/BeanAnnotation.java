package examples.bean;

import examples.instantiating.ExampleBean;
import examples.instantiating.ExampleRef;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
@ImportResource({"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class BeanAnnotation {

	@Bean(initMethod = "init")
	@Scope("prototype")
	public Foo foo1 () {
		return new Foo();
	}

	/**
	 * 代理方式
	 */
	@Bean(destroyMethod = "cleanup", name = "foo2")
	@Description("This is a description")
	public Foo foo2 () {
		return foo1();
	}

	@Bean
	@Qualifier("exampleBeanJava")
	public ExampleBean getExampleBean () {
		return new ExampleBean(2018, "java");
	}

	@Bean
	@Qualifier("exampleBeanJava2")
	public ExampleBean getExampleBeanScope () {
		return getExampleBean();
	}

	@Bean
	@Qualifier("targetBeanJava")
	public ExampleRef getExampleRef () {
		return new ExampleRef();
	}

	@Bean
	@Qualifier("exampleBeanJava3")
	public ExampleBean getExampleBean3 () {
		ExampleBean bean = new ExampleBean();
		bean.setRef(getExampleRef());
		return bean;
	}


	@Bean
	@ConditionalOnMissingBean(InternalResourceViewResolver.class)
	public InternalResourceViewResolver defaultViewResolver () {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public MappingJackson2JsonView jsonView(){
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		return jsonView;
	}
}
