package com.gckj.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;
import org.springframework.beans.factory.BeanDefinitionStoreException;

public class ExpressionEvaluator {

	public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

	public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

	public static String evaluate(String param, Map<String, Object> map) {
		return parseStringValue(param, map);

	}

	protected static String parseStringValue(String strVal, Map<String, Object> props) {
		StringBuffer buf = new StringBuffer(strVal);
		int startIndex = strVal.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
		while (startIndex != -1) {
			int endIndex = buf.toString().indexOf(DEFAULT_PLACEHOLDER_SUFFIX,
					startIndex + DEFAULT_PLACEHOLDER_PREFIX.length());
			if (endIndex != -1) {
				String placeholder = buf.substring(startIndex + DEFAULT_PLACEHOLDER_PREFIX.length(), endIndex);
				String propVal = resolvePlaceholder(placeholder, props);
				if (propVal != null) {
					buf.replace(startIndex, endIndex + DEFAULT_PLACEHOLDER_SUFFIX.length(), propVal);

					startIndex = buf.toString().indexOf(DEFAULT_PLACEHOLDER_PREFIX, startIndex + propVal.length());
				}

				else {
					throw new BeanDefinitionStoreException("Could not resolve placeholder '" + placeholder + "'");
				}
			} else {
				startIndex = -1;
			}
		}

		return buf.toString();
	}

	@SuppressWarnings("unchecked")
	protected static String resolvePlaceholder(String param, Map<String, Object> props) {
		Expression aExpression;
		try {
			aExpression = ExpressionFactory.createExpression(param);
			JexlContext jc = JexlHelper.createContext();
			jc.getVars().putAll(props);
			Object o = aExpression.evaluate(jc);
			return o.toString();
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	// public static String eval(String path, VariableResolver resolver) {
	// return null;
	// }

	public static String evaluate(String paramString, String name, String realPath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(name, realPath);
		return resolvePlaceholder(paramString, map);

	}

}
