package com.omysoft.common.enums;

import com.omysoft.common.enums.IEnumState;


/**
 * 会员类型
 * @author yy
 * @date 2015-12-25
 */
public enum MemberTypeState implements IEnumState {
	ENTERPRISE("0", "企业"), PERSONAL("1", "个人");
	
	private final String _value;
	private final String _label;

	MemberTypeState(String value, String label) {
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
