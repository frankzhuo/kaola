package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "huangyewangbeijing", delay = 0,useCookie = true)
public class HuangyewangBeijingSpider extends BaseSeimiCrawler {

	/**
	 * http://www.88152.com/beijing/list2.html
	 * 华企黄页网 
	 * 爬取北京企业注册信息  
	 */
	private BufferedWriter file = null;
	private static boolean flag= true;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://huangyewangbeijing.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
		JXDocument doc = response.document();
		System.out.println(response.getContent());
		 
		int pageNum = 8853;
		 
		for(int p=1;p<=pageNum;p++){
			flag = false; //未完成
			push(new Request("http://www.88152.com/beijing/list"+p+".html", "callback").setSkipDuplicateFilter(true));
			
			System.out.println("http://www.88152.com/beijing/list"+p+".html");
			
			while(!flag){//未完成则继续等
				Thread.sleep(2*1000);
			}
			
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		System.out.println(response.getRealUrl());
		JXDocument doc = response.document();
		
		try {
			 String content = response.getContent();
//			 System.out.println(content);
			 
			 List<Object> list1 = doc.sel("//div[@id='listcolumn']/ul[@class='comlist']/li[@style!='padding:0;']");
			 
			 for (int i = 0; i < list1.size(); i++) {
				 String str ="";
				 Element e = (Element) list1.get(i);
				 List<Element> e_span = e.getElementsByTag("div");
				 for (int j = 0; j < e_span.size(); j++) {
					String v = e_span.get(j).text();
					if(e_span.size()-1== j){
						String href  =  e_span.get(0).children().get(0).attr("href");
						str = str + v +"\001"+href +"\n";
					}else{
						str = str + v +"\001";
					}
					
				}
				 file.write(str);
			 }
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			 flag =true;
		}
	}
	
    /**
     * UA库可以自行查找整理，这里仅为样例
     * @return
     */
    @Override
    public String getUserAgent(){
        return "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";
    }
    
}
