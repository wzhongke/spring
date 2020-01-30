package redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConnect {
	/** 直接连接 */
	private static Jedis jedis = new Jedis("10.134.96.237", 6379);
	/**
	 * 线程池连接
	 */
	private static JedisPool pool = new JedisPool(new GenericObjectPoolConfig(), "10.134.96.237", 6379);

	public static void main(String[] args) throws InterruptedException {
		System.out.println(jedis.set("hello", "world"));
		Jedis jedises = pool.getResource();
		jedises.set("hello", "王");
		System.out.println(jedises.get("hello"));
	}

	public static void data() {
		// 设置字符串，返回结果 OK
		jedis.set("hello", "world");
		// 输出结果 world
		jedis.get("hello");
		// 输出结果 1
		jedis.incr("counter");
		// hash
		jedis.hset("myhash", "f1", "v1");
		jedis.hset("myhash", "f2", "v2");
		jedis.hgetAll("myhash");
		// list
		jedis.rpush("mylist", "1");
		jedis.rpush("mylsit", "2");
		jedis.lpush("mylist", "3");
		jedis.lrange("mylist", 0, -1);
		// set
		jedis.sadd("myset", "a");
		jedis.sadd("myset", "b");
		// 输出结果 [b, a]
		jedis.smembers("myset");
		// 有序集合 zset
		jedis.zadd("myzset", 99, "tom");
		jedis.zadd("myzset", 33, "peter");
		jedis.zadd("myzset", 66, "james");
		jedis.zrangeWithScores("myzset", 0, -1);
	}
}
