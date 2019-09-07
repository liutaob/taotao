package com.taotao.mycat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

/**
 * 	测试配置的mycat 分库分表	主从复制 读写分离
 * @author liut
 * @date 2019年5月3日下午6:30:07
 */
public class TestMyCat {
	
	@Test
	public void testMyCat()  {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.25.136:8066/taotao","taotao","taotao");
			Statement statement = connection.createStatement();
			String sql = "select * from tb_item where id=4914998";
			ResultSet res = statement.executeQuery(sql);
			while(res.next()) {
				 System.out.println(res.getInt("id"));//"id"
				 System.out.println(res.getString("title"));
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
