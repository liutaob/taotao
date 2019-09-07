package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 内容Controller
 * @author liut
 * @date 下午6:46:47
 */
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * 	分页展示内容列表
	 * @autor liut
	 * @date  下午6:48:22
	 * @params
	 * @return EasyUIDataGridResult
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Integer page,Integer rows,Long categoryId) {
		EasyUIDataGridResult result = contentService.getContentList(page, rows, categoryId);
		return result;
	}
	
	/**
	 * 新增内容列表
	 * @autor liut
	 * @date  上午10:06:15
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		TaotaoResult result = contentService.addContent(content);
		return result;
	}
	
	/**
	 * 	编辑内容列表
	 * @autor liut
	 * @date  上午10:14:52
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content) {
		TaotaoResult result = contentService.updateContent(content);
		return result;
	}
	
	/**
	 * 	删除内容列表
	 * @autor liut
	 * @date  上午11:43:34
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	//也可以RequsetParam("ids") List<Long> ids 再条件删除
	public TaotaoResult deleteContent(String ids) {
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
}
