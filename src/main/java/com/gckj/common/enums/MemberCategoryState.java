package com.omysoft.common.enums;

import com.omysoft.common.enums.IEnumState;


/**
 * 会员类别
 * @author yy
 * @date 2015-12-25
 */
public enum MemberCategoryState implements IEnumState {
	CONSIGNOR("0", "货主"), CARRIER("1", "车主");
	
	private final String _value;
	private final String _label;

	MemberCategoryState(String value, String label) {
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
