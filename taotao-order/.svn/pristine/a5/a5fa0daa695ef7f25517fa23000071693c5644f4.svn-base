package com.taotao.order.pojo;

import java.io.Serializable;
import java.util.List;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

/**
 * 扩展订单类
 * @author liut
 * @date 2019年3月3日下午6:54:37
 */
public class OrderInfo extends TbOrder implements Serializable{
	
	/**
	 * 订单明细
	 */
	private List<TbOrderItem> orderItems;
	/**
	 * 订单地址
	 */
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
	

}
