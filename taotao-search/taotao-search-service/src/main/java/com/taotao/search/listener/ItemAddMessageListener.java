package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.service.SearchItemService;

/**
 * 	监听商品添加、修改、上架事件，同步索引库
 * @author liut
 * @date 2019年2月23日下午6:33:23
 */
public class ItemAddMessageListener implements MessageListener {
	
	@Autowired
	private SearchItemService searchItemService;
	
	/**
	 * 	接收消息并将商品文档添加至索引库
	 * @autor liut
	 * @date  2019年2月27日下午3:30:48
	 * @params 接收信息
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
			searchItemService.addItemDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
