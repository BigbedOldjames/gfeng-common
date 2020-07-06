package com.omysoft.common.enums;

import com.omysoft.common.enums.IEnumState;

/**
 *  文本类型
 * @author yy
 * @date 2016-8-8
 */
public enum TextTypeState implements IEnumState {
	FUNCTION("1", "文本"), BUTTON("2", "布尔值"), SJF("3", "其他"), SJDDF("4", "公式");
	
	private final String _value;
	private final String _label;

	TextTypeState(String value, String label) {
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
