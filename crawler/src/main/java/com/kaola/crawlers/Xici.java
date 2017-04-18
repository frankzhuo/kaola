package com.kaola.crawlers;

import java.util.List;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultLocalQueue;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "xici",delay=10)
public class Xici  extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
	private int page=0;
	private int num=0;
    @Override
    public String[] startUrls() {
    	try{
    		filew=new FileWriter("/data/xici.txt");
    		
    	}catch(Exception o){
    		o.printStackTrace();
    	}
    	//return null;
        return new String[]{"http://www.xicidaili.com/nn/"};
    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            for (long i=1;i<393;i++){
            	TimeUnit.SECONDS.sleep(20);
                push(new Request("http://www.xicidaili.com/nn/"+i,"getUrls"));
            }
        } catch (Exception e) {
        	System.out.println("异常了");
            e.printStackTrace();
        }
    }
    public void getUrls(Response response){
    	String  str=response.getContent();
    	
    	//response.getHttpResponse().getFirstHeader("")
    	//System.out.println("***"+response.getRealUrl());
    	//System.out.println("***"+response.getContent());
        JXDocument doc = response.document();
        //System.out.println(doc.);
        try {
        
            //List<Object> urls = doc.sel("//ul[@class='ul_news_long']//a/@href");               
            List<Object> urls = doc.sel("//table[@id='ip_list']/tbody/tr/td/text()"); 
            //List<Object> urls = doc.sel("//ul[@id='J_PicMode']/text()"); 
            System.out.println(urls.size());
            int  num=0;
            String  ip="";
            String  port="";
            for(Object  url:urls){
            	num++;
            	if(num%10==2){
            		System.out.println(url.toString());
            		ip=url.toString();
            	}
            	if(num%10==3){
            		port=url.toString();
            		System.out.println("##"+url.toString());
            	}
            	if(num%10==5){
            		System.out.println(ip+" "+port+"\n");
            		filew.write(ip+" "+port+"\n");
            		filew.flush();
            	}
                  //push(new Request("http://detail.zol.com.cn/"+url.toString(),"getData"));
            	//System.out.println(response.getRealUrl());
  	         }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
  @Scheduled(cron = "1 1 1 * * ?")
  public void callByCron(){
      //logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
      try {
 	     
          for (long i=1;i<393;i++){
        	  TimeUnit.SECONDS.sleep(20);
              push(new Request("http://www.xicidaili.com/nn/"+i,"getUrls"));
          }
      } catch (Exception e) {
      	System.out.println("异常了");
          e.printStackTrace();
      }
      // 可定时发送一个Request
      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
  }
    
}