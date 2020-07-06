package com.omysoft.common.treepro;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.alibaba.fastjson.JSON;
import com.omysoft.common.entity.TreeEntity;
import com.omysoft.common.utils.StringUtils;

/**
 * Tree转换、解析、排序等工具类
 * @author yy
 * @date 2016年12月12日
 */
public class TreeUtils {
	
	public static final String children_alias = "childrenAlias"; //子节点标识符
	public static final String node_state_key_alias = "nodeStateKeyAlias"; //节点标示符
	public static final String node_state_value_alias = "nodeStateValueAlias"; //对应的值
	public static final List<String> CLASS_NAMES = Arrays.asList(new String[]{"java.lang.Boolean", "java.lang.Integer", "java.lang.Long", "java.lang.Double"});

	/**
	 * 获取ztree自定义转换所需的键值对
	 * @return
	 */
	public static Map<String, Object> getZtreeAliasMap() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(TreeUtils.children_alias, "children");
		m.put(TreeUtils.node_state_key_alias, "isParent");
		m.put(TreeUtils.node_state_value_alias, "true,false");
		return m;
	}
	
	/**
	 * 获取easyui tree自定义转换所需的键值对
	 * @return
	 */
	public static Map<String, Object> getEasyUIAliasMap() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(TreeUtils.children_alias, "children");
		m.put(TreeUtils.node_state_key_alias, "state");
		m.put(TreeUtils.node_state_value_alias, "\"closed\",\"open\"");
		return m;
	}
	
	
	
	
	//---------------------
	
	/**
	 * List转通用Tree
	 * @param list
	 * 			待转换的数据
	 * @param parse
	 * 			自定义属性转换器
	 * @return
	 */
	public static <T> GeneralTreeNode createTree(List<T> list, INodeConverter<T> parse){
		GeneralTreeNode root = new GeneralTreeNode();
		root.setData(new HashMap<String, Object>());
		Map<Long, GeneralTreeNode> treeNodeMap = new HashMap<Long, GeneralTreeNode>();
		for (T t : list){
			TreeEntity obj = (TreeEntity)t; 
			GeneralTreeNode treeNode = new GeneralTreeNode();
			treeNode.setData(parse.convert(t));
			treeNode.setSequence(obj.getSequence() == null ? 0 : obj.getSequence());
			treeNodeMap.put(obj.getId(), treeNode);
		}
		
		for(T t : list){
			TreeEntity obj = (TreeEntity)t; 
			GeneralTreeNode parent = treeNodeMap.get(obj.getParentId());
			GeneralTreeNode treeNode = treeNodeMap.get(obj.getId());
			if (parent == null) {
				root.getChildren().add(treeNode);
			} else {
				parent.getChildren().add(treeNode);
			}
		}
		return root;
	}
	
	/**
	 * 排序（按层次遍历多叉树）
	 * @param root
	 * 			通用Tree根节点
	 * @return
	 */
	public static GeneralTreeNode sortASCTree(GeneralTreeNode root){
		return sortTree(root, new Comparator<GeneralTreeNode>() {// 生序排序
			public int compare(GeneralTreeNode o1, GeneralTreeNode o2) {
				return o1.getSequence().compareTo(o2.getSequence());
			}
		});
	}
	
	/**
	 * 排序（按层次遍历多叉树）
	 * @param root
	 * 			通用Tree根节点
	 * @return
	 */
	public static GeneralTreeNode sortDESCTree(GeneralTreeNode root){
		return sortTree(root, new Comparator<GeneralTreeNode>() {// 生序排序
					public int compare(GeneralTreeNode o1, GeneralTreeNode o2) {
						return o2.getSequence().compareTo(o1.getSequence());
					}
				});
	}
	
	
	/**
	 * 排序（按层次遍历多叉树）
	 * @param root
	 * 			通用Tree根节点
	 * @return
	 */
	public static GeneralTreeNode sortTree(GeneralTreeNode root, Comparator<GeneralTreeNode> comparator){
		GeneralTreeNode rootTreeNode = root;
		Queue<GeneralTreeNode> queue = new LinkedList<GeneralTreeNode>();
		queue.offer(rootTreeNode);
		while(queue.size() != 0){
			GeneralTreeNode node = queue.poll();
			List<GeneralTreeNode> list = node.getChildren();
			if(!list.isEmpty()){
				for(GeneralTreeNode no : list){
					queue.offer(no);
				}
				Collections.sort(list, comparator);
			}
		}
		return rootTreeNode;
	}
	
	/**
	 * 手动序列化根节点返回tree型JSON
	 * @param root
	 * 		通用Tree根节点
	 * @return
	 */
	public static String toJSONTree(GeneralTreeNode root) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		List<GeneralTreeNode> list = root.getChildren();
		if(!list.isEmpty()){
			int i = 0;
			for(GeneralTreeNode node : list){
				Map<String, Object> map = node.getData();
				if (i > 0){
					buffer.append(", ");
				}
				buffer.append("{");
				for (String key : map.keySet()) {
					//排除系统默认的key
					if (key.equals(children_alias) || key.equals(node_state_key_alias) || key.equals(node_state_value_alias)
							|| key.equals(map.get(node_state_key_alias))) {
						continue;
					} else {
						buffer.append("\"").append(key).append("\":");
						//判断类型 待扩展-支持Map、JaveBean
						Object obj = map.get(key);
						if (obj != null && CLASS_NAMES.contains(obj.getClass().getName())) {
							buffer.append(obj);
						} else if (obj != null && obj instanceof String) {
							buffer.append("\"").append(obj).append("\"");
						} else {
							buffer.append(JSON.toJSONString(obj)); //fastjson序列化Map、List、JavaBean等
						}
						buffer.append(", ");
					}
				}
				String[] stateArray = map.get(node_state_value_alias).toString().split(",");
				//判断当前节点是否有子节点
				if(node.getChildren().isEmpty()){
					//判断是否 传入节点状态（没传入值+null、空值的情况，系统默认节点打开、关闭状态）
					if (map.get(map.get(node_state_key_alias)) != null) {
						buffer.append("\"").append(map.get(node_state_key_alias)).append("\":").append(map.get(map.get(node_state_key_alias)));
					} else {
						buffer.append("\"").append(map.get(node_state_key_alias)).append("\":").append(stateArray[1]);
					}
				}else{
					//判断是否 传入节点状态（没传入值+null、空值的情况，系统默认节点打开、关闭状态）
					if (map.get(map.get(node_state_key_alias)) != null) {
						buffer.append("\"").append(map.get(node_state_key_alias)).append("\":").append(map.get(map.get(node_state_key_alias))).append(", ");
					} else {
						buffer.append("\"").append(map.get(node_state_key_alias)).append("\":").append(stateArray[0]).append(", ");
					}
					buffer.append("\"").append(map.get(children_alias)).append("\": ");
					buffer.append(toJSONTree(node));
				}
				buffer.append("}");
				i++;
			}
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	/**
	 * 手动序列化根节点返回combobox型JSON
	 * @param root
	 * 		通用Tree根节点
	 * treeSt 请传空，用于子列前缀
	 * @return
	 */
	public static String toJSONComboboxTree(GeneralTreeNode root,String treeSt) {
		StringBuffer buffer = new StringBuffer();
		boolean flag = false;
		if(treeSt==null||"".equals(treeSt)){
			treeSt = "└";
			buffer.append("[");
			flag = true;
		}
		String origin = treeSt;
		List<GeneralTreeNode> list = root.getChildren();
		if(!list.isEmpty()){
			int i = 0;
			for(GeneralTreeNode node : list){
				Map<String, Object> map = node.getData();
				if (i > 0){
					buffer.append(",");
				}
				buffer.append("{");
				for (String key : map.keySet()) {
					buffer.append("\"").append(key).append("\":");
					//判断类型 待扩展-支持Map、JaveBean
					Object obj = map.get(key);
					if("text".equals(key)){
						buffer.append("\"").append(origin).append(obj).append("\"");
					}else{
						if (obj != null && CLASS_NAMES.contains(obj.getClass().getName())) {
							buffer.append(obj);
						} else if (obj != null && obj instanceof String) {
							buffer.append("\"").append(obj).append("\"");
						} else {
							buffer.append(JSON.toJSONString(obj)); //fastjson序列化Map、List、JavaBean等
						}
					}
					buffer.append(",");
				}
				if(!node.getChildren().isEmpty()){
					buffer.append("\"").append("isParent").append("\":").append("\"").append(true).append("\"");
					buffer.append("}");
				}else{
					buffer.substring(0, buffer.lastIndexOf(",")-1);
					buffer.append("}");
				}
				
				//判断当前节点是否有子节点
				if(!node.getChildren().isEmpty()){
					treeSt="  ├";
					buffer.append(",");
					buffer.append(toJSONComboboxTree(node,treeSt));
				}
				i++;
			}
		}
		if(flag){
			buffer.append("]");
		}
		return buffer.toString();
	}
	
	/**
	 * tree型JSON
	 * @param list
	 * 			待转换的数据
	 * @param parse
	 * 			自定义属性转换器
	 * @return
	 */
	public static <T> String toSortedJSONTree(List<T> list, INodeConverter<T> parse){
		GeneralTreeNode root = createTree(list, parse);
		sortDESCTree(root);
		return toJSONTree(root);
	}
	
	/**
	 * tree型JSON
	 * @param list
	 * 			待转换的数据
	 * @param parse
	 * 			自定义属性转换器
	 * @return
	 */
	public static <T> String toSortedASCJSONTree(List<T> list, INodeConverter<T> parse){
		GeneralTreeNode root = createTree(list, parse);
		sortASCTree(root);
		return toJSONTree(root);
	}
	
	/**
	 * tree型JSON
	 * @param list
	 * 			待转换的数据
	 * @param parse
	 * 			自定义属性转换器
	 * @return
	 */
	public static <T> String toASCSortedJSONTree(List<T> list, INodeConverter<T> parse){
		GeneralTreeNode root = createTree(list, parse);
		sortASCTree(root);
		return toJSONTree(root);
	}
	
	/**
	 * 
	 * @param list
	 * 			待转换的数据
	 * @param parse
	 * 			自定义属性转换器
	 * @return
	 */
	public static <T> List<Map<String, Object>> toSortedTreeList(List<T> list, INodeConverter<T> parse){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		GeneralTreeNode root = createTree(list, parse);
		sortASCTree(root);
		getNodeDataList(root, result);
		return result;
	}
	
	/**
	 * 左序递归遍历tree
	 * @param root
	 * @param list
	 */
	private static void getNodeDataList(GeneralTreeNode root, List<Map<String, Object>> list) {
		if (!root.getChildren().isEmpty()) {
			for (GeneralTreeNode o : root.getChildren()) {
				list.add(o.getData());
				getNodeDataList(o, list);
			}
		}
	}
	
	public static List<Map<String, Object>> toArrayList(GeneralTreeNode root) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		getNodeDataList(root, result);
		return result;
	}
	
	/**
	 * 获取tree节点的数据集，重新转换成tree
	 * @param root 
	 */
	public static List<Map<String, Object>> toMapTree(GeneralTreeNode root) {
		if (root.getData() == null) {
			root.setData(new HashMap<String, Object>());
		}
		getNodeDataList(root, root.getData());
		return (List<Map<String, Object>>) root.getData().get("children");
	}
	
	private static void getNodeDataList(GeneralTreeNode root, Map<String, Object>map) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (!root.getChildren().isEmpty()) {
			for (GeneralTreeNode o : root.getChildren()) {
				list.add(o.getData());
				map.put("children", list);
				getNodeDataList(o, o.getData());
			}
		}
	}
	
	
	public static GeneralTreeNode getSubTreee(GeneralTreeNode root, String searchValue, String searchKey) {
		GeneralTreeNode cur = new GeneralTreeNode();
		GeneralTreeNode rootTreeNode = root;
		if (root.getData() == null) {
			root.setData(new HashMap<String, Object>());
		}
		Queue<GeneralTreeNode> queue = new LinkedList<GeneralTreeNode>();
		queue.offer(rootTreeNode);
		while(queue.size() != 0){
			GeneralTreeNode node = queue.poll();
			String str = StringUtils.toStringDefaultEmpty(node.getData().get(searchKey));
			if (searchValue.equals(str)) {
				cur = node;
				break;
			}
			List<GeneralTreeNode> list = node.getChildren();
			if(!list.isEmpty()){
				for(GeneralTreeNode no : list){
					queue.offer(no);
				}
			}
		}
		GeneralTreeNode rs = new GeneralTreeNode();
		List<GeneralTreeNode> lll = new ArrayList<GeneralTreeNode>();
		lll.add(cur);
		rs.setChildren(lll);
		rs.setData(new HashMap<String, Object>());
		return rs;
	}
	
}
