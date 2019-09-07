package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.dao.TbUserDao;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserQuery;
import com.taotao.pojo.TbUserQuery.Criteria;
import com.taotao.sso.service.UserService;

/**
 * 用户管理Service
 * @author liut
 * @date 2019年3月2日下午8:14:20
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserDao userDao;
	@Autowired
	private JedisClient jedisClient;
	@Value("${USER_SESSION}")
	private String USER_SESSION;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	
	/**
	 * 校验数据
	 * @autor liut
	 * @date  2019年3月2日下午8:14:20
	 * @params
	 * @return @see com.taotao.sso.service.UserService#checkData(java.lang.String, int)
	 */
	@Override
	public TaotaoResult checkData(String data, int type) {
		
		TbUserQuery query = new TbUserQuery();
		Criteria criteria = query.createCriteria();
		//设置查询条件
		if(type == 1) {
			//用户名是否已注册
			criteria.andUsernameEqualTo(data);
		}else if(type == 2) {
			//手机号是否已注册
			criteria.andPhoneEqualTo(data);
		}else if(type == 3) {
			//邮箱是否已注册
			criteria.andEmailEqualTo(data);
		}else {
			return TaotaoResult.build(500, "非法数据");
		}
		List<TbUser> list = userDao.selectByExample(query);
		if(list != null && list.size() > 0) {
			//查询到数据，数据不可用
			return TaotaoResult.ok(false);
		}
		//数据可用
		return TaotaoResult.ok(true);
	
	}

	/**
	 * 用户注册
	 * @autor liut
	 * @date  2019年3月2日下午8:50:34
	 * @params
	 * @return @see com.taotao.sso.service.UserService#register(com.taotao.pojo.TbUser)
	 */
	@Override
	public TaotaoResult register(TbUser user) {
		//检查数据的有效性
		if (StringUtils.isBlank(user.getUsername())) {
			return TaotaoResult.build(400, "用户名不能为空");
		}
		//判断用户名是否重复
		TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
		if (!(boolean) taotaoResult.getData()) {
			return TaotaoResult.build(400, "用户名重复");
		}
		//判断密码是否为空
		if (StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "密码不能为空");
		}
		if (StringUtils.isNotBlank(user.getPhone())) {
			//是否重复校验
			taotaoResult = checkData(user.getPhone(), 2);
			if (!(boolean) taotaoResult.getData()) {
				return TaotaoResult.build(400, "电话号码重复");
			}
		}
		//如果email不为空，进行是否重复校验
		if (StringUtils.isNotBlank(user.getEmail())) {
			//是否重复校验
			taotaoResult = checkData(user.getEmail(), 3);
			if (!(boolean) taotaoResult.getData()) {
				return TaotaoResult.build(400, "email重复");
			}
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//md5加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		//插入数据库
		userDao.insert(user);
		//返回注册成功
		return TaotaoResult.ok();
	}

	/**
	 * 用户登录
	 * @autor liut
	 * @date  2019年3月2日下午8:50:34
	 * @params
	 * @return @see com.taotao.sso.service.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public TaotaoResult login(String username, String password) {
		//判断用户名和密码是否正确
		TbUserQuery query = new TbUserQuery();
		Criteria criteria = query.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userDao.selectByExample(query);
		if (list == null || list.size() == 0) {
			//返回登录失败
			return TaotaoResult.build(400, "用户名或密码不正确");
		}
		TbUser user = list.get(0);
		//密码要进行md5加密然后再校验
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			//返回登录失败
			return TaotaoResult.build(400, "用户名或密码不正确");
		}
		//生成token，使用uuid
		String token = UUID.randomUUID().toString();
		//清空密码
		user.setPassword(null);
		//把用户信息保存到redis，key就是token，value就是用户信息
		jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
		//设置key的过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		//返回登录成功，其中要把token返回。
		return TaotaoResult.ok(token);
	}

	/**
	 * 根据token获取用户信息
	 * @autor liut
	 * @date  2019年3月2日下午11:02:25
	 * @params
	 * @return @see com.taotao.sso.service.UserService#getUserByToken(java.lang.String)
	 */
	@Override
	public TaotaoResult getUserByToken(String token) {
		String json = jedisClient.get(USER_SESSION + ":" + token);
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "用户登录已经过期");
		}
		//重置Session的过期时间
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		//把json转换成User对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return TaotaoResult.ok(user);
	}

	/**
	 * 安全退出
	 * @autor liut
	 * @date  2019年3月2日下午11:10:58
	 * @params
	 * @return @see com.taotao.sso.service.UserService#loginOut(java.lang.String)
	 */
	@Override
	public TaotaoResult loginOut(String token) {
		//根据token删除redis中用户信息
		jedisClient.del(USER_SESSION + ":" + token);
		return TaotaoResult.ok();
	}

}
