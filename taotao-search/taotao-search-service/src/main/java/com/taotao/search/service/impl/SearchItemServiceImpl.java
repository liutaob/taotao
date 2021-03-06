package com.taotao.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.SearchItemDao;
import com.taotao.search.service.SearchItemService;

/**
 * 	商品数据导入索引库、同步索引库Service
 * @author liut
 * @date 2019年2月18日下午7:42:11
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchItemDao searchItemDao;
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 将商品数据导入solr索引库
	 * @autor liut
	 * @date  2019年2月27日下午3:33:59
	 * @params
	 * @return @see com.taotao.search.service.SearchItemService#importItemsToIndex()
	 */
	@Override
	public TaotaoResult importItemsToIndex() {
		try {
			//1、先查询所有商品数据
			List<SearchItem> itemList = searchItemDao.getItemList();
			//2、遍历商品数据添加到索引库
			for (SearchItem searchItem : itemList) {
				//创建文档对象
				SolrInputDocument document = new SolrInputDocument();
				//向文档中添加域
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_desc());
				//把文档写入索引库
				solrServer.add(document);
			}
			//3、提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "数据导入失败");
		}
		//4、返回添加成功
		return TaotaoResult.ok();
	}

	/**
	 * 将新增商品同步索引库
	 * @autor liut
	 * @date  2019年2月27日下午3:33:19
	 * @params
	 * @return @see com.taotao.search.service.SearchItemService#addItemDocument(long)
	 */
	@Override
	public TaotaoResult addItemDocument(long itemId) {
		//根据商品id查找到商品
		SearchItem searchItem = searchItemDao.getItemById(itemId);
		//创建文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		document.addField("item_desc", searchItem.getItem_desc());
		try {
			//把文档写入索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	/**
	 * 删除商品同步索引库
	 * @autor liut
	 * @date  2019年2月27日下午3:33:41
	 * @params
	 * @return @see com.taotao.search.service.SearchItemService#deleteItemDocument(long)
	 */
	@Override
	public TaotaoResult deleteItemDocument(long itemId) {
		try {
			//根据id删除索引库
			solrServer.deleteById(itemId+"");
			//提交
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}


}
