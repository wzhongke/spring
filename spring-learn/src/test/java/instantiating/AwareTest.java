package examples.bean.instantiating;

import examples.aware.BeanApplicationContextAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:dispatcher-servlet.xml"})
public class AwareTest {

	@Autowired
	private BeanApplicationContextAware aware;

	@Test
	public void testAware () {
		System.out.println(aware.getName());
	}
}
