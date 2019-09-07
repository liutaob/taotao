package com.taotao.order.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.pojo.OrderStatus;

/**
 * 订单Service接口
 * @author liut
 * @date 2019年3月3日下午7:07:47
 */
public interface OrderService {

	/**
	 * 创建订单
	 * @autor liut
	 * @date  2019年3月3日下午7:07:59
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult createOrder(OrderInfo orderInfo);
	
	/**
	 * 根据id查询订单
	 * @autor liut
	 * @date  2019年3月17日下午3:00:05
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult getOrderById(String orderId);
	
	/**
	 * 分页展示订单
	 * @autor liut
	 * @date  2019年3月17日下午4:00:01
	 * @params
	 * @return TaotaoResult
	 */
	EasyUIDataGridResult getOrderList(Long userId,Integer page,Integer rows);
	
	/**
	 * 修改订单状态为付款
	 * @autor liut
	 * @date  2019年3月17日下午4:35:32
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult changeOrderStatus(OrderStatus orderStatus);
}
