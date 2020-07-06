package com.gckj.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataEntity extends BaseEntity {
	
	private String remark;
	private String delFlag;
	private Long createById;
	private Date createTime;
	private Long updateById;
	private Date updateTime;

	@Column(name = "remark", length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "del_flag", length = 1)
	public String getDelFlag() {
		return this.delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "create_by_id")
	public Long getCreateById() {
		return this.createById;
	}

	public void setCreateById(Long createById) {
		this.createById = createById;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_by_id")
	public Long getUpdateById() {
		return this.updateById;
	}

	public void setUpdateById(Long updateById) {
		this.updateById = updateById;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "update_time", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/*@PrePersist
	public void prePersist(){
		UserDataScope user = UserConstants.getCurrentUser();
		if (this.createById == null) {
			if (user != null && user.getUserId() != null) {
				this.createById = user.getUserId();
				this.updateById = user.getUserId();
			}
		} else {
			this.updateById = this.createById;
		}
		if (this.updateTime == null) {
			this.updateTime = new Date();
		}
		if (this.createTime == null) {
			this.createTime = this.updateTime;
		}

	}
	
	@PreUpdate
	public void preUpdate(){
		UserDataScope user = UserConstants.getCurrentUser();
		if (this.createById == null) {
			if (user != null && user.getUserId() != null) {
				this.createById = user.getUserId();
				this.updateById = user.getUserId();
			}
		} else {
			if (user != null && user.getUserId() != null) {
				this.updateById = user.getUserId();
			}else{
				this.updateById = this.createById;
			}
		}
		this.updateTime = new Date();
	}*/
	
}
