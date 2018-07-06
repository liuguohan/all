package com.core.api.test.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClusterMain {

	public static void main(String[] args) throws IOException {
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(10);
		config.setMaxIdle(2);
		
		HostAndPort node0 = new HostAndPort("192.168.110.66", 6379);
		HostAndPort node1 = new HostAndPort("192.168.110.88", 6379);
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(node1);
		nodes.add(node0);
		
		JedisCluster cluster = new JedisCluster(nodes, 5000, 10, config);
		
		System.out.println(new String(cluster.get("key1".getBytes())));
		cluster.close();
	}
	
}
