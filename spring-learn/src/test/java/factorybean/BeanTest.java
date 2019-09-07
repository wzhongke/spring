package factorybean;

import examples.factorybean.Car;
import examples.factorybean.CarFactoryBean;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class BeanTest {

    @Test
    public void testBean() {
        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("services.xml"));
        Car car = (Car) factory.getBean("car");
        System.out.println(car);
        CarFactoryBean bean = (CarFactoryBean) factory.getBean("&car");
        System.out.println(bean.getCarInfo());

    }

    @Test
    public void testApplication() {
        BeanFactory factory = new ClassPathXmlApplicationContext("services.xml");
    }
}
