package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description：组织机构+角色 树形模型
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FunctionTreeNode {
	
	private Long id;
	private String name;
	private String url;
	private Integer sequence;
	private String target;
	private String iconCls;
	private Long parentId;
	private List<FunctionTreeNode> children = new ArrayList<FunctionTreeNode>();
	public List<FunctionTreeNode> getChildren() {
		return children;
	}
	public String getIconCls() {
		return iconCls;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Long getParentId() {
		return parentId;
	}
	public Integer getSequence() {
		return sequence;
	}
	public String getUrl() {
		return url;
	}
	public void setChildren(List<FunctionTreeNode> children) {
		this.children = children;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
