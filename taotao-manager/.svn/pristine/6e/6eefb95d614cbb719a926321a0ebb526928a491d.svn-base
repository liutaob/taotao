package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.JsonUtils;
import com.taotao.dao.TbItemCatDao;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatQuery;
import com.taotao.pojo.TbItemCatQuery.Criteria;
import com.taotao.service.ItemCatService;

/**
 * 商品分类管理Service
 * @author liut
 * @date 上午2:02:01
 */
@Service("itemCatService")
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatDao itemCatDao;
	@Autowired
	private JedisClient jedisClient;
	
	/**
	 * 根据父结点id获取子结点列表 商品类目展示选择
	 * @autor liut
	 * @date  2019年2月27日上午3:01:40
	 * @params
	 * @return @see com.taotao.service.ItemCatService#getItemCatList(long)
	 */
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		//根据父节点id查询子节点列表
		TbItemCatQuery example = new TbItemCatQuery();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		//设置parentid
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatDao.selectByExample(example);
		//转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			//如果节点下有子节点“closed”，如果没有子节点“open”
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			//添加到节点列表
			resultList.add(node);
		}
		return resultList;
	}
	
	/**
	 * 首页展示所有商品类目
	 * @autor liut
	 * @date  2019年2月27日上午3:02:21
	 * @params
	 * @return @see com.taotao.service.ItemCatService#getAllItemCatList()
	 */
	@Override
	public List<TbItemCat> getAllItemCatList() {
		// 先查询缓存
		// 添加缓存不能影响正常业务逻辑
		try {
			// 查询缓存
			String json = jedisClient.get("allItemCat");
			// 查询到结果，把json转换成List返回
			if (StringUtils.isNotBlank(json)) {
				List<TbItemCat> list = JsonUtils.jsonToList(json, TbItemCat.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询数据库
		TbItemCatQuery example = new TbItemCatQuery();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo((long) 0);
		List<TbItemCat> list = itemCatDao.selectByExample(example);
		// 把结果添加到缓存
		try {
			jedisClient.set("allItemCat", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
