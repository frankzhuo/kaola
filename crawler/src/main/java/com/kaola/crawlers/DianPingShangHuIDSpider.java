package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "dianpingshanghuid", delay = 10)
public class DianPingShangHuIDSpider extends BaseSeimiCrawler {

	/**
	 * 大宗点评 根据shopid 获取点评信息    
	 */
	private BufferedWriter file = null;
	private BufferedReader fileReader = null;
    private static boolean flag= true;
	
//    @Override
//    public String proxy() {
//        String[] proxies = new String[]{
//        		"http://119.29.54.15:80",
//        		"http://119.29.191.76:80",
//        		"http://101.201.143.54:80"};
//        return proxies[RandomUtils.nextInt()%proxies.length];
//    }
    
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("/data/crawler/dianping/dianpingshanghuid.txt", true));
			fileReader = new BufferedReader(new FileReader("/data/crawler/dianping/dianpingshanghuvalid.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.dianping.com/" };
	}

	@Override
	public void start(Response response) {
		String line;
		try {
			while((line = fileReader.readLine()) !=null){
				flag = false; //未完成
				
				try{
					push(new Request(line, "callPageNum").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
				}
				catch(Exception ex){
					flag= true;
					ex.printStackTrace();
				}
				
				int count=0;
				while(!flag){//未完成则继续等
					System.out.println("===sleep===="+count+"===="+new Date());
					Thread.sleep(10*1000);
					if(count>5){
						break;
					}
					count++;
				}
				System.out.println("------yunxing zhi ---------"+line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callPageNum(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
		try {
			 List<Object> list = doc.sel("//div[@class='shop-wrap']/div[@class='page']/a[@class='PageLink']/text()");
			 
			 int pageNum  = 0;
			 if(list.size()>0){
				 pageNum = Integer.parseInt((String) list.get(list.size()-1)); 
             }
			 
			 callback(response);
			 
			 for (int i = 2; i <= pageNum; i++) {
				 Thread.sleep(5*1000);
				 push(new Request(url+"p"+i, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
			 }
			 
			 flag =true;/////上一个商户id的评论已完成
		} catch (Exception e) {
			flag =true;/////上一个商户id的评论已完成
			e.printStackTrace();
		}finally {
			flag =true;
		}
	}
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		try {
			 List<Object> list = doc.sel("//div[@class='shop-wrap']/div[@class='content']/div[@id='shop-all-list']/ul/li/div[@class='pic']/a/@href");
			 
			for (Object tmp : list) {
				String shopid= tmp.toString().substring(tmp.toString().lastIndexOf("/")+1);
		        System.out.println(shopid);
		        file.write(shopid+"\n");
			}
			file.flush();
		} catch (Exception e) {
			flag =true;
			e.printStackTrace();
		}
	}
	
	 /**
     * UA库可以自行查找整理，这里仅为样例
     * @return
     */
    @Override
    public String getUserAgent(){
    	return "Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0";
    }
}
