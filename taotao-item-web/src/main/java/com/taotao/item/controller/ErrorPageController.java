package com.taotao.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 错误页面跳转Controller
 * @author liut
 * @date 2019年2月27日下午6:30:16
 */
@Controller
public class ErrorPageController {
	
	/**
	 * 	404页面跳转
	 * @autor liut
	 * @date  2019年2月27日下午3:40:53
	 * @params
	 * @return String
	 */
	@RequestMapping("/error/{code}")
	public String showPage(@PathVariable String code) {
		return "/error/"+code;
	}
}
