package com.taotao.sso.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

/**
 * 用户管理Controller
 * @author liut
 * @date 2019年3月2日下午8:26:08
 */
@Controller
public class UserController {

	@Autowired 
	private UserService userService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	/**
	 * 校验用户数据
	 * @autor liut
	 * @date  2019年3月2日下午8:29:20
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkUserData(@PathVariable String param,@PathVariable Integer type) {
		TaotaoResult result = userService.checkData(param, type);
		return result;
	}
	
	/**
	 * 注册用户
	 * @autor liut
	 * @date  2019年3月2日下午9:00:45
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult regitster(TbUser user) {
		TaotaoResult result = userService.register(user);
		return result;
	}
	
	/**
	 * 用户登录
	 * @autor liut
	 * @date  2019年3月2日下午10:27:56
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password, 
			HttpServletResponse response, HttpServletRequest request) {
		TaotaoResult result = userService.login(username, password);
		//登录成功后写cookie
		if (result.getStatus() == 200) {
			//把token写入cookie 默认关闭浏览器失效
			CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	
	/**
	 * 根据token查询用户
	 * @autor liut
	 * @date  2019年3月2日下午11:07:04
	 * @params
	 * @return String
	 */
	@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET, 
	//指定返回响应数据的content-type，生产
	produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = userService.getUserByToken(token);
		//判断是否是jsonp请求 返回
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}
	
	/**
	 * 安全退出
	 * @autor liut
	 * @date  2019年3月2日下午12:14:24
	 * @params
	 * @return TaotaoResult
	 * @throws IOException 
	 */
	@RequestMapping(value="/user/logout/{token}", method=RequestMethod.GET)
	public void loginOut(@PathVariable String token,HttpServletResponse response,HttpServletRequest request) throws IOException {
		TaotaoResult result = userService.loginOut(token);
		//删除Cookie中的数据
		CookieUtils.deleteCookie(request, response, TOKEN_KEY);
		//回到首页
		response.sendRedirect("http://localhost:8082");
	}
}
