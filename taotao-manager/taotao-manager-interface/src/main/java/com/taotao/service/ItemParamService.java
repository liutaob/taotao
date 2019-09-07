package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;

/**
 * 商品规格参数接口
 * @author liut
 * @date 下午8:07:13
 */
public interface ItemParamService {
	
	/**
	 * 根据id查询商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午2:55:36
	 * @params 
	 * @return TbItemParam
	 */
	TbItemParam getItemParamById(long id);
	
	/**
	 * 根据商品itemId查询商品规格参数
	 * @autor liut
	 * @date  2019年2月27日上午2:56:09
	 * @params
	 * @return TbItemParamItem
	 */
	TbItemParamItem getItemParamItemByItemId(long itemId);
	
	/**
	 * 分页展示商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午3:03:38
	 * @params
	 * @return EasyUIDataGridResult
	 */
	EasyUIDataGridResult getItemParamList(int page,int rows);
	
	/**
	 * 点击编辑商品时加载商品规格
	 * @autor liut
	 * @date  2019年2月27日上午3:04:23
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult getItemParamItemById(long itemId);
	
	/**
	 * 判断选择的商品类目是否已经添加过规格
	 * @autor liut
	 * @date  2019年2月27日上午3:05:48
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult getItemParamByItemCatId(long itemCatId);
	
	/**
	 * 新增商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午3:07:25
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult addItemParam(Long itemCatId,String paramData);
	
	/**
	 * 删除勾选的商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午3:07:29
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult deleteItemParam(String ids);
}
