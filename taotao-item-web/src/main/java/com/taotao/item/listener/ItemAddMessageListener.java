package com.taotao.item.listener;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.taotao.model.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemDescService;
import com.taotao.service.ItemParamService;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;


/**
 * 新增商品并生成商品详情页面	freemarker
 * @author liut
 * @date 2019年2月27日下午4:02:55
 */
public class ItemAddMessageListener implements MessageListener {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemParamService itemParamService;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;
	
	/**
	 *  根据接收消息的商品id生成静态页面
	 * @autor liut
	 * @date  2019年2月27日下午3:41:57
	 * @params
	 * @return @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			//从消息中取商品id
			String text = textMessage.getText();
			long itemId = Long.parseLong(text);
			//根据商品id查询数据，取商品信息
			//等待事务提交	也可改为在表现层发消息（此时事务已提交）
			Thread.sleep(2000);
			//创建configuration对象
			Configuration configuration = freeMarkerConfigurer.createConfiguration();
			//获取模板
			Template template = configuration.getTemplate("item.ftl");
			//创建数据集
			Map dataModel = new HashMap(16);
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = itemDescService.getItemDescById(itemId);
			TbItemParamItem itemParam = itemParamService.getItemParamItemByItemId(itemId);
			dataModel.put("item", item);
			dataModel.put("itemDesc", itemDesc);
			dataModel.put("itemParam", itemParam);
			//创建一个Writer对象，指定输出文件的路径及文件名
			Writer out = new FileWriter(HTML_OUT_PATH+itemId+".html");
			//使用模板那对象的process方法输出文件
			template.process(dataModel, out);
			//关流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
