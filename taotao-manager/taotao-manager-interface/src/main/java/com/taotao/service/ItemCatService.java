package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.pojo.TbItemCat;

/**
 * 商品类目接口
 * @author liut
 * @date 上午1:57:55
 */
public interface ItemCatService {
	
	/**
	 * 根据父结点id获取子结点列表
	 * @autor liut
	 * @date  2019年2月26日下午9:11:22
	 * @params parentId父节点id
	 * @return List<EasyUITreeNode>
	 */
	List<EasyUITreeNode> getItemCatList(long parentId);
	
	/**
	 * 获取全部商品类目 首页展示
	 * @autor liut
	 * @date  2019年2月26日下午9:11:06
	 * @params
	 * @return List<TbItemCat>
	 */
	List<TbItemCat> getAllItemCatList();
	
}
