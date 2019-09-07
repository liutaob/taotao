package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbItemCat;
import com.taotao.portal.pojo.ADNode;
import com.taotao.portal.pojo.ScrollADNode;
import com.taotao.service.ItemCatService;

/**
 * 首页展示Controller
 * 
 * @author liut
 * @date 下午5:25:27
 */
@Controller
public class IndexController {

	@Autowired
	private ContentService contentService;
	@Autowired
	private ContentCategoryService contentCategoryService;
	@Autowired
	private ItemCatService itemCatService;
	
	// 轮播图下广告
	@Value("${Scroll_CATEGORY_ID}")
	private Long Scroll_CATEGORY_ID;
	//淘淘快报
	@Value("${TAOTAO_NEWS_CATEGORY_ID}")
	private Long TAOTAO_NEWS_CATEGORY_ID;
	//家电通讯
	@Value("${HOME_APPLIANCE_CATEGORY_ID}")
	private Long HOME_APPLIANCE_CATEGORY_ID;
	
	// 首页轮播图大广告
	@Value("${SLIDE_CATEGORY_ID}")
	private Long SLIDE_CATEGORY_ID;
	@Value("${SLIDE_HEIGHT}")
	private Integer SLIDE_HEIGHT;
	@Value("${SLIDE_WIDTH}")
	private Integer SLIDE_WIDTH;
	@Value("${SLIDE_HEIGHT_B}")
	private Integer SLIDE_HEIGHT_B;
	@Value("${SLIDE_WIDTH_B}")
	private Integer SLIDE_WIDTH_B;
	
	// 右上方广告
	@Value("${RIGHT_TOP_CATEGORY_ID}")
	private Long RIGHT_TOP_CATEGORY_ID;
	@Value("${RIGHT_TOP_HEIGHT}")
	private Integer RIGHT_TOP_HEIGHT;
	@Value("${RIGHT_TOP_WIDTH}")
	private Integer RIGHT_TOP_WIDTH;
	@Value("${RIGHT_TOP_HEIGHT_B}")
	private Integer RIGHT_TOP_HEIGHT_B;
	@Value("${RIGHT_TOP_WIDTH_B}")
	private Integer RIGHT_TOP_WIDTH_B;
	
	//品牌旗舰店
	@Value("${BRAND_FLAGSHIP_STORE_CID}")
	private Long BRAND_FLAGSHIP_STORE_CID;
	@Value("${BRAND_FLAGSHIP_STORE_HEIGHT}")
	private Integer BRAND_FLAGSHIP_STORE_HEIGHT;
	@Value("${BRAND_FLAGSHIP_STORE_WIDTH}")
	private Integer BRAND_FLAGSHIP_STORE_WIDTH;
	@Value("${BRAND_FLAGSHIP_STORE_HEIGHT_B}")
	private Integer BRAND_FLAGSHIP_STORE_HEIGHT_B;
	@Value("${BRAND_FLAGSHIP_STORE_WIDTH_B}")
	private Integer BRAND_FLAGSHIP_STORE_WIDTH_B;
	
	//家电通讯下的商品图广告
	@Value("${ITEM_CID}")
	private Long ITEM_CID;
	@Value("${ITEM_HEIGHT}")
	private Integer ITEM_HEIGHT;
	@Value("${ITEM_WIDTH}")
	private Integer ITEM_WIDTH;
	
	/**
	 * 访问前台portal首页
	 * @autor liut
	 * @date  2019年2月27日下午3:39:24
	 * @params
	 * @return String
	 */
	@RequestMapping("/index")
	public String showIndex(Model model) {
		// 取轮播图的内容信息
		List<ADNode> slideADList = getADNodeList(SLIDE_CATEGORY_ID, SLIDE_HEIGHT, SLIDE_HEIGHT_B, SLIDE_WIDTH, SLIDE_WIDTH_B);
		// 取右上方广告的内容信息
		List<ADNode> rightTopList = getADNodeList(RIGHT_TOP_CATEGORY_ID, RIGHT_TOP_HEIGHT, RIGHT_TOP_HEIGHT_B, RIGHT_TOP_WIDTH, RIGHT_TOP_WIDTH_B);
		// 取轮播图下的内容信息
		List<ScrollADNode> mScrollList = getScrollADList(Scroll_CATEGORY_ID);
		//品牌旗舰店
		TbContentCategory brandFalgShip = contentCategoryService.getContentCategoryById(BRAND_FLAGSHIP_STORE_CID);
		List<ADNode> brandFalgShipList = getADNodeList(BRAND_FLAGSHIP_STORE_CID, BRAND_FLAGSHIP_STORE_HEIGHT, RIGHT_TOP_HEIGHT_B, BRAND_FLAGSHIP_STORE_WIDTH, BRAND_FLAGSHIP_STORE_WIDTH_B);
		//淘淘快报
		TbContentCategory taotaoFastReport = contentCategoryService.getContentCategoryById(TAOTAO_NEWS_CATEGORY_ID);
		List<TbContent> taotaoList = contentService.getContenListByCid(TAOTAO_NEWS_CATEGORY_ID);
		//家电通讯
		TbContentCategory homeAppliance = contentCategoryService.getContentCategoryById(HOME_APPLIANCE_CATEGORY_ID);
		List<TbContent> homeApplianceList = contentService.getContenListByCid(HOME_APPLIANCE_CATEGORY_ID);
		//家电通讯下方商品广告
		List<ADNode> itemList = getADNodeList(ITEM_CID, ITEM_HEIGHT, 0, ITEM_WIDTH, 0);
		// 获取首页所有的商品类目
		List<TbItemCat> allItemCatList = itemCatService.getAllItemCatList();
		// 把数据传递给页面
		model.addAttribute("slideADList", JsonUtils.objectToJson(slideADList));
		model.addAttribute("rightTopList", JsonUtils.objectToJson(rightTopList));
		model.addAttribute("mScrollList", JsonUtils.objectToJson(mScrollList));
		model.addAttribute("taotaoList", taotaoList);
		model.addAttribute("taotaoFastReport", taotaoFastReport.getName());
		model.addAttribute("homeApplianceList", homeApplianceList);
		model.addAttribute("homeAppliance", homeAppliance.getName());
		model.addAttribute("brandFalgShipList", brandFalgShipList);
		model.addAttribute("brandFalgShip", brandFalgShip.getName());
		model.addAttribute("indexItem", itemList.get(0));
		//暂时先展示14条
		model.addAttribute("allItemCatList", allItemCatList.subList(0, 14));
		return "index";
	}
	
	
	/**
	 * 	获取轮播图、右上方广告、品牌旗舰店、家电通讯下方商品广告列表
	 * @autor liut
	 * @date 2019年2月18日上午11:53:15
	 * @params
	 * @return List<ADNode>
	 */
	private List<ADNode> getADNodeList(Long categoryId,Integer height,Integer heightB,Integer width,Integer widthB) {
		// 根据内容分类id下的内容信息列表
		List<TbContent> contentList = contentService.getContenListByCid(categoryId);
		// 转换成AdNodeList
		List<ADNode> AdNodeList = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			ADNode node = new ADNode();
			node.setAlt(tbContent.getTitle());
			node.setHeight(height);
			node.setHeightB(heightB);
			node.setWidth(width);
			node.setWidthB(widthB);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			// 添加到列表
			AdNodeList.add(node);
		}
		return AdNodeList;
	}

	
	/**
	 * 获取轮播图下方广告列表
	 * 
	 * @autor liut
	 * @date 2019年2月18日上午11:57:04
	 * @params
	 * @return List<ScrollADNode>
	 */
	private List<ScrollADNode> getScrollADList(Long categoryId) {
		// 取轮播图下的内容信息
		List<TbContent> contentList = contentService.getContenListByCid(categoryId);
		// 轮播图下面的广告转换成ScrollADNodeList
		List<ScrollADNode> scrollADList = new ArrayList<>();
		for (int i = 0; i < contentList.size(); i++) {
			TbContent tbContent = contentList.get(i);
			ScrollADNode node = new ScrollADNode();
			node.setAlt(tbContent.getTitle());
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setExt("");
			node.setIndex(i);
			// 添加到列表
			scrollADList.add(node);
		}
		return scrollADList;
	}
	
	
}
