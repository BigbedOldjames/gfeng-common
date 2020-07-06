package com.omysoft.common.treepro;



import java.util.Map;

/**
 * 自定义转换数据（JavaBean转Map）
 * @author yy
 * @date 2016年12月11日
 */
public interface INodeConverter<T> {
	
	/**
	 * JavaBean转Map
	 * @param t
	 * 		待转换的JavaBean实例
	 * @return
	 */
	public Map<String, Object> convert(T t);
	
}
