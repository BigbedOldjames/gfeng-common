package com.gckj.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description：easyui datagrid 属性返回模型
 * @author：ldc
 * date：2020-06-23
 */
@SuppressWarnings("rawtypes")
public class Datagrid {
	private List  rows= new ArrayList();
	private Long total = 0L;
	private List footer = new ArrayList();
	
	public Datagrid() {
	}
	
	@SuppressWarnings("unchecked")
	public Datagrid(Map map) {
		footer.add(map);
	}
	
	public Datagrid(Page result) {
		this.rows = result.getList();
		this.total = result.getCount();
	}
	
	public List getFooter() {
		return footer;
	}
	public List getRows() {
		return rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setFooter(List footer) {
		this.footer = footer;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
}
