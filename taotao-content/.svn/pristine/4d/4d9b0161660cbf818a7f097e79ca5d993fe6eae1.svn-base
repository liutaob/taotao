package com.taotao.content.service.impl;

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
import com.taotao.content.service.ContentService;
import com.taotao.dao.TbContentDao;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentQuery;
import com.taotao.pojo.TbContentQuery.Criteria;

/**
 * 内容管理
 * 
 * @author liut
 * @date 下午6:38:01
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentDao contentDao;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;

	/**
	 * 内容分页展示
	 * @autor liut
	 * @date  2019年2月27日上午2:28:12
	 * @params 当前页数 行数	内容分类id
	 * @return @see com.taotao.content.service.ContentService#getContentList(int, int, java.lang.Long)
	 */
	@Override
	public EasyUIDataGridResult getContentList(int page, int rows, Long categoryId) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbContentQuery example = new TbContentQuery();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		// 设置categoryId
		criteria.andCategoryIdEqualTo(categoryId);
		// 查询数据库
		List<TbContent> list = contentDao.selectByExampleWithBLOBs(example);
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		// 取查询结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	/**
	 * 新增内容
	 * @autor liut
	 * @date  2019年2月27日上午2:30:15
	 * @params 内容
	 * @return @see com.taotao.content.service.ContentService#addContent(com.taotao.pojo.TbContent)
	 */
	@Override
	public TaotaoResult addContent(TbContent content) {
		// 补全内容对象属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 插入数据库
		contentDao.insert(content);
		// 删除对应的缓存信息，同步缓存
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		// 返回结果
		return TaotaoResult.ok();
	}

	/**
	 * 编辑内容
	 * @autor liut
	 * @date  2019年2月27日上午2:31:23
	 * @params 内容
	 * @return @see com.taotao.content.service.ContentService#updateContent(com.taotao.pojo.TbContent)
	 */
	@Override
	public TaotaoResult updateContent(TbContent content) {
		// 根据id查找到要更新的内容列表
		TbContent oldContent = contentDao.selectByPrimaryKey(content.getId());
		// 修改内容属性
		content.setCreated(oldContent.getCreated());
		content.setUpdated(new Date());
		// 更新数据库
		contentDao.updateByPrimaryKeyWithBLOBs(content);
		// 同步缓存
		// 删除对应的缓存信息
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	/**
	 * 删除内容
	 * @autor liut
	 * @date  2019年2月27日上午2:31:51
	 * @params 勾选的ids字符串
	 * @return @see com.taotao.content.service.ContentService#deleteContent(java.lang.String)
	 */
	@Override
	public TaotaoResult deleteContent(String ids) {
		// 切割逐一删除
		String[] split = ids.split("[,]");
		for (String string : split) {
			long id = Long.parseLong(string);
			contentDao.deleteByPrimaryKey(id);
			// 删除对应的缓存信息，同步缓存
			TbContent content = contentDao.selectByPrimaryKey(id);
			jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		}
		return TaotaoResult.ok();
	}

	/**
	 * 获取内容列表 首页广告
	 * @autor liut
	 * @date  2019年2月27日上午2:33:38
	 * @params 内容分类id
	 * @return @see com.taotao.content.service.ContentService#getContenListByCid(java.lang.Long)
	 */
	@Override
	public List<TbContent> getContenListByCid(Long categoryId) {
		// 先查询缓存
		// 添加缓存不能影响正常业务逻辑，预防redis挂掉
		try {
			// 查询缓存
			String json = jedisClient.hget(INDEX_CONTENT, categoryId + "");
			// 查询到结果，把json转换成List返回
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 缓存中没有命中，需要查询数据库
		TbContentQuery example = new TbContentQuery();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andCategoryIdEqualTo(categoryId);
		// 执行查询
		List<TbContent> list = contentDao.selectByExample(example);
		// 把结果添加到缓存
		try {
			jedisClient.hset(INDEX_CONTENT, categoryId + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return list;
	}


}
