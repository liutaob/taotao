package com.taotao.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.dao.TbItemDescDao;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemDescService;

/**
 * 	商品描述Service
 * @author liut
 * @date 2019年2月26日下午4:03:00
 */
@Service("itemDescService")
public class ItemDescServiceImpl implements ItemDescService {
	
	@Autowired
	private TbItemDescDao itemDescDao;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${DESC}")
	private String DESC;
	@Value("${TIME_EXPIRE}")
	private Integer TIME_EXPIRE;
	
	/**
	 * 根据商品id获取商品描述
	 * @autor liut
	 * @date  2019年2月27日上午3:10:31
	 * @params
	 * @return @see com.taotao.service.ItemDescService#getItemDescById(long)
	 */
	@Override
	public TbItemDesc getItemDescById(long itemId) {
		// 先查询缓存
		// 添加缓存不能影响正常业务逻辑
		try {
			// 查询缓存
			String json = jedisClient.get(ITEM_INFO+ itemId + DESC);
			// 查询到结果，把json转换成TbItemDesc返回
			if (StringUtils.isNotBlank(json)) {
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有命中，需要查询数据库
		TbItemDesc itemDesc = itemDescDao.selectByPrimaryKey(itemId);
		// 把结果添加到缓存
		try {
			jedisClient.set(ITEM_INFO + itemId + "" + DESC, JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(ITEM_INFO + itemId + "" + DESC, TIME_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return itemDesc;
	}

}
