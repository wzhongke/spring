package examples.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;

@Configuration
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
}
