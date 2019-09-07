package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面展示Controller
 * @author liut
 *
 */
@Controller
public class PageController {
	
	/**
	 * 访问后台管理系统首页
	 * @autor liut
	 * @date  2019年2月27日下午3:40:41
	 * @params
	 * @return String
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	/**
	 * 页面跳转
	 * @autor liut
	 * @date  2019年2月27日下午3:40:53
	 * @params
	 * @return String
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
