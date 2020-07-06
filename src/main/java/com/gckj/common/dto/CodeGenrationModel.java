package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * @Description：单号缓存对象
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeGenrationModel implements java.io.Serializable {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 2961412957763206050L;
	
	private String isCycDay; //是否按日期重置
	private Date effectiveDate; //有效日期
	private String dateReset; //有效期时间格式
	
	private List<String> queue = new ArrayList<String>(); //订单池

	public String getIsCycDay() {
		return isCycDay;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public String getDateReset() {
		return dateReset;
	}
	public List<String> getQueue() {
		return queue;
	}
	public void setIsCycDay(String isCycDay) {
		this.isCycDay = isCycDay;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public void setDateReset(String dateReset) {
		this.dateReset = dateReset;
	}
	public void setQueue(List<String> queue) {
		this.queue = queue;
	}
	
}