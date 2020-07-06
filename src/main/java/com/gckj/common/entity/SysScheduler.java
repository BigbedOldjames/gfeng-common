package com.gckj.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @Description：任务管理
 * @author：ldc
 * date：2020-06-23
 */
@Entity
@Table(name = "sys_scheduler")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysScheduler extends DataEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5021951911827612189L;
	private String name;
	private String schedulerName;
	private String jobName;
	private String jobGroup;
	private String jobClass;
	private String cronExpression;
	private String isEnabled;
	private String jobDataParam;

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "scheduler_name", length = 100)
	public String getSchedulerName() {
		return this.schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	@Column(name = "job_name", length = 100)
	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Column(name = "job_group", length = 100)
	public String getJobGroup() {
		return this.jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	@Column(name = "job_class", length = 100)
	public String getJobClass() {
		return this.jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	@Column(name = "cron_expression", length = 50)
	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	@Column(name = "is_enabled", length = 1)
	public String getIsEnabled() {
		return this.isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Column(name = "job_data_param", length = 100)
	public String getJobDataParam() {
		return jobDataParam;
	}

	public void setJobDataParam(String jobDataParam) {
		this.jobDataParam = jobDataParam;
	}
	

}