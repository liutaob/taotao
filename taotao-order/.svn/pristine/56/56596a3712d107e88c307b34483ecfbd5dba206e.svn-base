package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.dao.TbOrderDao;
import com.taotao.dao.TbOrderItemDao;
import com.taotao.dao.TbOrderShippingDao;
import com.taotao.jedis.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.pojo.OrderStatus;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderItemQuery;
import com.taotao.pojo.TbOrderItemQuery.Criteria;
import com.taotao.pojo.TbOrderQuery;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.pojo.TbOrderShippingQuery;

/**
 * 订单Service
 * @author liut
 * @date 2019年3月3日下午7:10:45
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderDao orderDao;
	@Autowired
	private TbOrderItemDao orderItemDao;
	@Autowired
	private TbOrderShippingDao orderShippingDao;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_BEGIN_VALUE}")
	private String ORDER_ID_BEGIN_VALUE;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	
	@Override
	public TaotaoResult createOrder(OrderInfo orderInfo) {
		//生成订单号,可以使用redis的incr生成
		if (!jedisClient.exists(ORDER_ID_GEN_KEY)) {
			//设置初始值
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//向订单表插入数据，需要补全pojo的属性
		orderInfo.setOrderId(orderId);
		//免邮费
		orderInfo.setPostFee("0");
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		//订单创建时间
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//向订单 表插入数据
		orderDao.insert(orderInfo);
		//向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//获得明细主键
			String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
			tbOrderItem.setId(oid);
			tbOrderItem.setOrderId(orderId);
			//插入明细数据
			orderItemDao.insert(tbOrderItem);
		}
		//向订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingDao.insert(orderShipping);
		//返回订单号
		return TaotaoResult.ok(orderId);
	}

	/**
	 * @autor liut
	 * @date  2019年3月17日下午3:00:19
	 * @params
	 * @return @see com.taotao.order.service.OrderService#getOrderById(java.lang.Long)
	 */
	@Override
	public TaotaoResult getOrderById(String orderId) {
		//创建一个订单信息对象
		OrderInfo orderInfo = new OrderInfo();
		//查询订单
		TbOrder order = orderDao.selectByPrimaryKey(orderId);
		/**
		 * 补全订单信息属性
		 */
		orderInfo.setOrderId(orderId);
		orderInfo.setCreateTime(order.getCreateTime());
		orderInfo.setUpdateTime(order.getUpdateTime());
		orderInfo.setStatus(order.getStatus());
		orderInfo.setPostFee(order.getPostFee());
		orderInfo.setPayment(order.getPayment());
		orderInfo.setPaymentType(order.getPaymentType());
		orderInfo.setUserId(order.getUserId());
		/**
		 * 查询该订单下所有订单项 并设置为订单信息属性
		 */
		TbOrderItemQuery query = new TbOrderItemQuery();
		Criteria criteria = query.createCriteria();
		criteria.andOrderIdEqualTo(orderId);
		List<TbOrderItem> list = orderItemDao.selectByExample(query);
		orderInfo.setOrderItems(list);
		/**
		 * 查询该订单的收获地址信息 并设置为订单信息属性
		 */
		TbOrderShippingQuery query2 = new TbOrderShippingQuery();
		com.taotao.pojo.TbOrderShippingQuery.Criteria criteria2 = query2.createCriteria();
		criteria2.andOrderIdEqualTo(orderId);
		List<TbOrderShipping> list2 = orderShippingDao.selectByExample(query2);
		if(list2 !=null && list2.size() != 0) {
			TbOrderShipping orderShipping = list2.get(0);
			orderInfo.setOrderShipping(orderShipping);
		}
		//返回结果
		return TaotaoResult.ok(orderInfo);
	}

	/**
	 * 分页展示订单
	 * @autor liut
	 * @date  2019年3月17日下午4:00:38
	 * @params
	 * @return @see com.taotao.order.service.OrderService#getOrderList(java.lang.String, int, int)
	 */
	@Override
	public EasyUIDataGridResult getOrderList(Long userId,Integer page,Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		TbOrderQuery query = new TbOrderQuery();
		com.taotao.pojo.TbOrderQuery.Criteria criteria = query.createCriteria();
		criteria.andUserIdEqualTo(userId);
		//查询所有订单
		List<TbOrder> orderList = orderDao.selectByExample(query);
		PageInfo<TbOrder> pageInfo = new PageInfo<TbOrder>(orderList);
		//取查询结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(orderList);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 修改订单状态为付款
	 * @autor liut
	 * @date  2019年3月17日下午4:35:46
	 * @params
	 * @return @see com.taotao.order.service.OrderService#changeOrderStatus(com.taotao.order.pojo.OrderStatus)
	 */
	@Override
	public TaotaoResult changeOrderStatus(OrderStatus orderStatus) {
		/**
		 * 查询要修改的订单
		 */
		TbOrderQuery query = new TbOrderQuery();
		com.taotao.pojo.TbOrderQuery.Criteria criteria = query.createCriteria();
		criteria.andOrderIdEqualTo(orderStatus.getOrderId());
		List<TbOrder> list = orderDao.selectByExample(query);
		if (list == null || list.size() == 0) {
			return TaotaoResult.build(500, "系统异常");
		}
		/**
		 * 获取要修改的订单 并更新订单状态、付款时间
		 */
		TbOrder order = list.get(0);
		order.setPaymentTime(new Date());
		order.setStatus(2);
		return TaotaoResult.ok();
	}

}
