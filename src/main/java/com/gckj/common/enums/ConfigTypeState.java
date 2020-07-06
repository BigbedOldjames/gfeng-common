package com.gckj.common.enums;

/**
 * 
 * @Description：自定义配置类型
 * @author：ldc
 * date：2020-06-23
 */
public enum ConfigTypeState implements IEnumState {
	
	// ----------全局 默认1到100----------------
	FREE111("1", "全局"),
	BUS444Y11("50", "ALL车辆"),
	BUSY11("98", "LOGINK"),BUSY12("99", "短信"),
	// ----------TMS 默认101到200----------------
	FREE("101", "TMS托运单"), BUSY("102", "TMS配载单"), 
	BU11SY("103", "TMS中转单"), BU1122SY("104", "TMS送货单"), 
	BU1122fffSY("105", "TMS运单接收"), BU1122FFfffSY("106", "TMS交接单"),
	BU1122FFffFFFfSY("107", "TMS外包单"),
	
	// ----------WEB 默认201到300----------------
	FREE1131("201", "WEB预约订单"),FREE1411("202", "WEB物流运单"),
	FREE14FF11("211", "WEB收发客户"),
	
	// ----------门户 默认301到400----------------
	FREE1122231("301", "MH门户");
	
	private final String _value;
	private final String _label;

	ConfigTypeState(String value, String label) {
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
	
	public static String getLableByValue(String value) {
		String label = "";
		for (ConfigTypeState o : ConfigTypeState.values()) {
			if (o.getValue().equals(value)) {
				label = o.getLabel();
			}
		}
		return label;
	}
	
	public static String getValueByLable(String lable) {
		String value = "";
		for (ConfigTypeState o : ConfigTypeState.values()) {
			if (o.getLabel().equals(lable)) {
				value = o.getValue();
			}
		}
		return value;
	}
	
}
