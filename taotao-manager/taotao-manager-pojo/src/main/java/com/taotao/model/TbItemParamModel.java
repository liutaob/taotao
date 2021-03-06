package com.taotao.model;

import java.io.Serializable;

import com.taotao.pojo.TbItemParam;

/**
 * 扩展的规格参数类 解决分页显示时商品类目为空问题
 * @author liut
 * @date 2019年2月14日下午5:46:27
 */
public class TbItemParamModel extends TbItemParam implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品类目名称
	 */
	private String itemCatName;

	public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}
	
}
