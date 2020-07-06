package com.omysoft.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * sql工具类
 * 
 */
public class SqlUtils {

	// ---------------sql-----------------

	/**
	 * 拼接的sql包含逗号分隔符
	 * @param clazz
	 * @return 
	 */
	public static String getBeanContainDelimiterToSql(Class<?> clazz) {
		String defaultAlias = "t";
		return getBeanToSql(clazz, defaultAlias, null).toString();
	}

	public static String getPropertiesToSql(Class<?> clazz, String extraColumns) {
		String defaultAlias = "t";
		return getPropertiesToSql(clazz, defaultAlias, null, extraColumns);
	}

	public static String getPropertiesToSql(Class<?> clazz, String[] ignoreProperties, String extraColumns) {
		String defaultAlias = "t";
		return getPropertiesToSql(clazz, defaultAlias, ignoreProperties, extraColumns);
	}

	/**
	 * easyui 通过Sql方式多表联查 通过反射拿到那些@Column和@Table等注解，然后拿到里边的属性 进行拼接
	 * 
	 * @param clazz
	 *            需反射的对象
	 * @param alias
	 *            映射表的别名
	 * @param ignoreProperties
	 *            忽视的对象属性
	 * @param extraColumns
	 *            额外的查询条件
	 * @return
	 */
	public static String getPropertiesToSql(Class<?> clazz, String alias, String[] ignoreProperties, String extraColumns) {
		StringBuffer buffer = getBeanToSql(clazz, alias, ignoreProperties);
		String headCondition = buffer.append(extraColumns).append("").toString();
		if (extraColumns != null && !"".equals(extraColumns.trim())) {
			return headCondition;
		} else {
			return headCondition.substring(0, headCondition.length() - 1);
		}
	}

	/**
	 * @param clazz
	 *            需反射的对象
	 * @param alias
	 *            映射表的别名
	 * @param ignoreProperties
	 *            忽视的对象属性
	 * @return
	 */
	public static StringBuffer getBeanToSql(Class<?> clazz, String alias, String[] ignoreProperties) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select ");
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor property : propertyDescriptors) {
			String fieldName = property.getName();
			if (ignoreProperties == null || (!ignoreList.contains(fieldName))) {
				Method getter = property.getReadMethod();
				// 判断该方法是否包含Column注解
				if (getter.isAnnotationPresent(Column.class)) {
					// 获取该方法的Column注解实例
					Column column = getter.getAnnotation(Column.class);
					// 获取Column中的属性
					String name = column.name();
					buffer.append(" ").append(alias).append(".").append(name).append(" as ").append(fieldName).append(",");
				}
			}
		}
		return buffer;
	}

}
