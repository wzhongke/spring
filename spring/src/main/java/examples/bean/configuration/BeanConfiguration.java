package examples.bean.configuration;

import examples.bean.instantiating.ExampleBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 使用按照类型注入时， @Primary 注的bean会优先被注入
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
