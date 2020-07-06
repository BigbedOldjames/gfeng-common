package com.omysoft.common.enums;

/**
 *  
 * @Description：枚举类DTO,用于向Spring MVC form:options标签遍历元素提供getter、setter方法
 * @author：ldc
 * date：2020-06-23
 */
public class EnumDto {
	
	private String label;
	private String value;

	public EnumDto() {
	}

	public EnumDto(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
