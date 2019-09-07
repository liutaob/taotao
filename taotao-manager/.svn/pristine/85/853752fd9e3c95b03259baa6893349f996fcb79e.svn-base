package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.dao.TbItemParamExDao;
import com.taotao.dao.TbItemParamItemDao;
import com.taotao.dao.TbItemParamDao;
import com.taotao.jedis.JedisClient;
import com.taotao.model.TbItemParamModel;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamQuery;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemQuery;
import com.taotao.pojo.TbItemParamQuery.Criteria;
import com.taotao.service.ItemParamService;

/**
 * 商品类目、商品规格参数
 * @author liut
 * @date 下午8:15:13
 */
@Service("itemParamService")
public class ItemParamServiceImpl implements ItemParamService {
	
	@Autowired
	private TbItemParamDao tbItemParamDao;
	@Autowired
	//tb_item_param_item表 区别于其他商品分类itemCatid 这个是商品itemId
	private TbItemParamItemDao tbItemParamItemDao;
	@Autowired
	//扩展的规格参数类 解决分页显示时商品类目为空问题
	private TbItemParamExDao tbItemParamExDao;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${PARAM}")
	private String PARAM;
	@Value("${TIME_EXPIRE}")
	private Integer TIME_EXPIRE;
	
	/**
	 * 根据id查询商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午2:57:42
	 * @params
	 * @return @see com.taotao.service.ItemParamService#getItemParamById(long)
	 */
	@Override
	public TbItemParam getItemParamById(long id) {
		TbItemParam itemParam = tbItemParamDao.selectByPrimaryKey(id);
		return itemParam;
	}
	
	/**
	 * 分页展示商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午3:04:59
	 * @params
	 * @return @see com.taotao.service.ItemParamService#getItemParamList(int, int)
	 */
	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		List<TbItemParamModel> list = tbItemParamExDao.selectItemParamList();
		PageInfo<TbItemParamModel> pageInfo = new PageInfo<TbItemParamModel>(list);
		//取查询结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	
	/**
	 * 点击编辑商品时加载商品规格
	 * @autor liut
	 * @date  2019年2月27日上午3:04:50
	 * @params
	 * @return @see com.taotao.service.ItemParamService#getItemParamItemById(long)
	 */
	@Override
	public TaotaoResult getItemParamItemById(long itemId) {
		TbItemParamItemQuery example = new TbItemParamItemQuery();
		com.taotao.pojo.TbItemParamItemQuery.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//根据商品id限制查询条件 带大字段的查找到商品规格	每一个商品和规格参数一一对应
		List<TbItemParamItem> list = tbItemParamItemDao.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}else {
			return TaotaoResult.ok();
		}
	}
	
	/**
	 * 判断选择的商品类目是否已经添加过规格
	 * @autor liut
	 * @date  2019年2月27日上午3:07:13
	 * @params
	 * @return @see com.taotao.service.ItemParamService#getItemParamByItemCatId(long)
	 */
	@Override
	public TaotaoResult getItemParamByItemCatId(long itemCatId) {
		TbItemParamQuery example = new TbItemParamQuery();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(itemCatId);
		List<TbItemParam> list = tbItemParamDao.selectByExampleWithBLOBs(example);
		if(list != null && list.size() != 0) {
			return TaotaoResult.ok(list.get(0));
		}else {
			return TaotaoResult.ok();
		}
	}

	/**
	 * 新增商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午3:06:11
	 * @params
	 * @return @see com.taotao.service.ItemParamService#addItemParam(java.lang.Long, java.lang.String)
	 */
	@Override
	public TaotaoResult addItemParam(Long itemCatId, String paramData) {
		TbItemParam itemParam = new TbItemParam();
		//补全规格参数属性
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParam.setItemCatId(itemCatId);
		itemParam.setParamData(paramData);
		//插入数据库
		tbItemParamDao.insert(itemParam);
		return TaotaoResult.ok();
	}

	/**
	 * 删除勾选的商品类目规格参数
	 * @autor liut
	 * @date  2019年2月27日上午3:06:22
	 * @params
	 * @return @see com.taotao.service.ItemParamService#deleteItemParam(java.lang.String)
	 */
	@Override
	public TaotaoResult deleteItemParam(String ids) {
		//将选中的规格参数切割
		String[] split = ids.split("[,]");
		for (String string : split) {
			//根据规格参数id删除数据库
			tbItemParamDao.deleteByPrimaryKey(Long.parseLong(string));
		}
		//返回结果
		return TaotaoResult.ok();
	}

	/**
	 * 根据商品id获取商品规格参数
	 * @autor liut
	 * @date  2019年2月27日上午3:06:42
	 * @params
	 * @return @see com.taotao.service.ItemParamService#getItemParamItemByItemId(long)
	 */
	@Override
	public TbItemParamItem getItemParamItemByItemId(long itemId) {
		//先从缓存中获取	try...catch以防redis崩溃
		try {
			String json = jedisClient.get(ITEM_INFO + "itemId" + PARAM);
			if(StringUtils.isNotBlank(json)) {
				TbItemParamItem param = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return param;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//设置查询条件
		TbItemParamItemQuery example = new TbItemParamItemQuery();
		com.taotao.pojo.TbItemParamItemQuery.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//缓存中没有则查询数据库
		List<TbItemParamItem> list = tbItemParamItemDao.selectByExample(example);
		if(list != null && list.size() >0) {
			//一个商品只有一个规格参数
			TbItemParamItem param = list.get(0);
			//将结果存进缓存
			jedisClient.set(ITEM_INFO+itemId+PARAM,JsonUtils.objectToJson(list));
			jedisClient.expire(ITEM_INFO + itemId + "" + PARAM, TIME_EXPIRE);
			//返回结果
			return param;
		}
		return null;
	}


}
