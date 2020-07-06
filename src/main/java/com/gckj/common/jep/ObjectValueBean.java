package com.omysoft.common.jep;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.omysoft.common.utils.StringUtils;

public class ObjectValueBean implements IValuable {

	public Object object;
	public Map<String, Double> resultMap = new HashMap<String, Double>();

	public ObjectValueBean(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public Double getValue(String fieldName) throws Exception {
		if (resultMap.containsKey(fieldName)) {
			return resultMap.get(fieldName);
		} else {
			String fieldValue = BeanUtils.getProperty(object, fieldName);
			if (StringUtils.isNotNull(fieldValue)) {
				return new Double(fieldValue);
			} else {
			    return 0.0;
			}
		}
	}

	@Override
	public void setValue(String fieldName, Double fieldValue) throws Exception {
		BeanUtils.setProperty(object, fieldName, fieldValue);
		resultMap.put(fieldName, fieldValue);
	}
}
