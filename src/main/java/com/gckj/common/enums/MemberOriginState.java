package com.omysoft.common.enums;

/**
 * 注册会员来源
 * @author yy
 * @date 2015-12-25
 */
public enum MemberOriginState implements IEnumState {
	PLATFORM("0", "运营平台"), PORTAL("1", "业务门户"), APP("2", "移动端APP");
	
	private final String _value;
	private final String _label;

	MemberOriginState(String value, String label) {
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
