package com.omysoft.common.utils;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClientManager {
	
	public static Jedis redisClient() {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		JedisPool jedisPool = (JedisPool) context.getBean("jedisPool");
	    Jedis client = jedisPool.getResource();
	    return client;
	}
}
