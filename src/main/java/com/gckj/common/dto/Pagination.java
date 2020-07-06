package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gckj.common.utils.MyDateConvert;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @Description：easyui的datagrid向后台传递参数使用的model（采用泛型实现）
 * @author：ldc
 * date：2020-06-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination<T> {

	// 分页及排序参数
	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sort = null;// 排序字段名
	private String order = "asc";// 按什么排序(asc,desc)
	
	// 分页
	private List<T>  list= new ArrayList<T>();
	private Long count = 0L;
	
	public Pagination() {}
	
	public Pagination(int page, Long count) {
		this.page = page;
		this.count = count;
	}

	public Long getCount() {
		return count;
	}

	public List<T> getList() {
		return list;
	}

	public String getOrder() {
		return order;
	}

	public int getPage() {
		return page;
	}

	public int getRows() {
		return rows;
	}

	public String getSort() {
		return sort;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	/**
	 * 分页是否有效
	 */
	public boolean isDisabled() {
		return this.page == -1;
	}
	
	/**
	 * 是否进行总数统计
	 */
	public boolean isNotCount() {
		return this.count == -1;
	}
	
	
	/**
	 *生成order by规则 
	 */
	public String generateOrderBy() {
		StringBuffer buffer = new StringBuffer();
		String[] sortArr=null;
		String[] orderArr=null;
		if (!StringUtils.isBlank(sort)) {
			sortArr = sort.split(",");
		}
		if (!StringUtils.isBlank(order)) {
			orderArr = order.split(",");
		}
		if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order) && sortArr.length == orderArr.length) {
			buffer.append(" order by ");
			for(int i = 0, len = sortArr.length; i < len; i++){
				String sortField = sortArr[i];
				String orderValue = orderArr[i];
//				if (sortField.indexOf(".") < 1) {
//					sortField = "t." + sortField;
//				}
				if(i!=0){
					buffer.append(" , ");
				}
				buffer.append(sortField + " " + orderValue + " ");// 添加排序信息
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 返回一个对象
	 */
	public T uniqueResult() {
		T t = null;
		if (this.list.size() != 0) {
			t =  this.list.get(0);
		}
		return t;
	}
	
	public static <T> void copyProperties(Object data, Pagination<T> result) {
		if (result.uniqueResult() != null) {
			T t = result.uniqueResult();
			try {
				//建议采用spring BeanUtils.copyProperties，自动日期转换
				//apache BeanUtils.copyProperties默认情况下会将Ineger、Boolean、Long等基本类型包装类为null时的值复制后转换成0或者false
				ConvertUtils.register(new MyDateConvert(), Date.class);
				BeanUtils.copyProperties(data, t);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
}
