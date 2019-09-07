package com.taotao.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.model.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemCatService;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemParamService;
import com.taotao.service.ItemService;

/**
 * 	商品详情Controller	jsp+redis
 * @author liut
 * @date 2019年2月26日下午3:46:26
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemParamService itemParamService;
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 *  访问商品详情页	jsp+Redis
	 * @autor liut
	 * @date  2019年2月26日下午9:04:29
	 * @params
	 * @return String
	 */
	@RequestMapping("/item/{itemId}")
	public String gotoItem(@PathVariable Long itemId,Model model) {
		//根据商品id查找商品详情
		TbItemDesc itemDesc = itemDescService.getItemDescById(itemId);
		//查找到商品并补全Item属性
		TbItem tbItem = itemService.getItemById(itemId);
		Item item = new Item(tbItem);
		List<TbItemCat> allItemCatList = itemCatService.getAllItemCatList();
		model.addAttribute("allItemCatList", allItemCatList.subList(0, 14));
		//将数据传递给页面
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
	
	/**
	 *  商品详情页回填规格参数
	 * @autor liut
	 * @date  2019年2月26日下午6:02:46
	 * @params
	 * @return ModelAndView
	 */
	@RequestMapping("/item/param/{itemId}")
	@ResponseBody
	public ModelAndView getItemParamById(@PathVariable Long itemId) {
		TbItemParamItem itemParam = itemParamService.getItemParamItemByItemId(itemId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("itemParam", itemParam);
		return mv;
	}
	
	/**
	 * 	商品详情页回填商品描述
	 * @autor liut
	 * @date  2019年2月26日下午6:04:18
	 * @params
	 * @return ModelAndView
	 */
	@RequestMapping("/item/desc/{itemId}")
	@ResponseBody
	public ModelAndView getItemDescById(@PathVariable Long itemId) {
		//根据商品id查找商品详情
		TbItemDesc itemDesc = itemDescService.getItemDescById(itemId);
		ModelAndView mv = new ModelAndView();
		mv.addObject("itemDesc", itemDesc);
		return mv;
	}
}
