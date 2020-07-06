package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Description：easyui消息窗口模型，返回的JSON对象
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messager implements java.io.Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	public static String TITLE_DEFAULT = new String("成功提示：");
	public static String TTTLE_ERROR = new String("错误提示：");
	
	private String title;

	private boolean success = false;  // true: 成功 ,false:异常 或错误

	private String message;	//消息窗口提示信息

	private Object data = null;
	
	public Messager() {
	}
	
	public Messager(boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public Messager(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public Messager(boolean success, String title, String message, Object data) {
		this.success = success;
		this.title = title;
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public String getTitle() {
		return title;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
