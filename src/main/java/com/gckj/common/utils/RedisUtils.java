package com.omysoft.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 通用缓存工具类
 * 
 * @author lh
 * @version 3.0
 * @since 2016-6-22
 * 
 */
public class RedisUtils {

	private static StringRedisTemplate stringRedisTemplate = ApplicationContextUtils.getBean("stringRedisTemplate");
	private static RedisTemplate<String, Object> redisTemplate = ApplicationContextUtils.getBean("redisTemplate");

	/**
	 * 删除缓存<br>
	 * 根据key精确匹配删除
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisTemplate.delete(key[0]);
			} else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 批量删除<br>
	 * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
	 * 
	 * @param pattern
	 */
	public static void batchDel(String... pattern) {
		for (String kp : pattern) {
			redisTemplate.delete(redisTemplate.keys(kp + "*"));
		}
	}

	/**
	 * 取得缓存（int型）
	 * 
	 * @param key
	 * @return
	 */
	public static Integer getInt(String key) {
		String value = stringRedisTemplate.boundValueOps(key).get();
		if (StringUtils.isNotBlank(value)) {
			return Integer.valueOf(value);
		}
		return null;
	}

	/**
	 * 取得缓存（字符串类型）
	 * 
	 * @param key
	 * @return
	 */
	public static String getStr(String key) {
		return stringRedisTemplate.boundValueOps(key).get();
	}
	
	/**
	 * 将对象以JSON格式写入缓存
	 * @param key
	 * @param obj
	 * @param expireTime 
	 * 			失效时间（单位秒）
	 */
	public static void setStr(String key, String string, Long expireTime) {
		stringRedisTemplate.opsForValue().set(key, string);
		if (expireTime > 0) {
			stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		}
	}

	/**
	 * 取得缓存（字符串类型）
	 * 
	 * @param key
	 * @return
	 */
	public static String getStr(String key, boolean retain) {
		String value = stringRedisTemplate.boundValueOps(key).get();
		if (!retain) {
			redisTemplate.delete(key);
		}
		return value;
	}

	/**
	 * 获取缓存<br>
	 * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
	 * 
	 * @param key
	 * @return
	 */
	public static Object getObj(String key) {
		return redisTemplate.boundValueOps(key).get();
	}

	/**
	 * 获取缓存<br>
	 * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
	 * 
	 * @param key
	 * @param retain
	 *            是否保留
	 * @return
	 */
	public static Object getObj(String key, boolean retain) {
		Object obj = redisTemplate.boundValueOps(key).get();
		if (!retain) {
			redisTemplate.delete(key);
		}
		return obj;
	}

	/**
	 * 获取缓存<br>
	 * 注：该方法暂不支持Character数据类型
	 * 
	 * @param key
	 *            key
	 * @param clazz
	 *            类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key, Class<T> clazz) {
		return (T) redisTemplate.boundValueOps(key).get();
	}

	/**
	 * 获取缓存json对象<br>
	 * 
	 * @param key
	 *            key
	 * @param clazz
	 *            类型
	 * @return
	 */
	public static <T> T getJson(String key, Class<T> clazz) {
		return JSON.parseObject(stringRedisTemplate.boundValueOps(key).get(), clazz);
	}

	/**
	 * 将value对象写入缓存
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            失效时间(秒)
	 */
	public static void set(String key, Object value, Date time) {
		if (value.getClass().equals(String.class)) {
			stringRedisTemplate.opsForValue().set(key, value.toString());
		} else if (value.getClass().equals(Integer.class)) {
			stringRedisTemplate.opsForValue().set(key, value.toString());
		} else if (value.getClass().equals(Double.class)) {
			stringRedisTemplate.opsForValue().set(key, value.toString());
		} else if (value.getClass().equals(Float.class)) {
			stringRedisTemplate.opsForValue().set(key, value.toString());
		} else if (value.getClass().equals(Short.class)) {
			stringRedisTemplate.opsForValue().set(key, value.toString());
		} else if (value.getClass().equals(Long.class)) {
			stringRedisTemplate.opsForValue().set(key, value.toString());
		} else if (value.getClass().equals(Boolean.class)) {
			stringRedisTemplate.opsForValue().set(key, value.toString());
		} else {
			redisTemplate.opsForValue().set(key, value);
		}
		if (time.getTime() > 0) {
			redisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
		}
	}

	/**
	 * 将对象以JSON格式写入缓存
	 * @param key
	 * @param obj
	 * @param expireTime 
	 * 			失效时间（单位秒）
	 */
	public static void setJson(String key, Object obj, Long expireTime) {
		stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(obj));
		if (expireTime > 0) {
			stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		}
	}

	/**
	 * 更新key对象field的值
	 * 
	 * @param key
	 *            缓存key
	 * @param field
	 *            缓存对象field
	 * @param value
	 *            缓存对象field值
	 */
	public static void setJsonField(String key, String field, String value) {
		JSONObject obj = JSON.parseObject(stringRedisTemplate.boundValueOps(key).get());
		obj.put(field, value);
		stringRedisTemplate.opsForValue().set(key, obj.toJSONString());
	}

	/**
	 * 递减操作
	 * 
	 * @param key
	 * @param by
	 * @return
	 */
	public static double decr(String key, double by) {
		return redisTemplate.opsForValue().increment(key, -by);
	}

	/**
	 * 递增操作
	 * 
	 * @param key
	 * @param by
	 * @return
	 */
	public static double incr(String key, double by) {
		return redisTemplate.opsForValue().increment(key, by);
	}

	/**
	 * 获取double类型值
	 * 
	 * @param key
	 * @return
	 */
	public static double getDouble(String key) {
		String value = stringRedisTemplate.boundValueOps(key).get();
		if (StringUtils.isNotBlank(value)) {
			return Double.valueOf(value);
		}
		return 0d;
	}

	/**
	 * 设置double类型值
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            失效时间(秒)
	 */
	public static void setDouble(String key, double value, Date time) {
		stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
		if (time.getTime() > 0) {
			stringRedisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
		}
	}

	/**
	 * 设置int类型值
	 * 
	 * @param key
	 * @param value
	 * @param time
	 *            失效时间(秒)
	 */
	public static void setInt(String key, int value, Date time) {
		stringRedisTemplate.opsForValue().set(key, String.valueOf(value));
		if (time.getTime() > 0) {
			stringRedisTemplate.expire(key, time.getTime(), TimeUnit.SECONDS);
		}
	}

	/**
	 * 指定缓存的失效时间
	 * 
	 * @author FangJun
	 * @date 2016年8月14日
	 * @param key
	 *            缓存KEY
	 * @param time
	 *            失效时间(秒)
	 */
	public static void expire(String key, Long timeout) {
		if (timeout > 0) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 添加set
	 * 
	 * @param key
	 * @param value
	 */
	public static void sadd(String key, String... value) {
		redisTemplate.boundSetOps(key).add(value);
	}

	/**
	 * 删除set集合中的对象
	 * 
	 * @param key
	 * @param value
	 */
	public static void srem(String key, String... value) {
		redisTemplate.boundSetOps(key).remove(value);
	}

	/**
	 * set重命名
	 * 
	 * @param oldkey
	 * @param newkey
	 */
	public static void srename(String oldkey, String newkey) {
		redisTemplate.boundSetOps(oldkey).rename(newkey);
	}
	
	
	
	//-------------hash--------------
	
	/**
	 * 向Redis hash中指定key添加键值对对象
	 * @param key
	 * 			cache对象key
	 * @param map
	 * 			键值对对象
	 */
	public static <T> void addHash(String key, Map<String, T> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}
	
	/**
	 * 向Redis hash中指定key添加键值对
	 * @param key
	 *            cache对象key
	 * @param field
	 *            键
	 * @param value
	 *            值
	 */
	public static void addHash(String key, String field, String value) {
		redisTemplate.opsForHash().put(key, field, value);
	}

	/**
	 * 向Redis hash中指定key添加对象
	 * @param key
	 *            cache对象key
	 * @param field
	 *            键
	 * @param obj
	 *            序列化存储的对象
	 */
	public static <T> void addHashObj(String key, String field, T obj) {
		redisTemplate.opsForHash().put(key, field, JSON.toJSONString(obj));
	}
	
	/**
	 * 批量删除Redis hash中keys
	 * @param key
	 * @param fields 
	 */
	public static Long batchDelHash(String key, Object... fields) {
		return redisTemplate.boundHashOps(key).delete(fields);
	}
	
	/**
	 * 获取Redis hash中指定key的单个对象
	 * @param key
	 * @param field
	 * @param clazz
	 * @return 
	 */
	public static <T> T getHashParseObject(String key, String field, Class<T> clazz) {
		String text = (String) redisTemplate.boundHashOps(key).get(field);
		return JSON.parseObject(text, clazz);
	}
	
	/**
	 * 获取Redis hash中指定key的对象列表
	 * @param key
	 * @param field
	 * @param clazz
	 * @return 
	 */
 	public static <T> List<T> getHashParseArray(String key, String field, Class<T> clazz) {
		String text = (String) redisTemplate.boundHashOps(key).get(field);
		List<T> list = JSON.parseArray(text, clazz);
		if (list == null) {
			list = new ArrayList<T>();
		}
		return list;
	}
	
	/**
	 * 获取Redis hash中的键值对
	 * @param key
	 * @param clazz
	 * @return 
	 */
	public static <T> Map<String, T> getHash(String key, Class<T> clazz) {
		BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
		return boundHashOperations.entries();
	}
	
	/**
	 * 获取Redis hash中指定key的值（默认返回String）
	 * @param key
	 * 			cache对象key
	 * @param field
	 * 			键
	 * @return
	 */
	public static String getHashValue(String key, String field) {
		Object hashValue = redisTemplate.boundHashOps(key).get(field);
		return (String)hashValue;
	}

}
