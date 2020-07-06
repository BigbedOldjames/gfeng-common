package com.omysoft.common.enums;

public enum IsEnabled implements IEnumState {

	ENABLE("0", "否"), PROHIBIT("1", "是");
	
	private final String _value;
	private final String _label;

	IsEnabled(String value, String label) {
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
