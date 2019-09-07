package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * 商品管理接口
 * @author liut
 *
 */
public interface ItemService {
	
	/**
	 * 根据id获取商品
	 * @autor liut
	 * @date  2019年2月27日上午3:11:34
	 * @params
	 * @return TbItem
	 */
	TbItem getItemById(long id);
	
	/**
	 * 商品分页展示
	 * @autor liut
	 * @date  2019年2月27日上午3:12:08
	 * @params
	 * @return EasyUIDataGridResult
	 */
	EasyUIDataGridResult getItemList(int page,int rows);
	
	/**
	 * 添加商品
	 * @autor liut
	 * @date  2019年2月27日上午3:13:41
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult addItem(TbItem tbItem,String desc,String paramData);
	
	/**
	 * 加载商品描述
	 * @autor liut
	 * @date  2019年2月27日上午3:13:46
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult getItemDescById(long id);
	
	/**
	 * 修改商品
	 * @autor liut
	 * @date  2019年2月27日上午3:13:55
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult updateItem(TbItem tbItem,String desc,String paramData);
	
	/**
	 * 删除所选商品
	 * @autor liut
	 * @date  2019年2月27日上午3:14:02
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult deleteItem(String ids);
	
	/**
	 * 商品上架
	 * @autor liut
	 * @date  2019年2月27日上午3:14:13
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult reshelfItem(String ids);
	
	/**
	 * 商品下架
	 * @autor liut
	 * @date  2019年2月27日上午3:14:19
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult instockItem(String ids);
}
