package examples.instantiating;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:dispatcher-servlet.xml"})
public class ScopeProxyTest {

	@Autowired
	private ScopeProxy proxy;

	@Test
	public void testProxy () {
		System.out.println(proxy.getBean());
		System.out.println(proxy.getBean());
	}
}
