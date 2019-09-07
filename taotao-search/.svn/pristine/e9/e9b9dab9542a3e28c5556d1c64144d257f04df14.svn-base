package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

/**
 * 	首页商品搜索服务实现Service
 * @author liut
 * @date 2019年2月19日下午3:57:18
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	/**
	 * 搜索商品
	 * @autor liut
	 * @date  2019年2月27日上午3:26:08
	 * @params
	 * @return @see com.taotao.search.service.SearchService#search(java.lang.String, int, int)
	 */
	@Override
	public SearchResult search(String queryString, int page,int rows) throws Exception {
		//创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件、过滤条件、分页条件、排序条件、高亮
		query.setQuery(queryString);
		//设置分页条件
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//设置高亮
		query.setHighlight(true);
		//高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		//调用dao从索引库中搜索
		SearchResult result = searchDao.search(query);
		//查询到的总记录数
		long recordCount = result.getRecordCount();
		//计算页数并设置为搜索结果属性值
		long pageCount = recordCount/rows;
		if(recordCount % rows > 0) {
			pageCount++;
		}
		result.setTotalPages(pageCount);
		//返回搜索结果
		return result;
	}

}
