/**
 * 
 */
package com.taotao.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dao.TbItemDao;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemQuery;

/**
 * @author liut
 *分页测试
 */
public class TestPageHelper {
	
	@Test
	public void testPageHelper() throws Exception{
		//1，在mybatis的配置文件中配置分页插件
		//2，将执行查询之前配置分页条件，使用PageHelper的静态方法
		PageHelper.startPage(1, 10);
		//3，执行查询
		ApplicationContext applicationContext = new  ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemDao itemMapper = applicationContext.getBean(TbItemDao.class);
		//创建example对象
		TbItemQuery example = new TbItemQuery();
//		Criteria criteria = example.createCriteria();
		List<TbItem> list = itemMapper.selectByExample(example);
		//4，取分页信息，使用PageInfo对象取
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println("总记录数："+pageInfo.getTotal());
		System.out.println("总页数："+pageInfo.getPages());
		System.out.println("返回的记录数："+list.size());
	}
}
