package examples.proxy;

import org.apache.catalina.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Service {
	void add();
}

class UserServiceImp implements Service {

	@Override
	public void add() {
		System.out.println("------add------------");
	}
}


public class JdkProxy implements InvocationHandler {

	private Object target;

	public JdkProxy(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("----------before-----------------");
		Object result = method.invoke(target, args);
		System.out.println("-----------after-------------");
		return result;
	}

	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
	}

	public static void main(String[] args) {
		UserServiceImp serviceImp = new UserServiceImp();

		JdkProxy proxy = new JdkProxy(serviceImp);

		Service service = (Service) proxy.getProxy();

		service.add();
	}
}
