package com.taotao.jedis;

/**
 * Jedis接口
 * @author liut
 * @date 2019年2月27日上午2:42:23
 */
public interface JedisClient {
	
	/**
	 * 设置字符串类型缓存
	 * @autor liut
	 * @date  2019年2月27日上午2:43:59
	 * @params
	 * @return String
	 */
	String set(String key, String value);
	
	/**
	 * 获取字符串缓存
	 * @autor liut
	 * @date  2019年2月27日上午2:44:17
	 * @params
	 * @return String
	 */
	String get(String key);
	
	/**
	 * 根据键删除缓存
	 * @autor liut
	 * @date  2019年2月27日上午2:44:28
	 * @params
	 * @return Long
	 */
	Long del(String key);
	
	/**
	 * 缓存是否存在
	 * @autor liut
	 * @date  2019年2月27日上午2:45:01
	 * @params
	 * @return Boolean
	 */
	Boolean exists(String key);
	
	/**
	 * 设置过期时间
	 * @autor liut
	 * @date  2019年2月27日上午2:45:17
	 * @params
	 * @return Long
	 */
	Long expire(String key, int seconds);
	
	/**
	 * 查看过期时间 -1永不过期 -2不存在 倒计时
	 * @autor liut
	 * @date  2019年2月27日上午2:45:28
	 * @params
	 * @return Long
	 */
	Long ttl(String key);
	
	/**
	 * 缓存值+1
	 * @autor liut
	 * @date  2019年2月27日上午2:45:54
	 * @params
	 * @return Long
	 */
	Long incr(String key);
	
	/**
	 * 设置hash类型缓存
	 * @autor liut
	 * @date  2019年2月27日上午2:46:10
	 * @params
	 * @return Long
	 */
	Long hset(String key, String field, String value);
	
	/**
	 * 获取hash类型缓存
	 * @autor liut
	 * @date  2019年2月27日上午2:46:26
	 * @params
	 * @return String
	 */
	String hget(String key, String field);
	
	/**
	 * 删除hash类型缓存
	 * @autor liut
	 * @date  2019年2月27日上午2:46:36
	 * @params
	 * @return Long
	 */
	Long hdel(String key, String... field);
	
	/**
	 * 清空缓存数据库
	 * @autor liut
	 * @date  2019年2月27日上午2:46:51
	 * @params
	 * @return String
	 */
	String flushAll();
}
