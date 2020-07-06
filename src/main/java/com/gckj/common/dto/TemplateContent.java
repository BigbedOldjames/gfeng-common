package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Description：表模板内容主题DTO
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateContent implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String value;
	private Long pId;
	private boolean checked = false;
	private Integer width;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getpId() {
		return pId;
	}

	public String getValue() {
		return value;
	}

	public Integer getWidth() {
		return width;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

}
