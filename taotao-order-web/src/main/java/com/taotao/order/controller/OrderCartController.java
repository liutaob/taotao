package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.pojo.OrderStatus;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

/**
 * 订单确认页面Controller
 * @author liut
 * @date 2019年3月3日下午2:29:55
 */
@Controller
public class OrderCartController {
	
	@Value("${CART_KEY}")
	private String CART_KEY;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	/**
	 * 展示订单确认页面
	 * @autor liut
	 * @date  2019年3月3日下午2:33:10
	 * @params
	 * @return String
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		//用户必须是登录状态
		//取用户id
		TbUser user = (TbUser) request.getAttribute("user");
		//将用户信息传递给页面
		request.setAttribute("user", user);
		//从cookie中取购物车商品列表展示到页面
		List<TbItem> cartList = getCartItemList(request);
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "order-cart";
	}
	
	/**
	 * 从cookie中获取购物车列表
	 * @autor liut
	 * @date  2019年3月3日下午6:14:00
	 * @params
	 * @return List<TbItem>
	 */
	private List<TbItem> getCartItemList(HttpServletRequest request) {
		//从cookie中取购物车商品列表
		String json = CookieUtils.getCookieValue(request, CART_KEY, true);
		if (StringUtils.isBlank(json)) {
			//如果没有内容，返回一个空的列表
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
	/**
	 * 生成订单
	 * @autor liut
	 * @date  2019年3月3日下午7:25:23
	 * @params
	 * @return String
	 */
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model,HttpServletRequest request
			,HttpServletResponse response) {
		//从Cookie中获取token，查询用户
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY, true);
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		TbUser user = (TbUser) taotaoResult.getData();
		//设置订单所属用户
		orderInfo.setUserId(user.getId());
		//生成订单
		TaotaoResult result = orderService.createOrder(orderInfo);
		//清空购物车
		CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(new ArrayList<>()),
				 true);
		//向页面传递数据
		model.addAttribute("orderId", result.getData().toString());
		model.addAttribute("payment", orderInfo.getPayment());
		//设置
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		return "success";
	}
	
	/**
	 * 分页展示我的订单
	 * @autor liut
	 * @date  2019年3月17日下午5:14:18
	 * @params
	 * @return EasyUIDataGridResult
	 */
	@RequestMapping("/order/list")
	@ResponseBody
	public EasyUIDataGridResult listOrder(Integer page,Integer rows,HttpServletRequest request) {
		//从cookie中获取用户id
		String token = CookieUtils.getCookieValue(request, TOKEN_KEY, true);
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		TbUser user = (TbUser) taotaoResult.getData();
		//获取当前用户下所有订单
		EasyUIDataGridResult result = orderService.getOrderList(user.getId(),page, rows);
		return result;
	}
	
	/**
	 * 我的订单分页展示 携带订单参数id 点击订单 查看订单详情
	 * @autor liut
	 * @date  2019年3月17日下午4:55:51
	 * @params
	 * @return String
	 */
	@RequestMapping("/order/info/{orderId}")
	public String gotoOrderDeail(@PathVariable String orderId,Model model) {
		//获取该订单的信息 同时查出订单项和收获信息
		TaotaoResult result = orderService.getOrderById(orderId);
		OrderInfo orderInfo = (OrderInfo) result.getData();
		//将数据传递给订单详情页
		model.addAttribute("orderItemsList", orderInfo.getOrderItems());
		return "order-detail";
	}
	
	/**
	 * 修改订单支付状态
	 * @autor liut
	 * @date  2019年3月17日下午5:18:04
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value="/order/changeStatus",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult changeOrderStatus(OrderStatus orderStatus) {
		TaotaoResult result = orderService.changeOrderStatus(orderStatus);
		return result;
	}
	
	/**
	 * 订单列表页面
	 * @autor liut
	 * @date  2019年3月17日下午6:07:34
	 * @params
	 * @return String
	 */
	@RequestMapping("/order/order-list")
	public String showOrderList() {
		return "order-list";
	}
}
