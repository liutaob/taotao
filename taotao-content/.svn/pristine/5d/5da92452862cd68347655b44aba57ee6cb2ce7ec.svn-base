package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.dao.TbContentCategoryDao;
import com.taotao.dao.TbContentDao;
import com.taotao.jedis.JedisClient;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryQuery;
import com.taotao.pojo.TbContentCategoryQuery.Criteria;
import com.taotao.pojo.TbContentQuery;

/**
 * 内容分类管理
 * 
 * @author liut
 * @date 上午11:22:31
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryDao tbContentCategoryDao;
	@Autowired
	private TbContentDao tbContentDao;
	@Autowired
	private JedisClient jedisClient;

	/**
	 * 根据父结点id查找内容分类列表 内容分类展示
	 * @autor liut
	 * @date  2019年2月27日上午2:35:49
	 * @params
	 * @return @see com.taotao.content.service.ContentCategoryService#getContentCategoryList(long)
	 */
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// 根据父节点id查询子节点列表
		TbContentCategoryQuery example = new TbContentCategoryQuery();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		// 设置parentid
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = tbContentCategoryDao.selectByExample(example);
		// 转换成EasyUITreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			// 如果节点下有子节点“closed”，如果没有子节点“open”
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			// 添加到节点列表
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 新增内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:37:54
	 * @params 父结点id 新增内容分类名
	 * @return @see com.taotao.content.service.ContentCategoryService#addContentCategory(java.lang.Long, java.lang.String)
	 */
	@Override
	public TaotaoResult addContentCategory(Long parentId, String name) {
		// 先根据内容分类id查询内容列表
		TbContentQuery example = new TbContentQuery();
		com.taotao.pojo.TbContentQuery.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(parentId);
		List<TbContent> contentList = tbContentDao.selectByExample(example);
		// 要新增的内容分类下有内容
		if (!contentList.isEmpty()) {
			// 给出提示创建失败
			return TaotaoResult.build(500, "该内容分类下包含内容");
		}
		TbContentCategory tbContentCategory = new TbContentCategory();
		// 补全内容分类属性
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		// 插入数据库
		tbContentCategoryDao.insert(tbContentCategory);
		// 同步缓存
		// 删除对应的缓存信息
		jedisClient.del(tbContentCategory.getId().toString());
		// 查找父节点
		TbContentCategory parent = tbContentCategoryDao.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			// 如果父节点为叶子节点改为父节点
			parent.setIsParent(true);
			// 更新父节点
			tbContentCategoryDao.updateByPrimaryKey(parent);
		}
		// 返回结果
		return TaotaoResult.ok(tbContentCategory);
	}

	/**
	 * 重命名内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:38:16
	 * @params 内容分类id 更改后名字
	 * @return @see com.taotao.content.service.ContentCategoryService#updateContentCategory(java.lang.Long, java.lang.String)
	 */
	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		// 根据id查找到要重命名的结点
		TbContentCategory tbContentCategory = tbContentCategoryDao.selectByPrimaryKey(id);
		// 重命名时间
		tbContentCategory.setUpdated(new Date());
		// 重命名结点名字
		tbContentCategory.setName(name);
		// 更新结点
		tbContentCategoryDao.updateByPrimaryKey(tbContentCategory);
		// 同步缓存
		// 删除对应的缓存信息
		jedisClient.del(tbContentCategory.getId().toString());
		// 返回结果
		return TaotaoResult.ok();
	}

	/**
	 * 删除内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:38:37
	 * @params 内容分类id
	 * @return @see com.taotao.content.service.ContentCategoryService#deleteContentCategory(java.lang.Long)
	 */
	@Override
	public TaotaoResult deleteContentCategory(Long id) {
		// 先根据内容分类id查询内容列表
		TbContentQuery contentExample = new TbContentQuery();
		com.taotao.pojo.TbContentQuery.Criteria contentCriteria = contentExample.createCriteria();
		contentCriteria.andCategoryIdEqualTo(id);
		List<TbContent> contentList = tbContentDao.selectByExample(contentExample);
		// 要删除的内容分类下有内容
		if (!contentList.isEmpty()) {
			// 给出提示删除失败
			return TaotaoResult.build(500, "该内容分类下包含内容");
		}
		TbContentCategory contentCategory = tbContentCategoryDao.selectByPrimaryKey(id);
		// 如果不是父节点
		if (!contentCategory.getIsParent()) {
			// 查找到父节点
			TbContentCategory parent = tbContentCategoryDao.selectByPrimaryKey(contentCategory.getParentId());
			// 根据父节点id查询子节点列表
			TbContentCategoryQuery example = new TbContentCategoryQuery();
			// 设置查询条件
			Criteria criteria = example.createCriteria();
			// 设置parentid
			criteria.andParentIdEqualTo(contentCategory.getParentId());
			// 执行查询
			List<TbContentCategory> list = tbContentCategoryDao.selectByExample(example);
			// 如果所属父结点下只有该结点，将其删除，并将父节点变为叶子结点，否则删除该叶子结点
			if (list.size() == 1) {
				parent.setIsParent(false);
				tbContentCategoryDao.updateByPrimaryKey(parent);
			}
		} else {// 如果是父节点1,不让删除 2，递归删
				// 1不删，不做任何处理
			return TaotaoResult.build(500, "该目录不为空，无法删除");
			// 2递归删除该父节点下所有子结点
//			diGuiDelete(id);
		}
		// 删除该结点
		tbContentCategoryDao.deleteByPrimaryKey(id);
		// 同步缓存
		// 删除对应的缓存信息
		jedisClient.del(id.toString());
		return TaotaoResult.ok();
	}

	/**
	 * 递归删除父节点以及所有叶子结点
	 * 
	 * @autor liut
	 * @date 下午2:30:24
	 * @params 父结点id
	 * @return void
	 */
	private void diGuiDelete(long id) {
		// 根据父节点id查询子节点列表
		TbContentCategoryQuery example = new TbContentCategoryQuery();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		// 设置parentid
		criteria.andParentIdEqualTo(id);
		// 查询父节点为要删除结点的所有子结点列表
		List<TbContentCategory> list = tbContentCategoryDao.selectByExample(example);
		for (TbContentCategory tbContentCategory : list) {
			if (tbContentCategory.getIsParent()) {
				// 把该父节点变为子结点
				TbContentCategory parent = tbContentCategoryDao.selectByPrimaryKey(tbContentCategory.getParentId());
				parent.setIsParent(false);
				tbContentCategoryDao.updateByPrimaryKey(parent);
				diGuiDelete(tbContentCategory.getId());
			}
			// 若是叶子结点删除叶子结点，或删除最后一个由空父结点变成的叶子结点
			tbContentCategoryDao.deleteByPrimaryKey(tbContentCategory.getId());
			// 同步缓存
			// 删除对应的缓存信息
			jedisClient.del(tbContentCategory.getId().toString());
		}
	}

	/**
	 * 获取内容分类
	 * @autor liut
	 * @date  2019年2月27日上午2:40:12
	 * @params 内容分类id
	 * @return @see com.taotao.content.service.ContentCategoryService#getContentCategoryById(java.lang.Long)
	 */
	@Override
	public TbContentCategory getContentCategoryById(Long id) {
		// 先查询缓存
		// 添加缓存不能影响正常业务逻辑
		try {
			// 查询缓存
			String json = jedisClient.get(id.toString());
			// 查询到结果，把json转换成List返回
			if (StringUtils.isNotBlank(json)) {
				TbContentCategory contentCategory = JsonUtils.jsonToPojo(json, TbContentCategory.class);
				return contentCategory;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentCategory contentCategory = tbContentCategoryDao.selectByPrimaryKey(id);
		// 把结果添加到缓存
		try {
			jedisClient.set(id.toString(), JsonUtils.objectToJson(contentCategory));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentCategory;
	}

}
