package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;
import sun.security.util.Length;

@Crawler(name = "yinjianhuijg", delay = 15)
public class YinJianHuiJGSpider extends BaseSeimiCrawler {

	/**
	 * 证监会 行政处罚决定
	 *  
	 */
	private BufferedWriter file = null;

	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://yinjianhuijg.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.cbrc.gov.cn/chinese/home/docViewPage/110002.html" };
	}

	@Override
	public void start(Response response) {
		String content = response.getContent();
	
		int pageNum = 1; // 测试页数写成固定页数

		callback(response);// 第一页

		System.out.println("start run queue size " + pageNum);
	}
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		try {
			List<Object> list = doc.sel("//table[@id='testUI']//tr/td//a/@href");

			int i = 0;
			for (Object tmp : list) {
			 if(i == 0 ){
				 i++;
				 continue;
			 }
			 if(tmp.toString().startsWith("/zhuanti") || tmp.toString().startsWith("#")){
				 continue;
			 }
			 System.out.println(tmp);
			 
			 String hrefDetail = "http://www.cbrc.gov.cn" + tmp.toString(); 
			 System.out.println(hrefDetail);
			 Thread.sleep(3*1000);
			 push(new Request(hrefDetail, "getData").setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.GET));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取详细页面
	 */
	public void getData(Response response) {
		JXDocument doc = response.document();
//		System.out.println(response.getContent());
		
		response.getRealUrl();
		try {
			String[] strs = new String[] {"", "", "", "", "", "", "", "","",""};

			List<Object> headInfo = doc.sel("//div[@class*='Section']/p[@class]/allText()");
 			List<Object> content = doc.sel("//div[@class*='Section']/table[@class='MsoNormalTable']//tr/td[last()]/allText()");
 			
 			String t1 = "";
			for (int i = 1; i < headInfo.size(); i++) {
				t1 += headInfo.get(i).toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ","").replaceAll(" ","");
			}
			strs[0] = response.getRealUrl()+t1;
			
			for (int i = 0; i < content.size(); i++) {
				String temp = content.get(i).toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ","");
				strs[i+1] = temp; 
			}
			
			String tt = ArrToStr(strs);
			System.out.println(tt);
			if (tt.split("\001").length == 10) {
				file.write(tt);
			}
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 把字符数组转化成字符串，按“\001”分隔符分组
	  * @param arr
	  * @return
	  */
	private String ArrToStr(String[] arr) {
		String str = "";
		for (int j = 0; j < arr.length; j++) {
			str = str + arr[j];
			if (j == arr.length - 1) {
				str = str + "\n";
			} else {
				str = str + "\001";
			}
		}
		return str;
	}
	private String replaceStr(String str){
		
		str = str.replaceAll("索引号:","");
		str = str.replaceAll("分类:","");
		str = str.replaceAll("发布机构:","");
		str = str.replaceAll("发文日期:","");
		str = str.replaceAll("文　　号:","");
		str = str.replaceAll("主题词:","");
		
		return str;
		
	}
}
