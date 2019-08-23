package examples.bean.instantiating;

import examples.bean.BeanAnnotation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BeanAnnotation.class)
public class ExampleBeanTest {

	@Autowired
	@Qualifier("exampleBean5")
	ExampleBean bean;

	@Autowired
    @Qualifier("exampleBean6")
    ExampleBean bean6;

	@Autowired
	@Qualifier("exampleBeanJava")
	private ExampleBean javaBean1;

	@Autowired
	@Qualifier("exampleBeanJava2")
	private ExampleBean javaBean2;

	@Test
	public void testExampleBean () {
		System.out.println(bean);
	}

	@Test
	public void testIdRef () {
        System.out.println(bean6);
    }

    @Test
	public void testJavaBean () {
	    System.out.println(javaBean1);
	    System.out.println(javaBean2);
    }
}
