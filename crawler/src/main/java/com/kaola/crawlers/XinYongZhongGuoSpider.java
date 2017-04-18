//package com.kaola.crawlers;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//
//import cn.wanghaomiao.seimi.annotation.Crawler;
//import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
//import cn.wanghaomiao.seimi.http.HttpMethod;
//import cn.wanghaomiao.seimi.struct.Request;
//import cn.wanghaomiao.seimi.struct.Response;
//import cn.wanghaomiao.xpath.model.JXDocument;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//@Crawler(name = "xinyongzhongguo", delay = 15)
//public class XinYongZhongGuoSpider extends BaseSeimiCrawler {
//
//	/**
//	 * 信用中国    
//	 */
//	private BufferedWriter file = null;
//	
//	@Override
//	public String[] startUrls() {
//		try {
//			file = new BufferedWriter(new FileWriter("d://xinyongzhongguo.txt", false));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// 初始化页面
//		return new String[] { "http://www.creditchina.gov.cn/toPunishList#getNo=1&page=1" };
//	}
//
//	@Override
//	public void start(Response response) {
//		try {
//		JXDocument doc = response.document();
//		System.out.println(response.getContent());
//		 
//		int pageNum = 1227;
//		 
//		for(int p=1;p<=pageNum;p++){
//			 Thread.sleep(8*1000);
//			 push(new Request("http://www.creditchina.gov.cn/uploads/creditinfo/"+p, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
//		  }
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
//    
//	
//	/*
//	 * 获取列表页面每行记录的URL地址
//	 */
//	public void callback(Response response) {
//		System.out.println(response.getRealUrl());
//		try {
//			 String content = response.getContent();
//			 JSONArray rows = JSONArray.fromObject(content);
//			 
//			 for (int i = 0; i < rows.size(); i++) {
//				 JSONObject tmp = rows.getJSONObject(i);
//				 String INAME = tmp.getString("INAME");//uuid
//				 String CARDNUMBER = tmp.getString("CARDNUMBER");//机构id
//				 String AREA_NAME = tmp.getString("AREA_NAME");//机构名称
//				 String line =INAME +"\001" + CARDNUMBER+"\001"+AREA_NAME+"\n";
//				 System.out.println(line);
//				 file.write(line);
//			 }
//			file.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
