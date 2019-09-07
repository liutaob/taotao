package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

/**
 * 内容分类Controller
 * @author liut
 * @date 上午11:34:48
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 	内容类目展示
	 * @autor liut
	 * @date  2/13/11:36
	 * @params
	 * @return List<EasyUITreeNode>
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id", defaultValue="0")Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	
	/**
	 * 	新增内容分类
	 * @autor liut
	 * @date  下午1:02:26
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult addContentCategory(Long parentId,String name) {
		TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
	
	/**
	 * 	重命名内容分类
	 * @autor liut
	 * @date  下午1:02:26
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id,String name) {
		TaotaoResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
	
	/**
	 * 	删除内容分类
	 * @autor liut
	 * @date  下午2:14:30
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value = "/content/category/delete/",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult	deleteContentCategory(Long id) {
		TaotaoResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
	
}
