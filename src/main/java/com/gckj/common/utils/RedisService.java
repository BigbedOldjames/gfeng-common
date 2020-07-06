package com.omysoft.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 缓存工具类
 * @author JTG
 * @Date 2019-08-03
 */
@Component
public class RedisService {

	@Autowired
	JedisPool pool = null;
	
	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	/**
	 * 判断指定key是否存在
	 *
	 * @param key
	 *            key
	 * @return 如果存在，返回true，否则返回false
	 */
	public boolean exists(final String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.exists(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// 删除指定的Keys
	public long delete(final String... keys) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.del(keys);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// 重命名指定的Key,如果参数中的两个Keys的命令相同，
	// 或者是源Key不存在，该命令都会返回相关的错误信息。如果newKey已经存在，则直接覆盖。
	public String rename(final String oldkey, final String newkey) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.rename(oldkey, newkey);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// 设置某个key的过期时间（单位：秒）,在超过该时间后，Key被自动的删除。
	// 如果该Key在超时之前被修改，与该键关联的超时将被移除。
	public long expire(final String key, final int seconds) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.expire(key, seconds);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// #将指定Key的Value原子性的递增1。如果该Key不存在，其初始值为0，在incr之后其值为1,返回递增后的值。
	public long incrBy(final String key, final long integer) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.incrBy(key, integer);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long incr(final String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.incr(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// #将指定Key的Value原子性的递减1。如果该Key不存在，其初始值为-1，在incr之后其值为1,返回递减后的值。

	public long decrBy(final String key, final long integer) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.decrBy(key, integer);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long decr(final String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.decr(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getJSONToObject(Class<?> clazz, String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String value = jedis.get(key);
			return (T) JSON.toJavaObject((JSON) JSON.parse(value), clazz);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public String getString(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> mget(Class<?> clazz, final String... keys) {
		List<T> list = new ArrayList<T>();
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			List<String> values = jedis.mget(keys);
			for (String value : values) {
				list.add((T) JSON.toJavaObject((JSON) JSON.parse(value), clazz));
			}
			return list;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public <T> String setObjectToJSON(String key, T value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String str = JSON.toJSONString(value);
			return jedis.set(key, str);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.set(key, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public <T> String setex(final String key, final int seconds, final T value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String str = JSON.toJSONString(value);
			return jedis.setex(key, seconds, str);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public <T> String setexString(final String key, final int seconds, final String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.setex(key, seconds, value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public <T> String mset(final Map<String, T> map) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String[] keyValues = new String[map.size() * 2];
			int i = 0;
			for (String key : map.keySet()) {
				keyValues[2 * i] = key;
				keyValues[2 * i + 1] = JSON.toJSONString(map.get(key));
				i++;
			}
			return jedis.mset(keyValues);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// #该命令原子性的完成参数中所有key/value的设置操作，
	// 如果在这一批Keys中有任意一个Key已经存在了，那么该操作将全部回滚，
	// 即所有的修改都不会生效。
	public <T> Long msetnx(final Map<String, T> map) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String[] keyValues = new String[map.size() * 2];
			int i = 0;
			for (String key : map.keySet()) {
				keyValues[2 * i] = key;
				keyValues[2 * i + 1] = JSON.toJSONString(map.get(key));
				i++;
			}
			return jedis.msetnx(keyValues);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 插入Map类型的某key对应的值
	 */
	public int hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hset(key, field, value).intValue();
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	/**
	 * 查询Map类型的某key对应的值
	 */
	public String hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hget(key, field);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	/**
	 * 查询Map类型的值
	 */
	public Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hgetAll(key);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	/**
	 * 删除hash中的一条记录
	 */
	public long hdel(String hashKey, String taskId) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hdel(hashKey, taskId);
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

}