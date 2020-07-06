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
public class ZTreeParser implements ITreeParser {

	@Override
	public AbstractTreeNode parseToMultiTree(List<? extends TreeEntity> list, Boolean isParentIdMustNull) {
		AbstractTreeNode root = new ZTreeNode();
		Map<Long, AbstractTreeNode> treeNodeMap = new HashMap<Long, AbstractTreeNode>();
		if (list != null && !list.isEmpty()) {
			for(TreeEntity obj : list){
				treeNodeMap.put(obj.getId(), new ZTreeNode(obj));
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
		return root;
	}

	@Override
	public AbstractTreeNode parseToObject(TreeEntity data) {
		return new ZTreeNode(data);
	}

	@Override
	public AbstractTreeNode parseToArrayList(List<? extends TreeEntity> list) {
		AbstractTreeNode root = new ZTreeNode();
		if (list != null && !list.isEmpty()) {
			for(TreeEntity obj : list){
				root.addChild(new ZTreeNode(obj));
			}
		}
		return root;
	}

	@Override
	public AbstractTreeNode parseToMultiTree(List<? extends TreeEntity> list, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
