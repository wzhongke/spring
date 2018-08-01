package rpc.wang.dubbox;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rpc.wang.dubbox.thrift.HelloThriftService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationConsumer.xml"})
public class DubboTest {

	@Autowired
	private HelloService demoService;

	@Test
	public void test () {
		System.out.println(demoService.hello());
	}

	@Autowired
	private HelloThriftService.Iface thriftService;

	@Test
	public void testThriftService () throws TException {
		System.out.println(thriftService.hello());
	}
}
