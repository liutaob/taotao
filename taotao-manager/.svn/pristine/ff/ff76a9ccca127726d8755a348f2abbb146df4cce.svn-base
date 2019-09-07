package com.taotao.model;

import java.io.Serializable;

import com.taotao.pojo.TbItem;

/**
 * 扩展TbItem类，用于商品详情页
 * @author liut
 * @date 2019年2月26日下午11:54:27
 */
public class Item extends TbItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 继承TbItem属性并赋值
	 */
	public Item(TbItem tbItem) {
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
		this.setImage(tbItem.getImage());
	}
	
	/**
	 * 处理多张图片
	 * @autor liut
	 * @date  2019年2月26日下午11:56:21
	 * @params
	 * @return String[]
	 */
	public String[] getImages() {
		String image2 = this.getImage();
		if(image2!=null && !"".equals(image2)) {
			String[] strings = image2.split("[,]");
			return strings;
		}
		return null;
	}
}