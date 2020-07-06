package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @Description：返回2个不同/相同类型对象,使用extend扩展返回对象的个数
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TowTupple<A, B> {
	
	public final A NotSelected;
	public final B Selected;
	
	public TowTupple(A a, B b) {
		this.NotSelected = a;
		this.Selected = b;
	}
}
