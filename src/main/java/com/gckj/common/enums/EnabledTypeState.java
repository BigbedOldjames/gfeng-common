package com.gckj.common.enums;

/**
 * 
 * @Description：启用禁用状态
 * @author：ldc
 * date：2020-06-23
 */
public enum EnabledTypeState implements IEnumState {
	ENABLED("0", "启用"), DISABLED("1", "禁用");
	
	private final String _value;
	private final String _label;

	EnabledTypeState(String value, String label) {
		_value = value;
		_label = label;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public String getLabel() {
		return _label;
	}
}
