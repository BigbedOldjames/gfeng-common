package com.omysoft.common.enums;

/**
 * 菜单类型
 * @author yy
 * @date 2016-4-29
 */
public enum FunctionType implements IEnumState {
	FUNCTION("1", "菜单"), BUTTON("2", "按钮");
	
	private final String _value;
	private final String _label;

	FunctionType(String value, String label) {
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
