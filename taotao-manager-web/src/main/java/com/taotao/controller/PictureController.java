package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;

/**
 * 上传图片Controller
 * @author liut
 * @date 下午4:45:14
 */
@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	/**
	 * 上传图片
	 * @autor liut
	 * @date  2019年2月27日下午3:41:18
	 * @params
	 * @return String
	 */
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile) {
		try {
			//接收上传的文件
			//取扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			//上传图片到服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
			url = IMAGE_SERVER_URL + url;
			//响应上传图片的url
			Map result = new HashMap<>(16);
			result.put("error",0);
			result.put("url",url);
			/*
			 * ResponseBody相当于repsonse.write()，只能写字符串，谷歌浏览器默认转换成了json格式字符串
			 * 火狐浏览器不兼容，需要用JsonUtils工具类即jackSon类转换
			 */
			return JsonUtils.objectToJson(result);
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>(16);
			result.put("error",1);
			result.put("message","图片上传失败");
			return JsonUtils.objectToJson(result);
		}
		
		
	}
}
