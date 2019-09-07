package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 	接受ActiveMQ发送的消息
 * @author liut
 * @date 2019年2月23日下午5:49:12
 */
public class MyMessageListener implements MessageListener {

	/**
	 * 测试监听接收消息
	 * @autor liut
	 * @date  2019年2月27日下午3:32:40
	 * @params
	 * @return @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {
			//接收到消息
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
