package test;

import examples.userdefinition.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class XMLParseTest {
	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new ClassPathResource("services.xml"));
		User user = (User) factory.getBean("testa");
		System.out.println(user);
	}
}
