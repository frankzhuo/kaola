package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "izhikuasklist", delay = 15)
public class IZhiKuAskListSpider extends BaseSeimiCrawler {

	/**
	 * i智库：问答 抓取
	 * http://www.ipzhiku.com/ttqa/
	 * list
	 * detail   
	 */
	private BufferedWriter file = null;
	private BufferedWriter file_detail = null;
	
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://izhikuasklist.txt", false));
			file_detail = new BufferedWriter(new FileWriter("d://izhikuaskdetail.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
		int pageNum = 24;//24;
		 
		for(int p=1;p<=pageNum;p++){
			 push(new Request("http://www.ipzhiku.com/ttqa/?/sort_type-new__day-0__is_recommend-0__page-"+p, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
			 Thread.sleep(100*1000);
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
		System.out.println(response.getContent());
		try {
			 List<Object> list1 = doc.sel("//div[@class='mod-body']/div[@class='aw-common-list']/div/div[@class='aw-question-content']");
			 
			 for (int i = 0; i <list1.size() ; i++) { //
				 String href ="http://www.ipzhiku.com"+list1.get(i);
				 Element div_e = (Element)list1.get(i) ;
				 String title = div_e.getElementsByTag("a").get(0).text();
				 String href_detail = div_e.getElementsByTag("a").get(0).attr("href");
				 
				 String pingjia = div_e.getElementsByTag("p").get(0).text();
				 
				 push(new Request(href_detail, "detail").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
				 Thread.sleep(10*1000);
				 
				 file.write(href_detail +"\001"+title+"\001"+pingjia+"\n");
				 System.out.println(href_detail +"\001"+title+"\001"+pingjia);
				 
			 }
			 file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取详情的URL地址
	 */
	public void detail(Response response) {
		JXDocument doc = response.document();
		System.out.println(response.getContent());
		String url = response.getRealUrl();
		try {
			 List<Object> list1 = doc.sel("//div[@class='container']/div[@class='row']//div[@class='aw-mod aw-question-detail aw-item']/div");
			 List<Object> list2 = doc.sel("//div[@class='container']/div[@class='row']//div[@class='aw-mod aw-question-comment']/div/div/div/div[@class='markitup-box']");
			 
			 String title = "";
			 String content = "";
			 for (int i = 0; i <list1.size() ; i++) { 
				 if(i==0){
					 title = ((Element)list1.get(i)).text().trim().replace("\n", "").replace(" ","");
				 }
				 else if(i == 1 ){
					 content = ((Element)list1.get(i)).text().trim().replace("\n", "").replace(" ","");
				 }
			 }
			 
			 if(list2.size() > 0){
				 for (int i = 0; i <list2.size() ; i++) { 
					 String huifu = ((Element)list2.get(i)).text().trim().replace("\n", "").replace(" ","");
					 String tmp = url +"\001" + title +"\001"+content+"\001"+(huifu.trim().length()>0?huifu.trim():"-")+"\n";
					 System.out.println(tmp);
					 file_detail.write(tmp);
				 } 
			 }else{
				 String tmp = url +"\001" + title +"\001"+content+"\001"+"-"+"\n";
				 System.out.println(tmp);
				 file_detail.write(tmp);
			 }
			
			 file_detail.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
