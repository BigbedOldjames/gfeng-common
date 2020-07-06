package com.omysoft.common.jep;


public interface IValuable{
	
	/**
	 * 通过字段名称获取字段值
	 */
	public Object getValue(String fieldName)throws Exception;
	
	/**
	 * 设置字段名称和计算结果
	 */
	public void setValue(String fieldName, Double fieldValue)throws Exception;
}
