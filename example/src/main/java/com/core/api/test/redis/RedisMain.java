package com.core.api.test.redis;

import redis.clients.jedis.Jedis;

public class RedisMain {

	public static void main(String[] args) {
		
		Jedis redisClient = new Jedis("192.168.110.66", 6379);
		redisClient.set("key1", "value");
		
		redisClient.close();
	}
	
}
