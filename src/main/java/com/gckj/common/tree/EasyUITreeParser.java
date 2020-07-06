package com.omysoft.common.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.omysoft.common.entity.TreeEntity;



/**
 * ClassName: ZTreeParser
 * @Description: zTree转换器
 * @author yy
 * @date 2014-9-18 下午2:54:32
 */
public class EasyUITreeParser implements ITreeParser {

	public AbstractTreeNode parseToMultiTree(List<? extends TreeEntity> list, Boolean isParentIdMustNull) {
		AbstractTreeNode root = new EasyUITreeNode();
		Map<Long, AbstractTreeNode> treeNodeMap = new HashMap<Long, AbstractTreeNode>();
		if (list != null && !list.isEmpty()) {
			for(TreeEntity obj : list){
				treeNodeMap.put(obj.getId(), new EasyUITreeNode(obj));
			}
			for(TreeEntity obj : list){
				AbstractTreeNode parent = treeNodeMap.get(obj.getParentId());
				AbstractTreeNode treeNode = treeNodeMap.get(obj.getId());
				if (parent == null) {
					if(isParentIdMustNull){
						//过滤掉禁用的菜单及其子树
						if(obj.getParentId() == null){
							root.addChild(treeNode);
						}
					}else{
						root.addChild(treeNode);
					}
				} else {
					parent.addChild(treeNode);
				}
			}
		}
		return root;
	}
	
	public AbstractTreeNode parseToMultiTree(List<? extends TreeEntity> list, Long id) {
		AbstractTreeNode root = new EasyUITreeNode();
		AbstractTreeNode supTreenode = new EasyUITreeNode();
		Map<Long, AbstractTreeNode> treeNodeMap = new HashMap<Long, AbstractTreeNode>();
		if (list != null && !list.isEmpty()) {
			for(TreeEntity obj : list){
				treeNodeMap.put(obj.getId(), new EasyUITreeNode(obj));
			}
			for(TreeEntity obj : list){
				AbstractTreeNode parent = treeNodeMap.get(obj.getParentId());
				AbstractTreeNode treeNode = treeNodeMap.get(obj.getId());
				if (parent == null) {
					root.addChild(treeNode);
				} else {
					parent.addChild(treeNode);
				}
			}
		}
		supTreenode.addChild(treeNodeMap.get(id));
		return supTreenode;
	}

	public AbstractTreeNode parseToObject(TreeEntity data) {
		return new EasyUITreeNode(data);
	}

	public AbstractTreeNode parseToArrayList(List<? extends TreeEntity> list) {
		AbstractTreeNode root = new EasyUITreeNode();
		if (list != null && !list.isEmpty()) {
			for(TreeEntity obj : list){
				root.addChild(new EasyUITreeNode(obj));
			}
		}
		return root;
	}
	
}
