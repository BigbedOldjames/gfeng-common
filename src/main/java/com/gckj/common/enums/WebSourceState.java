package com.omysoft.common.enums;

import java.util.ArrayList;
import java.util.List;


/**
 * 货源发布/预约订单状态
 * @author yy
 * @date 2015-12-25
 */
public enum WebSourceState implements IEnumState {
	
	PENDING("0", "待处理"), ASSIGNMENT("1","已分派"),ALREADYRECEIVE("2","已接收"), RETURN("100","已退回"),
	//短驳业务(4:已派车>已到货)
	BAR112GE("3","已通知短驳"),BARGE("4","已到货"),ACCEPT("5", "提货完成"),BARGESIGN("6", "短驳签收"),BARGRECEIPT("7", "短驳回单"),SEXAMINE("8","预约已审核"),
	//物流订单
	BOOKPENDING("10", "已开单"),EXAMINE("9","已审核"),
	APP("11", "已分派"), DELIVERY("12","已接收"),
	LD("13","配载完毕"), LDSD("14","在途"), SDA("15","已到达"), SSDSSDA("16","已卸货"),
	SFDS("17","已送货"),CONFIRMED("18","已签收"), SSD("19","已中转"),
	
	//运营平台间外包业务
	OUTSOURCED("90","已外包"), SDAS("91","外包已接收"),
	
	//门户端批量发货预发货
	PREPARE("95","预发货"),
	
	//分段状态
	INTERCHANGE("98","已交接");
	
	private final String _value;
	private final String _label;
	
	WebSourceState(String value, String label) {
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
	
	/**
	 * 可加载预约订单的状态数组
	 */
	public static String[] getUnbilledStates() {
		String[] states = {SEXAMINE.getValue(), BAR112GE.getValue(), BARGE.getValue(),ACCEPT.getValue()};
		return states;
	}
	/**
	 * 结算中心订单状态
	 */
	public static List<EnumDto> getOrdertatus() {
		List<EnumDto> list = new ArrayList<EnumDto>();
		EnumDto [] enumDto ={new EnumDto(BOOKPENDING.getValue(),BOOKPENDING.getLabel()),
				new EnumDto(APP.getValue(),APP.getLabel()),
				new EnumDto(DELIVERY.getValue(),DELIVERY.getLabel()),
				new EnumDto(LD.getValue(),LD.getLabel()),
				new EnumDto(LDSD.getValue(),LDSD.getLabel()),
				new EnumDto(SDA.getValue(),SDA.getLabel()),
				new EnumDto(SSDSSDA.getValue(),SSDSSDA.getLabel()),
				new EnumDto(CONFIRMED.getValue(),CONFIRMED.getLabel()),
				new EnumDto(BARGE.getValue(),BARGE.getLabel()),
				new EnumDto(ACCEPT.getValue(),ACCEPT.getLabel())
		};
		for (int i = 0; i < enumDto.length; i++) {
			list.add(enumDto[i]);
		}
		return list;
	}
	
}
