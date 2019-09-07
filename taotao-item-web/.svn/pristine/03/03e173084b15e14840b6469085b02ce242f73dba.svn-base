package com.taotao.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 测试Freemarker的使用
 * @author liut
 * @date 2019年2月26日下午11:07:18
 */
public class TestFreemarker {
	
	@Test
	public void getFile() {
		//1,创建一个模板文件
		//2,创建一个Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//3，设置模板所在路径
		try {
			configuration.setDirectoryForTemplateLoading(new File("D:\\eclipse_workpace\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
		//4,设置模板的字符集，一般utf-8
			configuration.setDefaultEncoding("utf-8");
		//5，使用Configuration对象加载一个模板文件，需要指定模板文件的文件名
			Template template = configuration.getTemplate("student.ftl");
		//6，创建一个数据集，可以是pojo也可以是map，推荐使用map
			Map dataModel = new HashMap(16);
			dataModel.put("hello", "Hello World");
			Student student = new Student(1, "张三", 18, "sh");
			dataModel.put("student", student);
			List<Student> stuList = new ArrayList<>();
			stuList.add(student);
			stuList.add(new Student(2, "hh", 19, "sh"));
			stuList.add(new Student(3, "ee", 20, "sh"));
			stuList.add(new Student(4, "pp", 18, "sh"));
			stuList.add(new Student(5, "aa", 12, "sh"));
			dataModel.put("stuList", stuList);
			dataModel.put("date", new Date());
			dataModel.put("val",null);
		//7，创建一个Writer对象，指定输出文件的路径及文件名
			Writer out = new FileWriter("G:\\BaiduNetdiskDownload\\student6.html");
		//8，使用模板那对象的process方法输出文件
			template.process(dataModel, out);
		//9，关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
