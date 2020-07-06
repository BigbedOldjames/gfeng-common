package com.omysoft.common.tree;

import java.util.List;

import com.omysoft.common.entity.TreeEntity;



/**
 * ClassName: ITreeParser
 * @Description: 转换器
 * @author yy
 * @date 2014-9-18 下午2:53:26
 */
public interface ITreeParser {

	/**
	 * @Description: 转换成多叉树
	 * @param @param list
	 * @param @return   
	 * @return TreeNode  
	 * @author yy
	 * @date 2014-9-16 下午2:00:50
	 */
	public AbstractTreeNode parseToMultiTree(List<? extends TreeEntity> list, Boolean isParentIdMustNull);
	/**
	 * @Description: 获取当前节点id下的子多叉树
	 * @param @param list
	 * @param @return   
	 * @return TreeNode  
	 * @author yy
	 * @date 2014-9-16 下午2:00:50
	 */
	public AbstractTreeNode parseToMultiTree(List<? extends TreeEntity> list, Long id);
	/**
	 * 转换成多叉树的某一个节点
	 * @param data
	 * @return
	 */
	public AbstractTreeNode parseToObject(TreeEntity data);
	/**
	 * 转换成多叉树的某一级/层的节点
	 * @param list
	 * @return
	 */
	public AbstractTreeNode parseToArrayList(List<? extends TreeEntity> list);

}
