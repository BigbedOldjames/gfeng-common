package com.gckj.common.utils;



import java.util.Map;

/** 
 * @Description：EhCache工具类（单例模式）
 * @author：ldc
 * date：2020-06-23
 */
public class EhCacheUtils {

//	private static CacheManager cacheManager = (CacheManager)ApplicationContextUtils.getBean("ehCacheManager");
	private static RedisService redisService = (RedisService)ApplicationContextUtils.getBean(RedisService.class);
	private static final String DEFALUT_CACHE = "sysCache";

	/**
	 * 从默认缓存中读取数据
	 */
	public static Object get(String key) {
		return get(DEFALUT_CACHE, key);
	}
	

	/**
	 * 从默认缓存中移除数据
	 */
	public static void remove(String key) {
		remove(DEFALUT_CACHE, key);
	}
	

	/**
	 * 从指定缓存中读取数据
	 */
	public static String get(String cacheName, String key) {
		return getCache(cacheName).get(key);
	}

	/**
	 * 写入数据到指定缓存
	 */
	public static void put(String cacheName, String key, String value) {
		redisService.hset(cacheName,key,value);
	}

	/**
	 * 从指定缓存中移除
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个
	 */
	private static Map<String, String> getCache(String cacheName){
		return redisService.hgetAll(cacheName);
	}
	
}
