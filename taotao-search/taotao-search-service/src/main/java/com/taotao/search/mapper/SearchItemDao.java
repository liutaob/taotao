package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

/**
 * 	商品搜索Dao
 * @author liut
 * @date 2019年2月18日下午7:16:59
 */
public interface SearchItemDao {
	
	/**
	 * 获取所有搜索商品
	 * @autor liut
	 * @date  2019年2月27日下午3:34:49
	 * @params
	 * @return List<SearchItem>
	 */
	List<SearchItem> getItemList();
	
	/**
	 * 根据商品id获取搜索商品 用于同步索引库
	 * @autor liut
	 * @date  2019年2月27日下午3:35:07
	 * @params 商品id
	 * @return SearchItem
	 */
	SearchItem getItemById(long id);
}
