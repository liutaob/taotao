package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

/**
 * 	商品规格参数Controller
 * @author liut
 * @date 下午8:05:11
 */
@Controller
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	/**
	 * 根据id查询商品类目规格参数
	 * @autor liut
	 * @date  下午8:25:35
	 * @params
	 * @return TbItemParam
	 */
	@RequestMapping("/item/param/{id}")
	@ResponseBody
	public TbItemParam getItemById(@PathVariable Long id) {
		TbItemParam itemParam = itemParamService.getItemParamById(id);
		return itemParam;
	}
	
	/**
	 * 分页展示商品类目规格参数
	 * @autor liut
	 * @date  下午8:21:08
	 * @params
	 * @return EasyUIDataGridResult
	 */
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows) {
		EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
	
	/**
	 * 查询商品 点击商品编辑时 根据商品itemId加载商品的规格参数（tb_item_param_item表）
	 * @autor liut
	 * @date  2019年2月14日下午8:11:27
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/rest/item/param/item/query/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable Long itemId) {
		TaotaoResult result = itemParamService.getItemParamItemById(itemId);
		return result;
	}
	
	/**
	 * 判断选择的目录是否已经添加过规格
	 * @autor liut
	 * @date  2019年2月14日下午7:40:34
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping("/item/param/query/itemcatid/{id}")
	@ResponseBody
	public TaotaoResult getItemCat(@PathVariable("id") Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByItemCatId(itemCatId);
		return result;
	}
	
	/**
	 * 	新增商品类目规格参数
	 * @autor liut
	 * @date  2019年2月14日下午4:00:58
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value="/item/param/save/{cid}",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItemParam(@PathVariable("cid") Long itemCatId,String paramData) {
		TaotaoResult result = itemParamService.addItemParam(itemCatId, paramData);
		return result;
	}
	
	/**
	 * 	删除勾选的商品类目规格参数
	 * @autor liut
	 * @date  2019年2月14日下午4:34:24
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/item/param/delete",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteResult(String ids) {
		TaotaoResult result = itemParamService.deleteItemParam(ids);
		return result;
	}
}
