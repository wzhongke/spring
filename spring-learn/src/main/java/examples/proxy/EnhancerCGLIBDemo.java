package examples.proxy;



import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class EnhancerCGLIBDemo {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(EnhancerCGLIBDemo.class);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				System.out.println("Before invoke" + method);
				Object result = methodProxy.invokeSuper(o, args);
				System.out.println("After invoke" + method);
				return  result;
			}
		});
		EnhancerCGLIBDemo demo = (EnhancerCGLIBDemo) enhancer.create();
		demo.test();
		System.out.println(demo);
	}

	public void test() {
		System.out.println("test");
	}
}
