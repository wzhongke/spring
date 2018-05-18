package configuration;

import examples.bean.BeanAnnotation;
import examples.bean.PropertiesConfig;
import examples.datasource.DataSourceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PropertiesConfig.class, BeanAnnotation.class})
public class PropertiesConfigTest {

	@Autowired
	private PropertiesConfig config;

	@Test
	public void testConfig () {
		System.out.println(config.getBaseDir());
	}


}
