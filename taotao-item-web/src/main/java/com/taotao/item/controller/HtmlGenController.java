package com.taotao.item.controller;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 测试Spring整合Freemarker生成静态页面
 * @author liut
 * @date 2019年2月27日下午2:46:55
 */
@Controller
public class HtmlGenController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/genhtml")
	@ResponseBody
	public String genhtml() throws Exception {
		int i=1/0;
		Configuration configuration = freeMarkerConfigurer.createConfiguration();
		Template template = configuration.getTemplate("hello.ftl");
		Map dataModel = new HashMap(16);
		dataModel.put("hello", "Hello World");
		Writer out = new FileWriter("G:\\BaiduNetdiskDownload\\Hello2.html");
		template.process(dataModel, out);
		out.close();
		return "ok";
	}
	
}
