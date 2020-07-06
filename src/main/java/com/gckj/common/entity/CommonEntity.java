package com.gckj.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;

public class CommonEntity {
	
//	@Transient
	@TableField(exist = false)
	private Long memberId; //APP、门户传递的会员ID

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
