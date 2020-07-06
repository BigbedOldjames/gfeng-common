package com.omysoft.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PageSortUtils {

	private PageSortUtils() {
	}
	/**
	 * 根据Date类型字段进行排序
	 * @param targetList
	 * 			传入的值
	 * @param sortField
	 * 			字段名
	 * @param sortMode
	 * 			排序方式
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> void sort(List<T> targetList, final String sortField, final String sortMode) {
		Collections.sort(targetList, new Comparator<T>() {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			public int compare(T arg0, T arg1) {
				int mark = 1;
				try {
					String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;
					Date date1 = (Date) getFieldValue(arg0, sortField);
					String strDate1 = sdf.format(date1);
					Date date2 = (Date) getFieldValueByMethod(arg1, methodStr);
					String strDate2 = sdf.format(date2);
					if (sortMode != null && "desc".equals(sortMode)) {
						mark = strDate2.compareTo(strDate1);
					} else {
						mark = strDate1.compareTo(strDate2);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return mark;
			}
		});
	}
	
	/**
	 * 根据String类型字段进行排序
	 * @param targetList
	 * 			传入的值
	 * @param sortField
	 * 			字段名
	 * @param sortMode
	 * 			排序方式
	 */
	public static <T> void sortStringType(List<T> targetList, final String sortField, final String sortMode) {
		Collections.sort(targetList, new Comparator<T>() {
			public int compare(T arg0, T arg1) {
				int mark = 1;
				try {
					String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;
					String string1 = (String) getFieldValue(arg0, sortField);
					String string2 = (String) getFieldValueByMethod(arg1, methodStr);
					if (null != string1 && null != string2) {
						if (sortMode != null && "desc".equals(sortMode)) {
							mark = string2.compareTo(string1);
						} else {
							mark = string1.compareTo(string2);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				return mark;
			}
		});
	}

	public static <T> Object getFieldValue(T obj, String fieldName) throws Exception {
		List<Field> fieldList = new ArrayList<Field>();
		Class<?> tempClass = obj.getClass();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}
		for (Field f : fieldList) {
			if (f.getName().equals(fieldName)) {
				f.setAccessible(true);//为true可以访问类的私有变量
				return f.get(obj);
			}
		}
		return null;
	}

	public static <T> Object getFieldValueByMethod(T obj, String fieldName) throws Exception {
		Method method = obj.getClass().getMethod(fieldName, null);
		return method.invoke(obj, null);
	}
	
}
