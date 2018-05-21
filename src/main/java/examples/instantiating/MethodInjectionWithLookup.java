package examples.instantiating;

import org.springframework.beans.factory.annotation.Lookup;

/**
 * @author wangzhongke
 */
public class MethodInjectionWithLookup {

	public void process () {
		ExampleBean bean = createExampleBean();
		System.out.println(bean);
		bean.setYears(6000);
		// do something
	}

	/**
	 * Spring 使用 CGLIB 库生成 bytecode 来动态覆盖对应方法的子类实现
	 * @return
	 */
	@Lookup("exampleBeanInject")
	protected ExampleBean createExampleBean () {
		// notice the Spring API dependency!
		return null;
	}
}
