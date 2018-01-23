package examples.instantiating;

/**
 * @author wangzhongke
 */
public class StaticFactoryMethod {
	private static StaticFactoryMethod instance = new StaticFactoryMethod();

	private StaticFactoryMethod() {}

	public static StaticFactoryMethod createInstance() {
		return instance;
	}

}
