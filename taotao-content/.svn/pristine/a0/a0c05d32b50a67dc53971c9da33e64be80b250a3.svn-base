package com.taotao.jedis;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;

/**
 * 集群版jedis
 * @author liut
 * @date 2019年2月27日上午2:42:56
 */
public class JedisClientCluster implements JedisClient {
	
	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

	/* (non-Javadoc)
	 * @see com.taotao.jedis.JedisClient#del(java.lang.String)
	 */
	@Override
	public Long del(String key) {
		return jedisCluster.del(key);
	}

	/* (non-Javadoc)
	 * @see com.taotao.jedis.JedisClient#flushAll()
	 */
	@Override
	public String flushAll() {
		return jedisCluster.flushAll();
	}

}
