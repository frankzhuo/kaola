package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "shuishouweifa", delay = 15)
public class ShuiShouWeiFaSpider extends BaseSeimiCrawler {

	/**
	 *  
	 */
	private BufferedWriter file = null;
    private static boolean flag= true;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://shuishouweifa.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
//		System.out.println(response.getContent());
		
		
		try {
			for(int i = 1; i<52;i++){
				flag = false; //未完成
				String href = "http://hd.chinatax.gov.cn/xxk/action/ListXxk.do";
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("categeryid", "24");
				map.put("querystring24", "articlefield02");
				map.put("querystring25", "articlefield02");
				map.put("queryvalue", "");
				map.put("cPage", i+"");

				push(new Request(href, "callback").setSkipDuplicateFilter(true).setParams(map).setHttpMethod(HttpMethod.POST));
				
				while(!flag){//未完成则继续等
					Thread.sleep(10*1000);
				}
				System.out.println("------yunxing zhi ---------"+i);
			}
			System.out.println("------运行完毕！！！ ---------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
  		System.out.println(url);
		try {
			 List<Object> list = doc.sel("//table//tr/td//td[@bgcolor='#F0F0F0']/a/@href");
			 for (int i = 0; i < list.size(); i++) {
				 String href = "http://hd.chinatax.gov.cn/xxk/action/"+list.get(i);
				 Thread.sleep(5*1000);
				 push(new Request(href, "readDetail").setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.POST));
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = true; 
		}
		finally{
			flag = true; 
		}
	}
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void readDetail(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
  		System.out.println(url);
		try {
			 List<Object> list = doc.sel("//table[@bgcolor='#CBDDEE']/tbody/tr");
			 
			 String tmp = "";
			 for (int i = 0; i < list.size(); i++) {
				 Element el = (Element) list.get(i);
		         List<Element> child_td = el.children();
		         
		        // for(int j=0 ; j<child_td.size();j++){
		        	 String td1 = child_td.get(0).text();
		        	 String td2 = child_td.get(1).text();
		        //}
		       if(i == list.size() -1){
		    	   tmp = tmp + td2+"\n";
		    	   break;
		       }
		      tmp = tmp + td2+"\001";
			 }
			 
			 System.out.println(tmp);
			 file.write(tmp);
			 file.flush();
			 
		} catch (Exception e) {
			e.printStackTrace();
			//flag = true; 
		}
		finally{
			//flag = true; 
		}
	}
	
}
