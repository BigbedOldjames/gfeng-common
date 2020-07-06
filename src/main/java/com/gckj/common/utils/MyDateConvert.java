package com.omysoft.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;


/**
 * 自定义日期转换器
 * 
 * @author yy
 * @date 2016-4-21
 */
public class MyDateConvert implements Converter {

	public Object convert(Class arg0, Object arg1) {
		if (arg1 == null) {
			return null;
		}
		if (arg1 instanceof Date) {
			return arg1;
		}
		if (arg1 instanceof String) {
			Date date = null;
			try {
				date = DateUtils.parseDate(arg1);
			} catch (Exception e) {
//				e.printStackTrace();
				try {
					SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
					date =  sd.parse(arg1.toString());
				} catch (Exception e2) {
					try {
						date = new Date(Long.parseLong(arg1.toString()));
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
			return date;
		}
		if (arg1 instanceof Long) {
			return new Date((Long)arg1);
		}
		return null;
	}

}
