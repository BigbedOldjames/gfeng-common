package com.omysoft.common.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.omysoft.common.entity.TreeEntity;



/**
 * ClassName: ZTreeNode
 * @Description: TODO
 * @author yy
 * @date 2014-9-18 下午2:38:31
 */
public class ZTreeNode extends AbstractTreeNode{
	public static final String LEAF_NODE = "isParent"; //zTree控件叶节点的标示符
	public static final Boolean LEAF_NODE_VALUE_TRUE = true; 
	public static final Boolean LEAF_NODE_VALUE_FALSE = false; 
	public static final List<String> CLASS_NAMES = Arrays.asList(new String[]{"java.lang.Boolean", "java.lang.Integer", "java.lang.Long", "java.lang.Double"});
	
	public ZTreeNode() {
	}

	public ZTreeNode(TreeEntity node) {
		setAttributes(node.attributeToZTree());
	}
	
	@Override
	public StringBuffer toBuffer(Map<String, Object> map, StringBuffer buffer) {
		int i = 0;
		buffer.append("{");
		for(String key : map.keySet()){
			if(i!=0){
				buffer.append(", ");
			}
			buffer.append("\"").append(key).append("\":");
			//判断类型
			Object obj = map.get(key);
			if (CLASS_NAMES.contains(obj.getClass().getName())) {
				buffer.append(obj);
			} else {
				buffer.append("\"").append(obj).append("\"");
			}
			i++;
		}
		buffer.append("}");			
		return buffer;
	}

	@Override
	public String toJsonArray() {
		List<AbstractTreeNode> child = this.getChildren();
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		buffer.append("[");
		if (!child.isEmpty()) {
			for(AbstractTreeNode node : child){
				if(i!=0){
					buffer.append(", ");
				}
				toBuffer(node.getAttributes(), buffer);
				i++;
			}
		}
		buffer.append("]");			
		return buffer.toString();
	}
	@Override
	public String toJsonObject() {
		StringBuffer buffer = new StringBuffer();
		return toBuffer(getAttributes(), buffer).toString();
	}

	@Override
	public String toJsonTree() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		if(this.hasChildren()){
			int i = 0;
			for(AbstractTreeNode node : this.getChildren()){
				Map<String, Object> map = node.getAttributes();
				if (i > 0){
					buffer.append(", ");
				}
				buffer.append("{");
				for (String key : map.keySet()) {
					if (!key.equals(LEAF_NODE)) {
						buffer.append("\"").append(key).append("\":");
						//判断类型
						Object obj = map.get(key);
						if (obj != null && CLASS_NAMES.contains(obj.getClass().getName())) {
							buffer.append(obj);
						} else {
							buffer.append("\"").append(obj).append("\"");
						}
						buffer.append(", ");
					}
				}
				if(!node.hasChildren()){
					buffer.append("\"").append(LEAF_NODE).append("\":").append(LEAF_NODE_VALUE_FALSE);
				}else{
					buffer.append("\"").append(LEAF_NODE).append("\":").append(LEAF_NODE_VALUE_TRUE).append(", ");
					buffer.append("\"children\": ");
					buffer.append(node.toJsonTree());
				}
				buffer.append("}");
				i++;
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public Integer getSequence() {
		Object sequence = getAttributes().get("sequence");
		return  sequence == null ? 0 : ((Integer)sequence);
	}

}
