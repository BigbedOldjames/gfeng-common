package com.omysoft.common.tree;

import java.util.List;

import com.omysoft.common.entity.TreeEntity;


/**
 * ClassName: JsonParser
 * 
 * @Description: TODO
 * @author yy
 * @date 2014-9-18 下午4:07:42
 */
public class JsonParser {

	/**
	 * @Description: 一次性全部加载 转化成多叉树 手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:19
	 */
	public static String fullToEasyUITree(List<TreeEntity> list) {
		if (list == null || list.size() < 1) {
			return "[{name:\"全部\",open:true}]";
		}
		ITreeParser parser = new EasyUITreeParser();
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"success\": true, \"data\" :").append(parser.parseToMultiTree(list, true).toJsonTree()).append("}");
		return buffer.toString();
	}
	
	/**
	 * @Description: 一次性全部加载 转化成多叉树 手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:19
	 */
	public static String fullSortedToEasyUITree(List<? extends TreeEntity> list) {
		if (list == null || list.size() < 1) {
			return "[{name:\"全部\",open:true}]";
		}
		ITreeParser parser = new EasyUITreeParser();
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"success\": true, \"data\" :").append(parser.parseToMultiTree(list, true).sort().toJsonTree()).append("}");
		return buffer.toString();
	}
	
	/**
	 * @Description: 一次性全部加载 转化成多叉树 手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:19
	 */
	public static String fullSortedToEasyUISubTree(List<? extends TreeEntity> list) {
		if (list == null || list.size() < 1) {
			return "[{name:\"全部\",open:true}]";
		}
		ITreeParser parser = new EasyUITreeParser();
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"success\": true, \"data\" :").append(parser.parseToMultiTree(list, false).sort().toJsonTree()).append("}");
		return buffer.toString();
	}
	
	/**
	 * @Description: 一次性全部加载 转化成多叉树 手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:19
	 */
	public static String fullSortedToEasyUITree(List<? extends TreeEntity> list, Long id) {
		if (list == null || list.size() < 1) {
			return "[{name:\"全部\",open:true}]";
		}
		ITreeParser parser = new EasyUITreeParser();
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"success\": true, \"data\" :").append(parser.parseToMultiTree(list, id).sort().toJsonTree()).append("}");
		return buffer.toString();
	}
	
	public static String fullSortedToUITree(List<? extends TreeEntity> list, Long id) {
		if (list == null || list.size() < 1) {
			return "[{name:\"全部\",open:true}]";
		}
		ITreeParser parser = new EasyUITreeParser();
		StringBuffer buffer = new StringBuffer();
		buffer.append(parser.parseToMultiTree(list, id).sort().toJsonTree());
		return buffer.toString();
	}

	/**
	 * @Description: 一次性全部加载 转化成多叉树并且排序  手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:19
	 */
	public static String fullToZTree(List<? extends TreeEntity> list) {
		if (list == null || list.size() < 1) {
			return "[{name:\"全部\",open:true}]";
		}
		ITreeParser parser = new ZTreeParser();
		return parser.parseToMultiTree(list, false).toJsonTree();
	}
	
	/**
	 * @Description: 一次性全部加载 转化成多叉树 并且排序 手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:19
	 */
	public static String fullSortedToZTree(List<? extends TreeEntity> list) {
		if (list == null || list.size() < 1) {
			return "[{name:\"全部\",open:true}]";
		}
		ITreeParser parser = new ZTreeParser();
		return parser.parseToMultiTree(list, false).sort().toJsonTree();
	}

	/**
	 * @Description: zTree逐级加载数据 手动序列化化为zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:01
	 */
	public static String proToEasyUITree(List<TreeEntity> list) {
		ITreeParser parser = new EasyUITreeParser();
		return parser.parseToArrayList(list).toJsonArray();
	}
	
	/**
	 * @Description: zTree逐级加载数据并且排序 手动序列化化为zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:01
	 */
	public static String proSortToEasyUITree(List<TreeEntity> list) {
		ITreeParser parser = new EasyUITreeParser();
		return parser.parseToArrayList(list).sort().toJsonArray();
	}

	/**
	 * @Description: zTree逐级加载数据 手动序列化化为zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:01
	 */
	public static String proToZTree(List<? extends TreeEntity> list) {
		ITreeParser parser = new ZTreeParser();
		return parser.parseToArrayList(list).toJsonArray();
	}
	
	/**
	 * @Description: zTree逐级加载数据并且排序 手动序列化化为zTree组件所需的JSON格式数据
	 * @param @param list
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:07:01
	 */
	public static String proSortToZTree(List<TreeEntity> list) {
		ITreeParser parser = new ZTreeParser();
		return parser.parseToArrayList(list).sort().toJsonArray();
	}

	/**
	 * @Description: zTree 树形节点对象 手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param object
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:06:32
	 */
	public static String toEasyUITree(TreeEntity object) {
		ITreeParser parser = new EasyUITreeParser();
		return parser.parseToObject(object).toJsonObject();
	}

	/**
	 * @Description: zTree 树形节点对象 手动序列化为 zTree组件所需的JSON格式数据
	 * @param @param object
	 * @param @return
	 * @return String
	 * @author yy
	 * @date 2014-9-18 下午4:06:32
	 */
	public static String toZTree(TreeEntity object) {
		ITreeParser parser = new ZTreeParser();
		return parser.parseToObject(object).toJsonObject();
	}

}
