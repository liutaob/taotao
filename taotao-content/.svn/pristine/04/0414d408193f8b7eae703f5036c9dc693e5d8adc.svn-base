package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 内容管理
 * @author liut
 * @date 下午6:33:39
 */
public interface ContentService {
	
	/**
	 * 内容分页展示
	 * @autor liut
	 * @date  2019年2月27日上午2:11:03
	 * @params 当前页数 行数	内容分类id
	 * @return EasyUIDataGridResult
	 */
	EasyUIDataGridResult getContentList(int page,int rows,Long categoryId);
	
	/**
	 * 新增内容
	 * @autor liut
	 * @date  2019年2月27日上午2:11:14
	 * @params	内容
	 * @return TaotaoResult
	 */
	TaotaoResult addContent(TbContent content);
	
	/**
	 * 编辑内容
	 * @autor liut
	 * @date  2019年2月27日上午2:11:28
	 * @params 内容
	 * @return TaotaoResult
	 */
	TaotaoResult updateContent(TbContent content);
	
	/**
	 * 删除内容
	 * @autor liut
	 * @date  2019年2月27日上午2:11:46
	 * @params 勾选的ids字符串
	 * @return TaotaoResult
	 */
	TaotaoResult deleteContent(String ids);
	
	/**
	 * 根据内容分类id获取内容列表
	 * @autor liut
	 * @date  2019年2月27日上午2:11:56
	 * @params 内容分类id
	 * @return List<TbContent>
	 */
	List<TbContent> getContenListByCid(Long categoryId);
	
}
