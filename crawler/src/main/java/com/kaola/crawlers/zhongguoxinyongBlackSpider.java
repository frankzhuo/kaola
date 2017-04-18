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

@Crawler(name = "zhongguoxinyongBlack", delay = 0,useCookie = true)
public class zhongguoxinyongBlackSpider extends BaseSeimiCrawler {

	/**
	 * 企业受惩罚黑名单（信用中国网）
	 */
	private BufferedWriter file = null;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://zhongguoxinyongBlack.txt", false));
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
		 
		int pageNum = 1;
		 
		for(int p=1;p<=pageNum;p++){
			Thread.sleep(2*1000);
			push(new Request("http://www.zhongguoxinyongheimingdan.com/s?p="+p, "callback").setSkipDuplicateFilter(true));
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
			 System.out.println(content);
			 
			 List<Object> list1 = doc.sel("//div[@class='result-list']/div[@class='list-item']/");
			 
			
			 
			 for (int i = 0; i < list1.size(); i++) {
				 String str ="";
				 Element e = (Element) list1.get(i);
				 List<Element> e_span = e.getElementsByTag("span");
				 for (int j = 0; j < e_span.size(); j++) {
					String v = e_span.get(j).text();
					if(e_span.size() -1 == j){
						str =  str +e_span.get(j).children().get(0).attr("href");
						break;
					}
					str = str + v +"\001";
				}
				 file.write(str);
			 }
			file.flush();
		} catch (Exception e) {
			//e.printStackTrace();
			
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
