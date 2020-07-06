package com.gckj.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gckj.common.enums.AppEnum;
import com.gckj.common.enums.EnumDto;
import com.gckj.common.enums.IEnumState;

/** 
 * @Description：枚举类工具类（工厂模式之恶汉模式）
 * @author：ldc
 * date：2020-06-23
 */
public class EnumUtils {
	
	private static Logger logger = LoggerFactory.getLogger(EnumUtils.class);
	
	/**
     * 枚举类对应的包路径,多个包用逗号分隔
     * 暂定不从配置文件加载信息
     */
	public final static String[] ENUM_PACKAGE_NAME = new String[]{"com.omysoft.system.enums", "com.omysoft.web.enums", "com.omysoft.tms.enums","com.omysoft.busi.enums","com.omysoft.stl.enums","com.omysoft.cms.enums","com.omysoft.ins.enums"};
//    public final static String ENUM_PACKAGE_NAME = UserConstants.getConfig("enumPackages");
    
    /**
     * 枚举接口类全路径
     */
    public final static String ENUM_INTERFACE_NAME = "com.omysoft.common.enums.IEnumState";

    /**
     * 枚举类对应的全路径Class<?>集合
     */
    public static final Set<Class<?>> enumClazz = PackageScanUtils.getClasses(ENUM_PACKAGE_NAME);
    
    /**
     * 主要用于spring mvc form option标签的使用
     */
    public static final Map<String, List<EnumDto>> enumList = initialEnumMap();
    
    /**
     * 外键转义（id转换成对应的字符串）
     */
    public static Map<String, Map<String, String>> enumMap = null;

	/**
     * 加载所有枚举对象数据
     *
     * */
	public static Map<String, List<EnumDto>> initialEnumMap(){
		Map<String, List<EnumDto>> map = new HashMap<String, List<EnumDto>>();
        try {
            for (Class<?> clazz : EnumUtils.enumClazz) {
            	//过滤非枚举类型
            	if (clazz.isEnum()) {
            		Class<?>[] interfaces = clazz.getInterfaces();
            		Boolean flag = false;
                	for (Class<?> cls : interfaces) {
                		if (cls.equals(Class.forName(ENUM_INTERFACE_NAME))) {
                			flag = true;
                			break;
                		}
                	}
                	List<EnumDto> list = new ArrayList<EnumDto>();
                	if (flag) {
                    	Method getValue = clazz.getMethod("getValue");
                        Method getLabel = clazz.getMethod("getLabel"); 
                        //得到enum的所有实例
        	            Object[] objs = clazz.getEnumConstants();
        	            for (Object obj : objs) {
        	            	EnumDto e = new EnumDto();
        	            	e.setLabel((String)getLabel.invoke(obj));
        	            	e.setValue((String)getValue.invoke(obj));
        	            	list.add(e);
        	            }
                		logger.info("初始化加载枚举类型：" + clazz.getName());
                	} else {
                		logger.warn(clazz.getName() + "未继承公用IEnumState接口, 如果该枚举类需要进行转换来让spring标签使用，请确保该类继承该接口！");
                	}
    	            map.put(clazz.getName(), list);
            	}
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return map;
    }
	
	public static List<EnumDto> getEnumValues(String key) {
		List<EnumDto> list = enumList.get(key);
		if (list == null || list.size() == 0) {
			logger.error(key + "无法找到对应的EnumDto集合，请检查输入的值！");
			return new ArrayList<EnumDto>();
		}
		return list;
	}
	
	public static String getEnumJSON(Class<?> ... clazz) {
		StringBuffer buffer = new StringBuffer().append("[");
		for (int i = 0, len = clazz.length; i < len; i++) {
//			String simpleName = clz.getSimpleName();
//			String key = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
			if (i != 0) {
				buffer.append(",");
			}
			buffer.append("[");
			buffer.append(JSON.toJSONString(EnumUtils.getEnumMap(clazz[i].getName())));
			buffer.append(",");
			buffer.append(JSON.toJSONString(EnumUtils.getEnumValues(clazz[i].getName())));
			buffer.append("]");
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	public static String getEnumValuesJSON(String key) {
		return JSON.toJSONString(getEnumValues(key)).replace("\"", "'");
	}
	
	public static Map<String, String> getEnumMap(Class<?> clazz) {
		return EnumUtils.getEnumMap(clazz.getName());
	}
	
	public static Map<String, String> getEnumMap(String key) {
		if (enumMap == null) {
			enumMap = new HashMap<String, Map<String, String>>();
			Iterator<String> it = enumList.keySet().iterator();
			while (it.hasNext()) {
				String nextKey = it.next();
				Map<String, String> map  = new HashMap<String, String>(); 
				List <EnumDto> list = enumList.get(nextKey);
				for (EnumDto o : list) {
					map.put(o.getValue(), o.getLabel());
				}
				enumMap.put(nextKey, map);
			}
		}
		return enumMap.get(key);
	}
	
	public static Map<String, List<EnumDto>> getEnumDto(Class<?> ... clazz) {
		Map<String, List<EnumDto>> map = new HashMap<String, List<EnumDto>>();
		for (Class<?> c : clazz) {
			map.put(c.getName(), enumList.get(c.getName()));
			
		}
		return map;
	}
	
	public static List<AppEnum> getAppEnum(Class<?> ... clazz) {
		List<AppEnum> list = new ArrayList<AppEnum>();
		for (Class<?> c : clazz) {
			AppEnum appEnum = new AppEnum();
			appEnum.setKey(c.getName());
			appEnum.setList(enumList.get(c.getName()));
			list.add(appEnum);
		}
		return list;
	}
	
	public static AppEnum getAppEnum(Class clazz) {
		AppEnum appEnum = new AppEnum();
		appEnum.setKey(clazz.getName());
		appEnum.setList(enumList.get(clazz.getName()));
		return appEnum;
	}
	
	public static Map<String, Map<String, String>> getEnumMaps(Class<?> ... clazz) {
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		for (Class<?> c : clazz) {
			map.put(c.getName(), EnumUtils.getEnumMap(c.getName()));
		}
		return map;
	}
	
	/**
	 * 获取指定枚举类的label
	 */
	public static String getEnumMapLabel(String key, Object value) {
		Map<String, String> map = enumMap.get(key);
		if (StringUtils.isNotNull(value)) {
			return map.get(value);
		} else {
			return "";
		}
	}
	
	/**
	 * 根据索引获取
	 * @param <T>
	 * @param clazz
	 * @param ordinal
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> clazz, int ordinal) {
		return (T)clazz.getEnumConstants()[ordinal];
	}


	/**
	 * 根据label值得到 对应vale的值
	 * @param enumType
	 * @param label
	 * @param <T>
	 * @return
	 */
	public static <T extends IEnumState> String  valueOf(Class<T> enumType, String label) {
		String val = null;
		T[] enumConstants = enumType.getEnumConstants();
		for (T t:enumConstants) {
			if (t.getLabel().equals(label)) {
				val = t.getValue();
			}
		}
		return val;
	}

	/**
	 * 根据枚举值得到枚举
	 * @param enumType
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T extends IEnumState> T getEnum(Class<T> enumType,String value) {
		T[] enumConstants = enumType.getEnumConstants();
		for (T t : enumConstants) {
			if (t.getValue().equals(value)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * 根据value值得到 对应label的值
	 * @param enumType
	 * @param value
	 * @param <T>
	 * @return
	 */
	public static <T extends IEnumState> String  labelOf(Class<T> enumType, String value) {
		String val = null;
		T[] enumConstants = enumType.getEnumConstants();
		for (T t:enumConstants) {
			if (t.getValue().equals(value)) {
				val = t.getLabel();
			}
		}
		return val;
	}
	
}
