package annotation;

import examples.annotation.Annotation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
}
