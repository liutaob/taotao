package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * 用户管理service接口
 * @author liut
 * @date 2019年3月2日下午8:12:53
 */
public interface UserService {
	
	/**
	 * 校验数据
	 * @autor liut
	 * @date  2019年3月2日下午8:24:36
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult checkData(String data,int type);
	
	/**
	 * 注册用户
	 * @autor liut
	 * @date  2019年3月2日下午8:47:42
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult register(TbUser user);
	
	/**
	 * 用户登录
	 * @autor liut
	 * @date  2019年3月2日下午8:50:44
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult login(String username, String password);
	
	/**
	 * 根据token获取用户信息
	 * @autor liut
	 * @date  2019年3月2日下午11:01:57
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult getUserByToken(String token);
	
	/**
	 * 安全退出
	 * @autor liut
	 * @date  2019年3月2日下午11:11:07
	 * @params
	 * @return TaotaoResult
	 */
	TaotaoResult loginOut(String token);
}
