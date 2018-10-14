package examples.bean.instantiating;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:dispatcher-servlet.xml"})
public class ComplexObjectTest {

	@Autowired
	@Qualifier("child")
	private ComplexObject object;

	@Test
	public void testMerge () {
		System.out.println(object.getAdminEmails());
	}

	@Autowired
	@Qualifier("moreComplexObject")
	private ComplexObject object1;

	@Test
	public void testObject () {
		System.out.println(object1.getSomeList());
	}
}
