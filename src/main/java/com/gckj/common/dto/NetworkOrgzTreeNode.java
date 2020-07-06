package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description：组织机构+网点 树形模型
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkOrgzTreeNode {

    public static String NETWORK_FLAG = "1"; //类型为1 代表网点
    public static String ORGZ_FLAG = "2"; //类型为2 代表组织机构

    private Long id;
    private String name;
    private boolean open = true;
    private Integer sequence;
    private Long parentId;
    private String type;   //类型 （用于组织机构+网点合并生成网点树的区分节点是机构还是网点）
    private List<NetworkOrgzTreeNode> children = new ArrayList<>();
    private String checked;
    
    public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public List<NetworkOrgzTreeNode> getChildren() {
        return children;
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
    public String getType() {
        return type;
    }
    public boolean isOpen() {
        return open;
    }
    public void setChildren(List<NetworkOrgzTreeNode> children) {
        this.children = children;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    public void setType(String type) {
        this.type = type;
    }
}
