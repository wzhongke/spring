package examples.app;

import examples.component.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.ComponentScan.Filter;

import javax.annotation.PostConstruct;

/**
 * includeFilters 是扫描匹配的内容，
 * excludeFilters 是过滤匹配的内容
 * @author wangzhongke
 */
@ComponentScan(basePackages = "examples.component",
	includeFilters = @Filter(type = FilterType.REGEX, pattern = ".*Example.*"),
	excludeFilters = {@Filter(Repository.class), @Filter(AppConfig.class)},
	nameGenerator = NameGenerator.class)
public class AppConfig {

	public static void main(String [] args) {
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");

		// add a shutdown hook for the above context...
		ctx.registerShutdownHook();
	}
}
