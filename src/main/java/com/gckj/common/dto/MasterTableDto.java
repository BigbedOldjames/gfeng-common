package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @Description：返回主子表结构的DTO
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MasterTableDto<A, B> {
	
	public final A data;
	public final B list;
	
	public MasterTableDto(A a, B b) {
		this.data = a;
		this.list = b;
	}
}
