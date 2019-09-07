package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 商品管理Controller
 * @author liut
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 	根据id查询商品信息
	 * @autor liut
	 * @date  下午8:25:35
	 * @params
	 * @return TbItem
	 */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	/**
	 * 	分页展示商品
	 * @autor liut
	 * @date  下午2:22:36
	 * @params
	 * @return EasyUIDataGridResult
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	/**
	 * 	新增商品
	 * @autor liut
	 * @date  下午2:23:12
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/item/save",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addResult(TbItem tbItem,String desc,@RequestParam(name="itemParams", defaultValue="")String paramData) {
		TaotaoResult result = itemService.addItem(tbItem, desc,paramData);
		return result;
	}
	
	/**
	 * 	打开商品编辑页
	 * @autor liut
	 * @date  下午2:23:24
	 * @params
	 * @return String
	 */
	@RequestMapping("/rest/page/item-edit")
	public String gotoEdit() {
		return "item-edit";
	}
	
	/**
	 * 	回填商品描述
	 * @autor liut
	 * @date  下午2:21:51
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		System.out.println("编辑商品描述id"+itemId);
		TaotaoResult result = itemService.getItemDescById(itemId);
		return result;
	}
	
	/**
	 * 	修改商品
	 * @autor liut
	 * @date  下午2:48:19
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/rest/item/update",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult editResult(TbItem tbItem, String desc,@RequestParam(name="itemParams", defaultValue="")String paramData) {
		TaotaoResult result = itemService.updateItem(tbItem, desc,paramData);
		return result;
	}
	
	/**
	 * 	删除商品
	 * @autor liut
	 * @date  下午3:43:30
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/rest/item/delete",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteResult(String ids) {
		System.out.println(ids);
		TaotaoResult result = itemService.deleteItem(ids);
		return result;
	}
	
	
	/**
	 * 	商品上架
	 * @autor liut
	 * @date  下午4:02:01
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/rest/item/reshelf",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult reshelfResult(String ids) {
		System.out.println(ids);
		TaotaoResult result = itemService.reshelfItem(ids);
		return result;
	}
	
	/**
	 * 	商品下架
	 * @autor liut
	 * @date  下午4:02:01
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/rest/item/instock",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult instockResult(String ids) {
		System.out.println(ids);
		TaotaoResult result = itemService.instockItem(ids);
		return result;
	}
}
