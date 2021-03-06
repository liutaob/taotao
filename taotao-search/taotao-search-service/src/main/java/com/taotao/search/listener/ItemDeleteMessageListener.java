package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.service.SearchItemService;

/**
 * 	监听商品删除、下架事件，同步索引库
 * @author liut
 * @date 2019年2月23日下午6:33:23
 */
public class ItemDeleteMessageListener implements MessageListener {
	
	@Autowired
	private SearchItemService searchItemService;
	
	/**
	    *将商品删除同步至索引库	
	 * @autor liut
	 * @date  2019年2月27日下午3:31:51
	 * @params
	 * @return @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		//从消息中取商品id
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			//根据商品id查询数据，取商品信息
			//等待事务提交	也可改为在表现层发消息（此时事务已提交）
			Thread.sleep(2000);
			//调用向索引库中添加商品文档方法
			searchItemService.deleteItemDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
