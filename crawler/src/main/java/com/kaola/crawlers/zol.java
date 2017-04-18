package com.kaola.crawlers;

import java.util.List;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
import cn.wanghaomiao.seimi.def.DefaultRedisQueue;

//@Crawler(name = "zol",delay=10,queue = DefaultRedisQueue.class)
//@Crawler(name = "xypj",delay=10)
@Crawler(name = "zol",delay=10)
public class zol  extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
	private int page=0;
	private int num=0;
    @Override
    public String[] startUrls() {
    	try{
    		//filew=new FileWriter("d:\\zol.txt");
    		filew=new FileWriter("/data/crawler/zol.txt");
    		
    	}catch(Exception o){
    		o.printStackTrace();
    	}
    	//return null;
        return new String[]{"http://www.baidu.com"};
    }
//    @Override
//    public String proxy() {
//        String[] proxies = new String[]{"http://10.5.28.14:5656"};
//        return proxies[RandomUtils.nextInt()%proxies.length];
//    }
    @Override
    public String getUserAgent(){
        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
        };
        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        //JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            //for (long i=1;i<3;i++){
            for (long i=1;i<108;i++){
            	System.out.println(i);
                push(new Request("http://detail.zol.com.cn/cell_phone_index/subcate57_0_list_1_0_1_2_0_"+i+".html","getUrls").setSkipDuplicateFilter(true));
                Thread.sleep(1000*20);
            }
        } catch (Exception e) {
        	System.out.println("异常了");
            e.printStackTrace();
        }
    }
    public void getUrls(Response response){
    	String  str=response.getContent();
    	
    	//response.getHttpResponse().getFirstHeader("")
    	System.out.println("***"+response.getRealUrl());
    	//System.out.println("***"+response.getContent());
        JXDocument doc = response.document();
        //System.out.println(str);
        try {
        
            //List<Object> urls = doc.sel("//ul[@class='ul_news_long']//a/@href");
            
        	                                   
            List<Object> urls = doc.sel("//ul[@id='J_PicMode']/li/a[@class='pic']/@href"); 
            //List<Object> urls = doc.sel("//ul[@id='J_PicMode']/text()"); 
            System.out.println(urls.size());
            for(Object  url:urls){    
                  System.out.println(url.toString());
                  push(new Request("http://detail.zol.com.cn/"+url.toString(),"getData").setSkipDuplicateFilter(true));
            	//System.out.println(response.getRealUrl());
  	         }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getData(Response response){
    	
    	System.out.println(response.getRealUrl());
    	String url=response.getRealUrl();
    	if(response.getHttpResponse().getStatusLine().toString().indexOf("500")>0)
    		return;
    	String  str=response.getContent();
    	System.out.println("getdata");
    	//System.out.println(str);
    	JXDocument doc = response.document();	
    	try{
	    	List<Object> name = doc.sel("//div[@class='page-title clearfix']/h1/text()"); 
	      	List<Object> nameAs = doc.sel("//div[@class='page-title clearfix']/h2/text()"); 
	      	List<Object> price = doc.sel("//span[@id='J_PriceTrend']/b[@class='price-type price-retain']/text()"); 
	      	if(price.size()<1){
	      		price = doc.sel("//span[@id='J_PriceTrend']/b[@class='price-type price-']/text()"); 

                System.out.println("n"+price.size());
	      	}
	      	if(price.size()<1){
	      		price = doc.sel("//div[@class='price price-sp-num']/span/b[@class='price-type price-']/text()"); 
                System.out.println("yu"+price.size());
	      	}
	      	if(price.size()<1){
	      		price = doc.sel("//div[@class='price price-np-num']/span/b[@class='price-type price-']/text()"); 
                System.out.println("yue"+price.size());
	      	}
	      	if(price.size()<1){
	      		price = doc.sel("//div[@class='price price-upc']/span/b[@class='price-type price-']/text()"); 
                System.out.println("yue"+price.size());
	      	}
	      	if(price.size()<1){
	      		price = doc.sel("//div[@class='price price-cp']/span/b[@class='price-type price-']/text()"); 
                System.out.println("yuer"+price.size());
	      	}
	      	
	      	if(price.size()<1){
	      		price = doc.sel("//div[@class='price price-normal']/span/b[@class='price-type price-']/text()"); 
                System.out.println("yuers"+price.size());
	      	}
	      	if(price.size()<1){
	      		price = doc.sel("//b[@class='price-type price-']/text()"); 
                System.out.println("yuers"+price.size());
	      	}
	      	//List<Object> config = doc.sel("//ul[@class='product-param-item pi-57 clearfix']/text()"); 
	      	//List<Object> config = doc.sel("//ul[@class='product-param-item pi-57 clearfix']/span[@class='param-value']/text()"); 
	     	//名称  别名  价格   主屏尺寸 主屏分辨率 后置摄像头 前置摄像头 电池容量 电池类型 核心数 内存
            System.out.println(name.get(0));
            String strAS="";
            if(nameAs.size()>0)
        	   strAS=nameAs.get(0).toString();
            strAS=strAS.replaceAll("别名：","");
            String priceStr="";
            System.out.println(strAS);
            if(price.size()>0)
        	   priceStr=price.get(0).toString();
           
            System.out.println(priceStr);
            //System.out.println(config.size());
            if(filew==null){
            	filew=new FileWriter("/data/crawler/zol.txt");
            	System.out.println("file  null");
            }
	      	filew.write(name.get(0)+"\001"+strAS+"\001"+priceStr+"\001"+url+"\n");
            
	      	//filew.write(name.get(0)+"\001"+nameAs.get(0)+"\001"+price.get(0)+"\001"+config.get(0)+"\001"+config.get(1)+"\001"+config.get(2)+"\001"
    		//	    +config.get(3)+"\001"+config.get(4)+"\001"+config.get(5)+"\001"+config.get(6)+"\001"+config.get(7)+"\n\r");
    	    filew.flush();

    	}catch(Exception o){
    		o.printStackTrace();
    	}
      	//<span id="J_PriceTrend">
    	//<div class="page-title clearfix">

    	//System.out.println(jsonString);
    }
  @Scheduled(cron = "5 25 14 10 * ?")
  public void callByCron(){
      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
      try {
    	  if(filew != null )
    		  filew.close(); 
    	  filew=new FileWriter("/data/crawler/zol.txt");
          //for (long i=1;i<3;i++){
          for (long i=1;i<108;i++){
          	  System.out.println(i);
              push(new Request("http://detail.zol.com.cn/cell_phone_index/subcate57_0_list_1_0_1_2_0_"+i+".html","getUrls").setSkipDuplicateFilter(true));
              Thread.sleep(1000*20);
          }
      } catch (Exception e) {
      	System.out.println("异常了");
          e.printStackTrace();
      }

      // 可定时发送一个Request
      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
  }
    
}