package com.gckj.common.enums;

import java.util.List;

/**
     * @Description： app专属
     * @author：ldc
     * date：2020-06-23
     */
public class AppEnum {

	private String key;
	private List<EnumDto> list;

	public AppEnum() {
	}
	
	public AppEnum(String key, List<EnumDto> list) {
		this.key = key;
		this.list = list;
	}

	public String getKey() {
		return key;
	}

	public List<EnumDto> getList() {
		return list;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setList(List<EnumDto> list) {
		this.list = list;
	}

}
