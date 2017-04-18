package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "zhongxinyong", delay = 15)
public class ZhongxinyongSpider extends BaseSeimiCrawler {

	/**
	 * 守合同，重信用
	 * http://111.206.129.35:8080/appraise/pages/gscx.jsp?webTopicId=05&curPage=444&organizeName=&appraiseAnnual=&regionId=   
	 */
	private BufferedWriter file = null;
	Map<String,Object> map_goodsid = new HashMap<String,Object>();
	
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://data/zhongxinyong.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
		int pageNum = 449;
		 
		for(int p=1;p<=pageNum;p++){
			 push(new Request("http://111.206.129.35:8080/appraise/pages/gscx.jsp?webTopicId=05&curPage="+p+"&organizeName=&appraiseAnnual=&regionId=", "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
		     Thread.sleep(20*1000);  
		     System.out.println("----------http://111.206.129.35:8080/appraise/pages/gscx.jsp?webTopicId=05&curPage="+p+"------------------");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		//System.out.println(response.getContent());
		try {
			 List<Object> list1 = doc.sel("//div[@class='grid_12']/ul/table/tbody/tr");
			 for(int i=1;i<list1.size();i++){
				 Element el = (Element)list1.get(i);
				 Elements tds = el.children();
				 //http://111.206.129.35:8080/appraise/pages/gsmx.jsp?projectId=15F483DB4CC944E589BBDD778775968A
				 String  href =  "http://111.206.129.35:8080"+ tds.get(0).getElementsByTag("a").get(0).attr("href");
				 String tmp = tds.get(0).text() +"\001"+tds.get(1).text()+"\001"+tds.get(2).text()+"\001"+tds.get(3).text()+"\001"+tds.get(4).text()+"\001"+tds.get(5).text()+"\001"+href+"\n";
			     file.write(tmp);
			 }
			 file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
