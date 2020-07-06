package com.omysoft.common.enums;

import com.omysoft.common.enums.IEnumState;

/**
 * 是否
 * @author yy
 * @date 2015-12-18
 */
public enum whetherType implements IEnumState {
	NO("0", "否"), YES("1", "是");
	
	private final String _value;
	private final String _label;

	whetherType(String value, String label) {
		_value = value;
		_label = label;
	}

	public String getValue() {
		return _value;
	}

	public String getLabel() {
		return _label;
	}
}
