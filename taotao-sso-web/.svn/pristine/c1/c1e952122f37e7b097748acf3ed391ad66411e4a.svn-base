package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示登录注册页面Controller
 * @author liut
 * @date 2019年3月2日下午11:40:23
 */
@Controller
public class PageController {

	/**
	 * 注册页面
	 * @autor liut
	 * @date  2019年3月2日下午11:40:37
	 * @params
	 * @return String
	 */
	@RequestMapping("/page/register")
	public String showRegister() {
		return "register";
	}
	
	/**
	 * 登录页面
	 * @autor liut
	 * @date  2019年3月2日下午11:40:43
	 * @params	url为拦截登录后回调地址
	 * @return String
	 */
	@RequestMapping("/page/login")
	public String showLogin(String url,Model model) {
		model.addAttribute("redirect", url);
		return "login";
	}
}
