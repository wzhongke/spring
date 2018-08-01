package rpc.wang.dubbox;

public class HelloServiceImpl implements HelloService {

	@Override
	public String hello() {
		return "Hello World";
	}
}
