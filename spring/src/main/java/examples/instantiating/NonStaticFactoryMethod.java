package examples.instantiating;

/**
 * @author wangzhongke
 */
public class NonStaticFactoryMethod {

	private static NonStaticFactoryMethod instance1 = new NonStaticFactoryMethod();
	private static NonStaticFactoryMethod instance2 = new NonStaticFactoryMethod();

	public NonStaticFactoryMethod getInstance1() {
		return instance1;
	}

	public NonStaticFactoryMethod getInstance2() {
		return instance2;
	}
}
