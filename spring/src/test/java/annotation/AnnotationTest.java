package annotation;

import examples.annotation.Annotation;
import examples.component.ComponentBeans;
import examples.component.MyService;
import examples.instantiating.ExampleBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:annotation.xml"})
public class AnnotationTest {

	@Autowired
	Annotation annotation;

	@Test
	public void testAnnotation() {
		System.out.println(annotation.getNoPrimary());
	}

	@Autowired
	@Qualifier("protected")
	private ExampleBean exampleBean;

	@Test
	public void testComponent () {
		System.out.println(exampleBean);
	}

	@Autowired
	private MyService  service;

	@Test
	public void testNameGenerator () {
		System.out.println(service.getBeanName());
	}
}
