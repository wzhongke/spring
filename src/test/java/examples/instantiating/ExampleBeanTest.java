package examples.instantiating;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:dispatcher-servlet.xml"})
public class ExampleBeanTest {

	@Autowired
	@Qualifier("exampleBean5")
	ExampleBean bean;

	@Autowired
    @Qualifier("exampleBean6")
    ExampleBean bean6;

	@Test
	public void testExampleBean () {
		System.out.println(bean);
	}

	@Test
	public void testIdRef () {
        System.out.println(bean6);
    }
}
