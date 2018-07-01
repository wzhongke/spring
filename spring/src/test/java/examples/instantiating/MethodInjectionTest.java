package examples.instantiating;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:dispatcher-servlet.xml"})
public class MethodInjectionTest {

	@Autowired
	private MethodInjectionWithLookup methodInjectionWithLookup;

	@Test
	public void testLookup () {
		methodInjectionWithLookup.process();
		methodInjectionWithLookup.process();
	}
}
