/**
 * 
 */
package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.dao.TbItemDescDao;
import com.taotao.dao.TbItemDao;
import com.taotao.dao.TbItemParamItemDao;
import com.taotao.dao.TbItemParamDao;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemQuery;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamQuery;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemQuery;
import com.taotao.pojo.TbItemParamQuery.Criteria;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 * @author liut
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemDao itemDao;
	@Autowired
	private TbItemDescDao itemDescDao;
	@Autowired
	private TbItemParamDao itemParamDao;
	@Autowired
	private TbItemParamItemDao itemParamItemDao;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name="itemAddTopic")
	private Destination itemAddTopic;
	//可以使用同一个
	@Resource(name="itemDeleteTopic")
	private Destination itemDeleteTopic;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${BASE}")
	private String BASE;
	@Value("${TIME_EXPIRE}")
	private Integer TIME_EXPIRE;
	
	/**
	 * 根据id获取商品
	 * @autor liut
	 * @date  2019年2月27日上午3:11:52
	 * @params
	 * @return @see com.taotao.service.ItemService#getItemById(long)
	 */
	@Override
	public TbItem getItemById(long id) {
		// 先查询缓存
		// 添加缓存不能影响正常业务逻辑
		try {
			// 查询缓存
			String json = jedisClient.get(ITEM_INFO+ id + BASE);
			// 查询到结果，把json转换成TbItem返回
			if (StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有命中，需要查询数据库
		TbItem tbItem = itemDao.selectByPrimaryKey(id);
		// 把结果添加到缓存并设置缓存时间
		try {
			jedisClient.set(ITEM_INFO + id + "" + BASE, JsonUtils.objectToJson(tbItem));
			jedisClient.expire(ITEM_INFO + id + "" + BASE, TIME_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return tbItem;
	}
	
	/**
	 * 商品分页展示
	 * @autor liut
	 * @date  2019年2月27日上午3:12:26
	 * @params
	 * @return @see com.taotao.service.ItemService#getItemList(int, int)
	 */
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemQuery example = new TbItemQuery();
		List<TbItem> list = itemDao.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		//取查询结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 新增商品
	 * @autor liut
	 * @date  2019年2月27日上午3:12:33
	 * @params
	 * @return @see com.taotao.service.ItemService#addItem(com.taotao.pojo.TbItem, java.lang.String, java.lang.String)
	 */
	@Override
	public TaotaoResult addItem(TbItem tbItem, String desc,String paramData) {
		//生成商品id
		Long itemId = IDUtils.genItemId();
		//补全item的属性
		tbItem.setId(itemId);
		//商品状态，1--正常、2--下架、3--删除
		tbItem.setStatus((byte)1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		//向商品表插入数据
		itemDao.insert(tbItem);
		//创建一个商品表描述对应的pojo
		TbItemDesc tbItemDesc = new TbItemDesc();
		//补全pojo的属性
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		//向商品描述表插入数据
		itemDescDao.insert(tbItemDesc);
		//设置商品类目规格参数查询条件
		TbItemParamQuery example = new TbItemParamQuery();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(tbItem.getCid());
		//根据选择的商品类目id查找商品规格参数
		List<TbItemParam> list = itemParamDao.selectByExampleWithBLOBs(example);
		if(list !=null && list.size()!=0) {
			//向商品规格表tb_item_param_item中插入数据
			TbItemParamItem tbItemParamItem = new TbItemParamItem();
			tbItemParamItem.setCreated(new Date());
			tbItemParamItem.setUpdated(new Date());
			tbItemParamItem.setItemId(itemId);
			tbItemParamItem.setParamData(paramData);
			itemParamItemDao.insert(tbItemParamItem);
		}
		//向ActiveMQ发送一个商品添加的消息
		jmsTemplate.send(itemAddTopic, new MessageCreator() {
			//发送商品id
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(itemId+"");
				return message;
			}
		});
		//返回结果
		return TaotaoResult.ok();
	}

	/**
	 * 删除勾选商品
	 * @autor liut
	 * @date  2019年2月27日上午3:12:47
	 * @params
	 * @return @see com.taotao.service.ItemService#deleteItem(java.lang.String)
	 */
	@Override
	public TaotaoResult deleteItem(String ids) {
		//切割选中的商品
		String[] split = ids.split("[,]");
		for (String string : split) {
			long id = Long.parseLong(string);
			//删除商品描述
			itemDescDao.deleteByPrimaryKey(id);
			//根据商品itemId查询该商品的规格参数并删除
			TbItemParamItemQuery example = new TbItemParamItemQuery();
			com.taotao.pojo.TbItemParamItemQuery.Criteria criteria = example.createCriteria();
			criteria.andItemIdEqualTo(id);
			itemParamItemDao.deleteByExample(example);
			//删除商品信息
			itemDao.deleteByPrimaryKey(id);//真正删除
			//向ActiveMQ发送一个删除商品的消息
			jmsTemplate.send(itemDeleteTopic, new MessageCreator() {
				//发送商品id
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = session.createTextMessage(id+"");
					return message;
				}
			});
		}
		return TaotaoResult.ok();
	}

	/**
	 * 根据商品id获取商品描述
	 * @autor liut
	 * @date  2019年2月27日上午3:12:58
	 * @params
	 * @return @see com.taotao.service.ItemService#getItemDescById(long)
	 */
	@Override
	public TaotaoResult getItemDescById(long id) {
		TbItemDesc itemDesc = itemDescDao.selectByPrimaryKey(id);
		return TaotaoResult.ok(itemDesc);
	}


	/**
	 * 修改商品信息
	 * @autor liut
	 * @date  2019年2月27日上午3:13:15
	 * @params
	 * @return @see com.taotao.service.ItemService#updateItem(com.taotao.pojo.TbItem, java.lang.String, java.lang.String)
	 */
	@Override
	public TaotaoResult updateItem(TbItem tbItem, String desc,String paramData) {
		//根据选中的商品id查找到要更新的商品
		TbItem oldTbItem = itemDao.selectByPrimaryKey(tbItem.getId());
		//补全item的属性
		tbItem.setId(oldTbItem.getId());
		//商品状态，1--正常、2--下架、3--删除
		tbItem.setStatus(oldTbItem.getStatus());
		tbItem.setCreated(oldTbItem.getCreated());
		tbItem.setUpdated(new Date());
		//更新商品信息
		itemDao.updateByPrimaryKey(tbItem);
		//补全更改的itemDesc属性
		TbItemDesc tbItemDesc = itemDescDao.selectByPrimaryKey(oldTbItem.getId());
		tbItemDesc.setCreated(tbItemDesc.getCreated());
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemDesc(desc);
		//更新商品描述数据库
		itemDescDao.updateByPrimaryKey(tbItemDesc);
		//根据商品id查找商品规格参数tb_item_param_item表
		TbItemParamItemQuery example = new TbItemParamItemQuery();
		com.taotao.pojo.TbItemParamItemQuery.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(tbItem.getId());
		//一个商品itemId对应着一个规格参数
		List<TbItemParamItem> list = itemParamItemDao.selectByExampleWithBLOBs(example);
		if(list != null && list.size() != 0) {
			//获取到要更新的商品的商品规格参数并更新数据库
			TbItemParamItem tbItemParamItem = list.get(0);
			tbItemParamItem.setUpdated(new Date());
			tbItemParamItem.setParamData(paramData);
			itemParamItemDao.updateByPrimaryKeyWithBLOBs(tbItemParamItem);
		}
		//向ActiveMQ发送一个修改商品的消息(注意索引库更新数据会删除原数据重新)
		jmsTemplate.send(itemAddTopic, new MessageCreator() {
			//发送商品id
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(oldTbItem.getId()+"");
				return message;
			}
		});
		return TaotaoResult.ok();
	}

	/**
	 * 商品上架
	 * @autor liut
	 * @date  2019年2月27日上午3:13:25
	 * @params
	 * @return @see com.taotao.service.ItemService#reshelfItem(java.lang.String)
	 */
	@Override
	public TaotaoResult reshelfItem(String ids) {
		//切割所选商品数组
		String[] split = ids.split("[,]");
		for (String string : split) {
			long id = Long.parseLong(string);
			//根据id查找商品数据库并设置商品状态为1，更新数据库
			TbItem tbItem = itemDao.selectByPrimaryKey(id);
			tbItem.setStatus((byte)1);
			itemDao.updateByPrimaryKey(tbItem);
			//向ActiveMQ发送一个修改商品的消息 上架即索引库增加
			jmsTemplate.send(itemAddTopic, new MessageCreator() {
				//发送商品id
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = session.createTextMessage(id+"");
					return message;
				}
			});
		}
		//返回结果
		return TaotaoResult.ok();
	}
	
	/**
	 * 商品下架
	 * @autor liut
	 * @date  2019年2月27日上午3:13:33
	 * @params
	 * @return @see com.taotao.service.ItemService#instockItem(java.lang.String)
	 */
	@Override
	public TaotaoResult instockItem(String ids) {
		//切割所选商品数组
		String[] split = ids.split("[,]");
		for (String string : split) {
			long id = Long.parseLong(string);
			//根据id查找到商品
			TbItem tbItem = itemDao.selectByPrimaryKey(id);
			//商品状态设为2
			tbItem.setStatus((byte)2);
			//更新数据库
			itemDao.updateByPrimaryKey(tbItem);
			//向ActiveMQ发送一个修改商品的消息	下架就是删除索引库
			jmsTemplate.send(itemDeleteTopic, new MessageCreator() {
				//发送商品id
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = session.createTextMessage(id+"");
					return message;
				}
			});
		}
		//返回结果
		return TaotaoResult.ok();
	}

	

}
