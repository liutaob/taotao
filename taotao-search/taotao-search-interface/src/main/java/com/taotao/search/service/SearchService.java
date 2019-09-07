package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;

/**
 * 	商品搜索接口
 * @author liut
 * @date 2019年2月19日下午3:55:32
 */
public interface SearchService {
	
	/**
	 * 根据关键字搜索商品
	 * @autor liut
	 * @date  2019年2月27日下午3:38:34
	 * @params
	 * @return SearchResult
	 */
	public SearchResult search(String queryString,int page,int rows) throws Exception;
}
