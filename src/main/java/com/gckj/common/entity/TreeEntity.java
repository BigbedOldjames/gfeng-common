package com.gckj.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * @Description：所有tree型实体类都继承该类
 * @author：ldc
 * date：2020-06-23
 */
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TreeEntity extends DataEntity {
	
	public static String  SPLIT_DELIMITER = ","; //分隔符
	
	private Long parentId;
	private String parentIds;
	private String name;
	private Integer sequence;	//easyui TreeGrid向后台传递参数sort,为防止冲突原sort改成sequence
	private String state;	//easyui TreeGrid、Tree、ComboTree实现异步加载数据(这里特别针对easyui)
	
	@Column(name = "parent_id")
	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "parent_ids", length = 100)
	public String getParentIds() {
		return this.parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "sequence")
	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	

	@Column(name = "state", length = 6)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
     * @Description：zTree 获取对象的属性集
     * @author：ldc
     * date：2020-06-23	 
	 * @param @return
	 * @return Map
	 */
	public abstract Map<String, Object> attributeToZTree();

	/**
     * @Description：EasyUI 获取对象的属性集
     * @author：ldc
     * date：2020-06-23
   	 * @param @return
	 * @return Map<String,Object>
    */
	public abstract Map<String, Object> attributeToEasyUITree();

	/**
	 * 当前parentIds编码生成规则: 父parentIds+当前id+逗号 或者逗号+id+逗号
	 * 输入的parentIds代表父对象的parentIds
	 */
	public String generateParentIds(String parentIds){
		String curParentIds = null;
		if (null == getParentId()) {
			curParentIds = TreeEntity.SPLIT_DELIMITER + getId() + TreeEntity.SPLIT_DELIMITER;
		} else {
			curParentIds = parentIds + getId() + TreeEntity.SPLIT_DELIMITER;
		}
		return curParentIds;
	}
	
	/**
	 * 返回Map<K,V>，k：实体类ID,value：实体对象name
	 */
	public static <T extends TreeEntity> Map<Long, Object> getIdNameMap(List<T> list) {
		Map<Long, Object> map = new HashMap<Long, Object>(); 
		for(T t : list){
			map.put(t.getId(), t.getName());
		}
		return map;
	}
	
	public static String getHeadquarterIds(String parentIds) {
		return getHeadquarterIds(parentIds, 3);
	}
	
	/**
	 * 获取指定层公司的parentIds
	 */
	public static String getHeadquarterIds(String parentIds, int  Level) {
		String[] idArray = parentIds.split(TreeEntity.SPLIT_DELIMITER);
		StringBuffer buffer = new StringBuffer();
		System.out.println(idArray.length);
		for (int i = 0, len = Level; i < len; i++) {
			buffer.append(idArray[i]).append(TreeEntity.SPLIT_DELIMITER);
		}
		return buffer.toString();
	}
	
//	public static void main(String[] args) {
//		String parentIds = getHeadquarterIds(",19,27,28,", 2);
//		System.out.println(parentIds);
//	}
}
