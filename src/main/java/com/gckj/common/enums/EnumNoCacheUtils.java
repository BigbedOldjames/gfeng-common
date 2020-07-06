package com.gckj.common.enums;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 
 * 
 * @Description：枚举类工具类（工厂模式之恶汉模式）,不适用缓存存储键值对
 * @author：ldc
 * date：2020-06-23
 */
public class EnumNoCacheUtils {

	private static Logger logger = LoggerFactory.getLogger(EnumNoCacheUtils.class);

	/**
	 * 根据索引获取
	 * 
	 * @param <T>
	 * @param clazz
	 * @param ordinal
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> clazz, int ordinal) {
		return (T) clazz.getEnumConstants()[ordinal];
	}

	/**
	 * 根据label值得到 对应vale的值
	 * 
	 * @param enumType
	 * @param label
	 * @param <T>
	 * @return
	 */
	public static <T extends IEnumState> String valueOf(Class<T> enumType, String label) {
		String val = null;
		T[] enumConstants = enumType.getEnumConstants();
		for (T t : enumConstants) {
			if (t.getLabel().equals(label)) {
				val = t.getValue();
			}
		}
		return val;
	}

	/**
	 * 根据value值得到 对应label的值
	 * 
	 * @param enumType
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T extends IEnumState> String labelOf(Class<T> enumType, String value) {
		String val = null;
		T[] enumConstants = enumType.getEnumConstants();
		for (T t : enumConstants) {
			if (t.getValue().equals(value)) {
				val = t.getLabel();
			}
		}
		return val;
	}

	public static <T extends IEnumState> T touch(Class<T> enumType, String value) {
		if (value == null || value.length() == 0)
			return enumType.getEnumConstants()[0];
		for (T t : enumType.getEnumConstants()) {
			if (t.getValue().equals(value)) {
				return t;
			}
		}
		return null;
	}

	// -----------------按需加载枚举类-------------------

	/**
	 * 类对象列表获取枚举类DTO
	 * @param clazz
	 * 			Class对象列表
	 */
	public static Map<String, List<EnumDto>> getEnumValues(Class<?>... clazz) {
		Map<String, List<EnumDto>> map = new HashMap<String, List<EnumDto>>();
		try {
			for (Class<?> c : clazz) {
				// 过滤非枚举类型
				if (c.isEnum()) {
					Class<?>[] interfaces = c.getInterfaces();
					Boolean flag = false;
					for (Class<?> cls : interfaces) {
						if (cls.equals(IEnumState.class)) {
							flag = true;
							break;
						}
					}
					List<EnumDto> list = new ArrayList<EnumDto>();
					if (flag) {
						Method getValue = c.getMethod("getValue");
						Method getLabel = c.getMethod("getLabel");
						// 得到enum的所有实例
						Object[] objs = c.getEnumConstants();
						for (Object obj : objs) {
							EnumDto e = new EnumDto();
							e.setLabel((String) getLabel.invoke(obj));
							e.setValue((String) getValue.invoke(obj));
							list.add(e);
						}
				//		logger.info("初始化加载枚举类型：" + c.getName());
						map.put(c.getName(), list);
					} else {
						logger.warn(c.getName() + "未继承公用IEnumState接口, 如果该枚举类需要进行转换来让spring标签使用，请确保该类继承该接口！");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 类对象列表获取枚举类键值对
	 * @param clazz
	 * 			Class对象列表
	 * @return
	 */
	public static Map<String, Map<String, String>> getEnumMapValues(Class<?>... clazz) {
		Map<String, Map<String, String>> enumMap = new HashMap<String, Map<String, String>>();
		Map<String, List<EnumDto>> enumValues = getEnumValues(clazz);
		Iterator<String> it = enumValues.keySet().iterator();
		while (it.hasNext()) {
			String nextKey = it.next();
			Map<String, String> map = new HashMap<String, String>();
			List<EnumDto> list = enumValues.get(nextKey);
			for (EnumDto o : list) {
				map.put(o.getValue(), o.getLabel());
			}
			enumMap.put(nextKey, map);
		}
		return enumMap;
	}
	
	public static List<EnumDto> getEnum(String key) {
		List<EnumDto> list = new ArrayList<EnumDto>();
		try {
			list = getEnumValues(Class.forName(key)).get(key);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(key + "无法找到对应的EnumDto集合，请检查输入的类的全路径！");
		}
		return list;
	}
	
	public static Map<String, String> getEnumMap(String key) {
		List<EnumDto> list = getEnum(key);
		Map<String, String> map = new HashMap<String, String>();
		for (EnumDto dto : list) {
			map.put(dto.getValue(), dto.getLabel());
		}
		return map;
	}
	
	/**
	 * 获取指定枚举类的label
	 */
	public static String getEnumMapLabel(String key, String value) {
		Map<String, String> map = getEnumMap(key);
		return map.get(value);
	}

	public static Map<String, Map<String, String>> getEnumMapValues(Set<Class<?>> c) {
		List<Class<?>> list = new ArrayList<Class<?>>(c);
		Class<?>[] array = new Class<?>[list.size()];
		for (int i = 0, len = list.size(); i < len; i++) {
			array[i] = list.get(i);
		}
		return getEnumMapValues(array);
	}
	
	public static Map<String, List<EnumDto>> getEnumValues(Set<Class<?>> c) {
		List<Class<?>> list = new ArrayList<Class<?>>(c);
		Class<?>[] array = new Class<?>[list.size()];
		for (int i = 0, len = list.size(); i < len; i++) {
			array[i] = list.get(i);
		}
		return getEnumValues(array);
	}
	
	public static Map<String, Map<String, String>> getEnumMapValues(Map<String, List<EnumDto>> enumValues) {
		Map<String, Map<String, String>> enumMap = new HashMap<String, Map<String, String>>();
		Iterator<String> it = enumValues.keySet().iterator();
		while (it.hasNext()) {
			String nextKey = it.next();
			Map<String, String> map = new HashMap<String, String>();
			List<EnumDto> list = enumValues.get(nextKey);
			for (EnumDto o : list) {
				map.put(o.getValue(), o.getLabel());
			}
			enumMap.put(nextKey, map);
		}
		return enumMap;
	}
	
}
