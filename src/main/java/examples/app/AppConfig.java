package examples.app;

import examples.component.NameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.ComponentScan.Filter;

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

	}
}
