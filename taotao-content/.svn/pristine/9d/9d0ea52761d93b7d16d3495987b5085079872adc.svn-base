package com.taotao.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.CacheService;
import com.taotao.jedis.JedisClient;

/**
 * 后台系统缓存管理
 * @author liut
 * @date 2019年2月25日下午3:31:23
 */
@Service
public class CacheServiceImpl implements CacheService {
	
	@Autowired
	private JedisClient jedisClient;

	/**
	 * 删除缓存
	 */
	@Override
	public TaotaoResult deleteCache() {
		try {
			//执行flushall命令
			jedisClient.flushAll();
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, "清理失败");
		}
	}

}
