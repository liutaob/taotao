package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

/**
 * 内容分类管理
 * @author liut
 * @date 上午11:16:21
 */
public interface ContentCategoryService {
	
	/**
	 * 根据父结点id查找内容分类列表
	 * @autor liut
	 * @date  2019年2月27日上午2:12:56
	 * @params 父结点id
	 * @return List<EasyUITreeNode>
	 */
	List<EasyUITreeNode> getContentCategoryList(long parentId);
	
	/**
	 * 新增内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:13:22
	 * @params 父结点id 新增内容分类名
	 * @return TaotaoResult
	 */
	TaotaoResult addContentCategory(Long parentId,String name);
	
	/**
	 * 重命名内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:13:54
	 * @params 内容分类id 更改后名字
	 * @return TaotaoResult
	 */
	TaotaoResult updateContentCategory(Long id,String name);
	
	/**
	 * 删除内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:15:36
	 * @params 内容分类id
	 * @return TaotaoResult
	 */
	TaotaoResult deleteContentCategory(Long id);
	
	/**
	 * 获取内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:16:00
	 * @params	内容分类id
	 * @return TbContentCategory
	 */
	TbContentCategory getContentCategoryById(Long id);
}
