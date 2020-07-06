package com.omysoft.common.treepro;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 通用Tree
 * @author yy
 * @date 2016年12月11日
 */
public class GeneralTreeNode {
	
	private List<GeneralTreeNode> children = new ArrayList<GeneralTreeNode>(); //子节点
	private Map<String, Object> data; //自定义数据
	private Integer sequence;	//排序
	
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public List<GeneralTreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<GeneralTreeNode> children) {
		this.children = children;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
