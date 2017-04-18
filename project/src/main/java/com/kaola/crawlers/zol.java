package com.kaola.crawlers;

import java.util.List;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultLocalQueue;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "zol",delay=10)
//@Crawler(name = "xypj",delay=10)

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
    		
    	}
    	//return null;
        return new String[]{"http://www.baidu.com"};
    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            //for (long i=1;i<3;i++){
            for (long i=1;i<108;i++){
            	System.out.println(i);
                push(new Request("http://detail.zol.com.cn/cell_phone_index/subcate57_0_list_1_0_1_2_0_"+i+".html","getUrls"));
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
        //System.out.println(doc.);
        try {
        
            //List<Object> urls = doc.sel("//ul[@class='ul_news_long']//a/@href");
            
        
            List<Object> urls = doc.sel("//ul[@id='J_PicMode']/li/a[@class='pic']/@href"); 
            //List<Object> urls = doc.sel("//ul[@id='J_PicMode']/text()"); 
            System.out.println(urls.size());
            for(Object  url:urls){    
                 System.out.println(url.toString());
                  push(new Request("http://detail.zol.com.cn/"+url.toString(),"getData"));
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
 // @Scheduled(cron = "0/5 * * * * ?")
//  public void callByCron(){
//      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
//   	try{
//		  filew=new FileWriter("d:\\shixin.txt");
//		  filec=new FileWriter("d:\\shixinc.txt");
//		  //DefaultLocalQueue qq=new DefaultLocalQueue();
//		  //this.setQueue(qq);
//	      for (long i=0;i<2000;i=i+50){
//          	System.out.println(i);
//          	 //push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//              push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=10&ie=utf-8&oe=utf-8&format=json&t=1455870490009&cb=jQuery1102049660134187316657_1455863650458&_=1455863650463","getData").setSkipDuplicateFilter(true));
//          }
//		
//	}catch(Exception o){
//		
//	}
//      // 可定时发送一个Request
//      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//  }
    
}