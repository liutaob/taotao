package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * 	商品导入索引库、同步索引库接口
 * @author liut
 * @date 2019年2月19日上午10:46:09
 */
public interface SearchItemService {
	
	/**
	 * 商品导入索引库
	 * @autor liut
	 * @date  2019年2月27日下午3:37:06
	 * @params
	 * @return TaotaoResult
	 */
	public TaotaoResult importItemsToIndex();
	
	/**
	 * 新增商品文档到索引库
	 * @autor liut
	 * @date  2019年2月27日下午3:37:19
	 * @params 商品id
	 * @return TaotaoResult
	 */
	public TaotaoResult addItemDocument(long itemId);
	
	/**
	 * 删除索引库中商品文档
	 * @autor liut
	 * @date  2019年2月27日下午3:37:37
	 * @params 商品id
	 * @return TaotaoResult
	 */
	public TaotaoResult deleteItemDocument(long itemId);
}
