package com.gckj.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gckj.common.entity.DataEntity;
/**
 * 
 * @Description：easyui的datagrid向后台传递参数使用的model
 * @author：ldc
 * date：2020-06-23
  */
@SuppressWarnings("rawtypes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page extends DataEntity {

	// 分页及排序参数
	private int pageNumber = 1;// 当前页
	private int pageSize = 10;// 每页显示记录数
	private String sort = null;// 排序字段名
	private String order = "asc";// 按什么排序(asc,desc)
	
	// 分页
	private List  list= new ArrayList();
	private Long count = 0L;

	public Page() {}
	
	public Page(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
	public Page(int pageNumber, Long count) {
		this.pageNumber = pageNumber;
		this.count = count;
	}
	
	/**
	 * restful传递分页参数转换
	 */
	public Page(Map<String, Object> map) {
		this.pageNumber = map.get("pageNumber") != null ? Integer.parseInt(map.get("pageNumber").toString()) : 0;
		this.pageSize = map.get("rows")!=null ? Integer.parseInt(map.get("rows").toString()) : 0;
	}
	
	public Page(Map<String, Object> map, Object object) {
		this.pageNumber = map.get("pageNo") != null ? (Integer) map.get("pageNo") : 0;
		this.pageSize = map.get("pageSize")!=null ? (Integer) map.get("pageSize") : 0;
	}

	public Long getCount() {
		return count;
	}

	public List getList() {
		return list;
	}

	public String getOrder() {
		return order;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public String getSort() {
		return sort;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	/**
	 * 分页是否有效
	 */
	public boolean isDisabled() {
		return this.pageNumber == -1;
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
				sortField = sortField.replace("*", ",");
				String orderValue = orderArr[i];
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
	public Object uniqueResult() {
		Object object = null;
		if (this.list.size() != 0) {
			object =  this.list.get(0);
		}
		return object;
	}
	
	/**
	 * 对象拷贝sql返回的结果，供spring mvc from标签使用，同时设置所需的值（id转name）
	 */
	@SuppressWarnings("unchecked")
	public static void copyProperties(Object data, Page result, ModelAndView mav, String...strings) {
		if (result.uniqueResult() != null) {
			Map<String, Object> params = (Map<String, Object>) result.uniqueResult();
			try {
				BeanUtils.copyProperties(data, params);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			for (String string : strings) {
				mav.addObject(string, params.get(string));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void copyProperties(Object data, Page result) {
		if (result.uniqueResult() != null) {
			Map<String, Object> params = (Map<String, Object>) result.uniqueResult();
			try {
				BeanUtils.copyProperties(data, params);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
	
}
