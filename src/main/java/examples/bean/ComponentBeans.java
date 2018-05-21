package examples.bean;

import examples.instantiating.ExampleBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author wangzhongke
 */
@Component("myComponentBean")
public class ComponentBeans {

	private static int i;

	@Bean
	@Qualifier("public")
	public ExampleBean publicInstance() {
		return new ExampleBean(10, "2345");
	}

	@Bean
	@Qualifier("protected")
	public ExampleBean protectedInstance (
		@Qualifier("public") ExampleBean publicBean,
		@Value("#{privateInstance.ultimateAnswer}") String ultimateAnswer
	) {
		return new ExampleBean(publicBean.getYears(), ultimateAnswer);
	}

	@Bean
	private ExampleBean privateInstance () {
		return new ExampleBean(i++, "private");
	}
}
