package com.gckj.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity extends CommonEntity{


	@TableId(type = IdType.AUTO)
	private Long id;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 获取List内元素的id,返回ids 以逗号连接
	 * 静态方法具备泛型能力，<T> + 返回值 + 方法名
	 * 注意在返回值的<T> 做参数类型的限定，而不是在形参上
	 */
	public static <T extends BaseEntity> String getIds(List<T> list) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0, len = list.size(); i < len; i++) {
			if (i != 0) {
				buffer.append(",");
			}
			buffer.append(list.get(i).getId());
		}
		return buffer.toString();
	}
	
	/**
	 * 返回Map<K,V>，k：实体类ID,value：实体对象
	 */
	public static <T extends BaseEntity> Map<Long, T> getMap(List<T> list) {
		Map<Long, T> map = new HashMap<Long, T>(); 
		for(T t : list){
			map.put(t.getId(), t);
		}
		return map;
	}
	
	/**
	 * 返回存放id的list容器
	 */
	public static <T extends BaseEntity> List<Long> getIdList(List<T> list) {
		List<Long> idList = new ArrayList<Long>();
		for(T t : list){
			idList.add(t.getId());
		}
		return idList;
	}
}
