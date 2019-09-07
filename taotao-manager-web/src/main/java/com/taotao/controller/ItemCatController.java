package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;

/**
 * 商品分类管理Controller
 * @author liut
 * @date 上午2:10:48
 */
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 	商品类目展示
	 * @autor liut
	 * @date  下午2:45:47
	 * @params
	 * @return List<EasyUITreeNode>
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}
	
}
