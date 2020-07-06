package com.omysoft.common.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


/**
 * ClassName: TreeNode
 * @Description: 抽象类 多叉树数据结构
 * @author yy
 * @date 2014-9-16 上午10:54:42
 */
abstract  class AbstractTreeNode{
	
	private AbstractTreeNode parent = null;
	private List<AbstractTreeNode> children = new ArrayList<AbstractTreeNode>();
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public void addChild(AbstractTreeNode treeNode) {
		treeNode.setParent(this);
		children.add(treeNode);
	}
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public List<AbstractTreeNode> getChildren() {
		return children;
	}
	
	public AbstractTreeNode getParent() {
		return parent;
	}
	
	public abstract Integer getSequence();
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	public void setAttribute(String name, String value) {
		attributes.put(name, value);
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public void setChildren(List<AbstractTreeNode> children) {
		this.children = children;
	}

	public void setParent(AbstractTreeNode parent) {
		this.parent = parent;
	}
	
	
	/**
	 * 按层排序（层次遍历多叉树）
	 * @param list
	 * @return
	 */
	public AbstractTreeNode sort(){
		AbstractTreeNode root = this;
		Queue<AbstractTreeNode> queue = new LinkedList<AbstractTreeNode>();
		queue.offer(root);
		while(queue.size() != 0){
			AbstractTreeNode node = queue.poll();
			if(node.hasChildren()){
				for(AbstractTreeNode no : node.getChildren()){
					queue.offer(no);
				}
				Collections.sort(node.getChildren(), new Comparator<AbstractTreeNode>() {// 排序
					public int compare(AbstractTreeNode o1, AbstractTreeNode o2) {
						return o1.getSequence().compareTo(o2.getSequence());
					}
				});
			}
		}
		return root;
	}

	
	/**
	 * @Description: StringBuffer 对象保存单个对象序列化信息
	 * @param @param map
	 * @param @param buffer
	 * @param @return   
	 * @return StringBuffer  
	 * @author yy
	 * @date 2014-9-18 下午2:51:19
	 */
	public abstract StringBuffer toBuffer(Map<String, Object> map, StringBuffer buffer);

	
	/**
	 * @Description: 多叉树序列化成层次结构的json(用于树的逐级加载)
	 * @param @return   
	 * @return String  
	 * @author yy
	 * @date 2014-9-18 下午2:25:59
	 */
	public abstract String toJsonArray();
	
	
	/**
	 * @Description:  多叉树的某个节点序列化成json
	 * @param @return   
	 * @return String  
	 * @author yy
	 * @date 2014-9-18 下午2:26:49
	 */
	public abstract String toJsonObject();
	
	/**
	 * @Description: 多叉树序列化成树形json（递归/非递归方式实现）
	 * @param @return   
	 * @return String  
	 * @author yy
	 * @date 2014-9-18 下午2:27:34
	 */
	public abstract String toJsonTree();

}
