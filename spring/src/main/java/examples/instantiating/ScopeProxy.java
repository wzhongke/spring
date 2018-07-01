package examples.instantiating;

/**
 * @author wangzhongke
 */
public class ScopeProxy {

	private ExampleBean bean;

	public ExampleBean getBean() {
		return bean;
	}

	public void setBean(ExampleBean bean) {
		this.bean = bean;
	}
}
