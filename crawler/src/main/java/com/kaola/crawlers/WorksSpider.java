package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

//@Crawler(name="works",delay=15)
public class WorksSpider extends BaseSeimiCrawler {

	private BufferedWriter  file=null;
	private BufferedWriter  file_works_no=null;
	@Override
	public String[] startUrls() {
		try {
			file=new BufferedWriter(new FileWriter("/data/crawler/works/works2.txt",false));
			file_works_no=new BufferedWriter(new FileWriter("/data/crawler/works/works_no.txt",false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//初始化页面
		return new String[]{"http://www.ccopyright.com.cn/cpcc/ZRegisterAction.do?method=list&no=fck&sortLabel=registerDate&count=10&curPage=0"};
	}

	@Override
	public void start(Response response) {
		String content =response.getContent();
		String reg = "\\[总数：(\\d+) 条\\]";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(content);
		int pageNum = 293;//信用评价记录的页数
		int count = 500;
		while(mat.find()){
			pageNum = (int) Math.ceil(Double.parseDouble(mat.group(1))/500);
		}
		for (int i = 0; i < pageNum; i++) {
			Map<String,String> map =new HashMap<String,String>();
			map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
			map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			map.put("Accept-Encoding","gzip, deflate, sdch");
			map.put("Accept-Language","zh-CN,zh;q=0.8");
			map.put("Cache-Control","max-age=0");
			map.put("Connection","keep-alive");
			map.put("Cookie","td_cookie=2145551314; JSESSIONID=08A0E15D53B2CE85B4F6C15A09C81F0C; Hm_lvt_06aa544292f23ed79e180706dcd464f1=1467683304; Hm_lpvt_06aa544292f23ed79e180706dcd464f1=1467683337; CNZZDATA2306079=cnzz_eid%3D388702980-1467678527-http%253A%252F%252Fwww.ccopyright.com.cn%252F%26ntime%3D1467678527; Hm_lvt_9c4f37212151487ffd09c83bccfd2ade=1467622190,1467683385; Hm_lpvt_9c4f37212151487ffd09c83bccfd2ade=1467702425");
			map.put("Host","www.ccopyright.com.cn");
			map.put("Upgrade-Insecure-Requests","1");
			push(new Request("http://www.ccopyright.com.cn/cpcc/ZRegisterAction.do?method=list&no=fck&sortLabel=registerDate&count="+count+"&curPage="+(i+1),"callback")
					//.setSkipDuplicateFilter(true)
					//.setHeader(map)
					.setHttpMethod(HttpMethod.GET)
					);
		}
		System.out.println("start run queue size " + pageNum);
	}
	
	 public void callback(Response response){
	        JXDocument doc = response.document();
	        try {
	         List<Object> list =doc.sel("//table[@bordercolor='#CCCCCC']//tr/child::node()");
	         for (Object tmp : list) {
		        	 Element  el = (Element)tmp;
		        	 String tagName = el.tagName();
		        	 if(!"tr".equals(tagName)){
		        		 continue;
		        	 }
		        	List<Element> children =  el.children();
		        	if("th".equals(children.get(0).tagName())){
		        		continue;
		        	}
		        	
		        	String[] strs = new String[]{"","","","","","","-"};
		        	for (int i = 0; i < children.size(); i++) {
		        		String str =children.get(i).text();
		        		if(str == null || str.trim().length() ==0){
		        			continue;
		        		}
		        		strs[i] = str;
					}
		        	
		        	String tt =ArrToStr(strs);
		        	System.out.println(tt);
					//if(tt.split("\001").length ==7){
						file.write(tt);
					//}else{
					//	file_works_no.write("fields num is " +tt.split("\001").length+",url is "+response.getUrl() +" content is"+tt+"\n");
					//}
		    	}
	         file.flush();
	         file_works_no.flush();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
		 private String ArrToStr(String[] arr){
				 String str = "";
				 for (int j = 0; j < arr.length; j++) {
					 str =str +arr[j];
						if(j == arr.length-1){
							str = str + "\n";
						}else{
							str = str + "\001";
						}
				}
				 return str;
			 }
}
