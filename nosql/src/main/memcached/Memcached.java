package memcached;


import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Memcached {

	private MemcachedClient client = null;

	void init() {
		try {
			client = new XMemcachedClient("10.134.85.164", 11211);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String [] args) throws InterruptedException, MemcachedException, TimeoutException {
		Memcached m = new Memcached();
		m.init();
		KeyIterator it= m.client.getKeyIterator(AddrUtil.getOneAddress("10.134.85.164:11211"));
		while(it.hasNext())
		{
			String key=it.next();
			System.out.println(key);
			System.out.println(m.client.get(key) + "");
		}
	}
}
