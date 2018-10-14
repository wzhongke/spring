package examples.bean.instantiating;

import org.springframework.beans.factory.annotation.Lookup;

/**
 * @author wangzhongke
 */
public class MethodInjectionWithLookup {

	public void process () {
		ExampleBean bean = createExampleBean();
		System.out.println(bean);
		// do something
	}

//	protected ExampleBean createExampleBean () {
//		// notice the Spring API dependency!
//		return null;
//	}

	// 使用 Lookup 注解
	@Lookup("exampleBeanInject")
	protected ExampleBean createExampleBean () {
		// notice the Spring API dependency!
		return null;
	}
}
