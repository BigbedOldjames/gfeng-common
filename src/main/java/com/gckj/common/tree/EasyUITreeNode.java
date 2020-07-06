package com.omysoft.common.tree;

import java.util.List;
import java.util.Map;

import com.omysoft.common.entity.TreeEntity;



/**
 * ClassName: ZTreeNode
 * @Description: TODO
 * @author yy
 * @date 2014-9-18 下午2:38:31
 */
public class EasyUITreeNode extends AbstractTreeNode{
	public static final String LEAF_NODE = "state"; // 叶节点easyUI控件的标示符
	public static final String LEAF_NODE_VALUE = "open";
	
	public EasyUITreeNode() {
	}

	public EasyUITreeNode(TreeEntity node) {
		setAttributes(node.attributeToEasyUITree());
	}
	
	@Override
	public StringBuffer toBuffer(Map<String, Object> map, StringBuffer buffer) {
		int i = 0;
		buffer.append("{");
		for(String key : map.keySet()){
			if(i != 0){
				buffer.append(", ");
			}
			if (key.equals("attributes")) {
				buffer.append("\"attributes\": {");
				Map map1 = (Map) map.get(key);
				int j = 0;
				for(Object key1 : map1.keySet()){
					if(j != 0){
						buffer.append(", ");
					}
					buffer.append("\"").append((String)key1).append("\": \"").append(map1.get(key1)).append("\"");
					j++;
				}
				buffer.append("}");	
			}else{
				buffer.append("\"").append(key).append("\": \"").append(map.get(key)).append("\"");
			}
			i++;
		}
		/*
		 * state：节点状态，'open' 或 'closed'，默认：'open'。在设置为'closed'的时候，当前节点的子节点将会从远程服务器加载他们。
		 * 用于tree、combotree动态加载(异步加载)
		 * */
		buffer.append("\"state\":\"closed\"");
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
		Map<String, Object> map = getAttributes();
		int i = 0;
		buffer.append("{");
		for(String key : map.keySet()){
			if(i != 0){
				buffer.append(", ");
			}
			if (key.equals("attributes")) {
				buffer.append("\"attributes\": {");
				Map map1 = (Map) map.get(key);
				int j = 0;
				for(Object key1 : map1.keySet()){
					if(j != 0){
						buffer.append(", ");
					}
					buffer.append("\"").append((String)key1).append("\": \"").append(map1.get(key1)).append("\"");
					j++;
				}
				buffer.append("}");	
			}else{
				buffer.append("\"").append(key).append("\": \"").append(map.get(key)).append("\"");
			}
			i++;
		}
		buffer.append("}");
		return buffer.toString();
	}

	@Override
	public String toJsonTree() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		if(this.hasChildren()){
			int j = 0;
			for(AbstractTreeNode node : this.getChildren()){
				Map<String, Object> map = node.getAttributes();
				if(j != 0){
					buffer.append(", ");
				}
				buffer.append("{");
				for (String key : map.keySet()) {
					//排除实体类中设置的state的干扰
					if(key.equals("state")){
						continue;
					}
					if (key.equals("attributes")) {
						buffer.append("\"attributes\": {");
						Map map1 = (Map) map.get(key);
						int i = 0;
						for(Object key1 : map1.keySet()){
							if(i != 0){
								buffer.append(", ");
							}
							buffer.append("\"").append((String)key1).append("\": \"").append(map1.get(key1)).append("\"");
							i++;
						}
						buffer.append("}, ");	
					}else{
						buffer.append("\"").append(key).append("\": \"").append(map.get(key)).append("\", ");
					}
				}
				if(!node.hasChildren()){
					buffer.append("\"").append(LEAF_NODE).append("\": \"").append(LEAF_NODE_VALUE).append("\"");
				}else{
					buffer.append("\"children\": ");
					buffer.append(node.toJsonTree());
				}
				buffer.append("}");
				j++;
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

	@Override
	public Integer getSequence() {
		Map map = (Map) getAttributes().get("attributes");
		Object sequence = map.get("sequence");
		return sequence == null ? 0 : (Integer)sequence;
	}

}
