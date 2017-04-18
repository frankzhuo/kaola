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

@Crawler(name = "yinjianfenju", delay = 15)
public class YinJianFenjuSpider extends BaseSeimiCrawler {

	/**
	 * 证监会 行政处罚决定
	 *  
	 */
	private BufferedWriter file = null;

	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://yinjianfenju.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.cbrc.gov.cn/zhuanti/xzcf/get2and3LevelXZCFDocListDividePage//2.html" };
	}

	@Override
	public void start(Response response) {
		String content = response.getContent();
	
		int pageNum = 143; // 测试页数写成固定页数

		callback(response);// 第一页

		for (int i = 2; i <= pageNum; i++) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			push(new Request("http://www.cbrc.gov.cn/zhuanti/xzcf/get2and3LevelXZCFDocListDividePage//2.html?current=" + i, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
		}
		
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

			List<Object> headInfo = doc.sel("//div[@class*='Section']/p/allText()");
 			List<Object> content = doc.sel("//div[@class*='Section']//table[@class]//tr/td[last()]/allText()");
 			
 			String t1 = "";
			for (int i = 0; i < headInfo.size(); i++) {
				t1 += headInfo.get(i).toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ","").replaceAll(" ","");
			}
//			strs[0] = response.getRealUrl()+ "--"+t1;
			strs[0] = t1.trim().equals("")?"-":t1.trim();
			
			if(content.size() < 1 || content.size() >9){
				return;
			}else if(content.size()==9){
				for (int i = 0; i < content.size(); i++) {
					String temp = content.get(i).toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ","");
					strs[i+1] = temp; 
				}
			}else if(content.size()==8){
				for (int i = 0; i < content.size(); i++) {
					String temp = content.get(i).toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ","");
					if(i==0){
						strs[i+1] = temp; 
					}else{
						strs[i+2] = temp; 
					}
				}
			}else{
				System.out.println("================"+response.getRealUrl());
			}
			
			strs[9]= strs[9].replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
			
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
}
