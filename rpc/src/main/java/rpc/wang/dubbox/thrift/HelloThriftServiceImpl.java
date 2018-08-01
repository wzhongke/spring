package rpc.wang.dubbox.thrift;

import org.apache.thrift.TException;

/**
 * @author wangzhongke
 * Dubbox 使用 HelloThrift
 */
public class HelloThriftServiceImpl implements HelloThriftService.Iface {

	@Override
	public String hello() throws TException {
		return "Hello Thrift";
	}
}
