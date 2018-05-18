package examples.bean;

import examples.instantiating.ExampleBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author wangzhongke
 */
@Configuration
public class BeanConfiguration {

	@Bean
	@Primary
	public ExampleBean firstExampleBean () {
		return new ExampleBean(10, "");
	}

}
