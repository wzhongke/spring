package examples.annotation;

import com.sun.istack.internal.Nullable;
import examples.bean.instantiating.ExampleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * @author wangzhongke
 */
public class Annotation {

	private ExampleBean exampleBean;

	/**
	 * `@Resource` 如果没有name属性指定，那么会先寻找name与属性名相同的bean；如果没有，那么就根据类型寻找
	 */
	@Resource
	private ExampleBean noPrimary;

	/**
	 * `@Autowired` 可以注入Map类型，只要map的key是String类型
	 */
	@Autowired
	private Map<String, ExampleBean> beanMap;

	@Autowired
	private Set<ExampleBean> beanSet;

	public Annotation() {}

	/**
	 * `@Autowired` 可以用在构造器上，也可以用在 setter 方法上，也可以用在属性上
	 * `@Autowired` 是根据类型匹配的，默认的required是false
	 */
	@Autowired(required = false)
	public Annotation(@Nullable ExampleBean exampleBean) {
		this.exampleBean = exampleBean;
	}

	/**
	 * `@Required` 注解用在属性的setter方法上，表示该属性必须已经被注入
	 * `@Qualifier()` 注解可以根据bean的name或者id注入，比 `@Primary` 更灵活
	 */
	@Autowired
	@Required
	public void setExampleBean (@Qualifier("no-primary") ExampleBean exampleBean) {
		this.exampleBean = exampleBean;
	}

	public ExampleBean getExampleBean() {
		return exampleBean;
	}

	public ExampleBean getNoPrimary() {
		return noPrimary;
	}
}
