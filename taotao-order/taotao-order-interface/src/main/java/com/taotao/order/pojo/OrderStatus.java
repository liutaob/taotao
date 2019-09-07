package com.taotao.order.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单状态类
 * @author liut
 * @date 2019年3月17日下午4:31:27
 */
public class OrderStatus implements Serializable{
	
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 订单状态
	 */
	private Integer status;
	/**
	 * 付款时间
	 */
	private Date paymentTime;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	

}
