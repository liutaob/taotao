package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.CacheService;

/**
 * 	缓存管理
 * @author liut
 * @date 2019年2月25日下午3:17:08
 */
@Controller
public class CacheManagerController {
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 清理缓存内容
	 * @autor liut
	 * @date  2019年2月25日下午3:36:16
	 * @params
	 * @return TaotaoResult
	 */
	@RequestMapping("/cache/clear")
	@ResponseBody
	public TaotaoResult importIndex() {
		TaotaoResult taotaoResult = cacheService.deleteCache();
		return taotaoResult;
	}
	
}
