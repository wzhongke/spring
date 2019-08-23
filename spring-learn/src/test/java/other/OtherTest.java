package other;


import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

public class OtherTest {

	@Test
	public void testMapToString() {
		Map<String, Object> map = new HashMap<>();
		map.put("test", "test");
		System.out.println(map.toString());
	}

	@Test
	public void testSimpleLoad() {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("services.xml"));
		test.Test test = (test.Test) factory.getBean("test");
		test.test();
	}
}
