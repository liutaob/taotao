package com.taotao.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.pojo.SearchResult;
import com.taotao.pojo.TbItemCat;
import com.taotao.search.service.SearchService;
import com.taotao.service.ItemCatService;

/**
 * 	搜索Controller
 * @author liut
 * @date 2019年2月19日下午5:45:17
 */
@Controller
public class SearchController {
	
	@Autowired 
	private SearchService searchService;
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 	首页商品搜索
	 * @autor liut
	 * @date  2019年2月19日下午4:15:30
	 * @params
	 * @return SearchResult
	 * @throws Exception 
	 * @throws Exception 
	 */
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam("q") String queryString,@RequestParam(defaultValue="1") Integer page) throws Exception{
//		int i=1/0;
		SearchResult result = null;
		//注意:表现层不能抛异常给用户  但可用采用全局异常处理
		//将查询字符串条件转码 解决get乱码 web.xml配置的是post乱码
		queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
		//查询搜索结果
		result = searchService.search(queryString,page,ITEM_ROWS);
		//搜索页面商品分类
		List<TbItemCat> allItemCatList = itemCatService.getAllItemCatList();
		//创建视图并存放数据传递给页面
		ModelAndView mv = new ModelAndView();
		mv.setViewName("search");
		mv.addObject("query", queryString);
		mv.addObject("totalPages", result.getTotalPages());
		mv.addObject("itemList", result.getItemList());
		mv.addObject("page", page);
		//暂时先展示14条
		mv.addObject("allItemCatList", allItemCatList.subList(0, 14));
		return mv;
	}
	
}
