package configuration;

import examples.bean.configuration.PropertiesConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PropertiesConfig.class)
public class PropertiesConfigTest {

	@Autowired
	private PropertiesConfig config;

	@Test
	public void testConfig () {
		System.out.println(config.getBaseDir());
	}
}
