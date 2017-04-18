package com.kaola.crawlers;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "caseflowchendu")

public class CaseFlowChendu extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
	private int page=0;
	private int num=0;
    @Override
    public String[] startUrls() {
    	try{
    		filew=new FileWriter("/data/crawler/caseflowchendu.txt");
    		
    	}catch(Exception o){
    		
    	}
        return new String[]{"http://sfgk.cdfy12368.gov.cn:153"};
    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            for (long i=1;i<11257;i++){
            	System.out.println("ok"+i);
            	HashMap<String,String> map=new HashMap<String,String>();
            	map.put("__EVENTTARGET", "btnPage6");
            	map.put("hdPageIndex", i+"");
            	map.put("hf_ajlb", "9715918889E6905BB9AD59B88AA07AC2");
            	map.put("hf_type", "73060EC7CC40EDFC");
            	map.put("__EVENTARGUMENT", "");
            	map.put("__LASTFOCUS", "");
            	map.put("__VIEWSTATE", "/wEPDwUJNjMyNTQ3MDYzD2QWAmYPZBYCAgQPZBYCAgEPZBYgAgEPDxYCHgRUZXh0BQYyNTg4OTVkZAIDDw8WAh8ABQUxMTI1N2RkAgUPDxYGHwAFAjw8HgtDb21tYW5kTmFtZQUBMR4HVmlzaWJsZWdkZAIHDw8WBh8ABQExHwEFATEfAmhkZAIJDw8WBB8ABQMuLi4fAmhkZAILDw8WBh8ABQItMh8BBQItMh8CaGRkAg0PDxYGHwAFAi0xHwEFAi0xHwJoZGQCDw8PFgYfAAUBMB8BBQEwHwJoZGQCEQ8PFgYfAAUBMR8BBQExHwJnZGQCEw8PFgYfAAUBMh8BBQEyHgdFbmFibGVkaGRkAhUPDxYEHwAFATMfAQUBM2RkAhcPDxYEHwAFATQfAQUBNGRkAhkPDxYEHwAFATUfAQUBNWRkAhsPDxYEHwAFATYfAQUBNmRkAh8PDxYEHwAFBTExMjU3HwEFBTExMjU3ZGQCIQ8PFgQfAAUCPj4fAQUBM2RkZO+/7js3cevwHCOTrNRFjccyFyJf");
            	map.put("__EVENTARGUMENT", "");
            	map.put("__EVENTVALIDATION", "/wEWDgLp8+bOBgLYm/LeDwL99O8KAove7eAHArPH37cLAtazu7sHApy3qLMMApy3gP0KApy3lNgDApy32MYJApy37KECArHht48JArXh844KAs7M/dkLBCa56DlmS/dp/XXf5+W/U56TWlY=");
            	map.put("hf_fymc", "");
            	map.put("tbGoPage", "");
                push(new Request("http://sfgk.cdfy12368.gov.cn:153/publiclist.aspx?fymc=&ajlb=9715918889E6905BB9AD59B88AA07AC2&type=73060EC7CC40EDFC","getUrls").setHttpMethod(HttpMethod.POST).setParams(map).setSkipDuplicateFilter(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getUrls(Response response){
    	String  str=response.getContent();
    	System.out.println("***"+str);
        JXDocument doc = response.document();
        try {
        	//doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()");
            //List<Object> urls = doc.sel("//li[@class='datas']/a/@href");
            //List<Object> urls = doc.sel("//a/@href");
            List<Object> urls = doc.sel("//li[@class='datas']//a/@href");
            logger.info("{}", urls.size());
            for (Object s:urls){
            	System.out.println(s.toString());
                push(new Request("http://sfgk.cdfy12368.gov.cn:153/"+s.toString().trim(),"getData"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void getData(Response response){	
        	//page++;
        	String url=response.getRealUrl();
        	
        	//System.out.println(response.getRealUrl());
        	//System.out.println(page);
        	//String  str=response.getContent();
    
    	    //System.out.println(str);
    	    JXDocument doc = response.document();
    	    try{
    	      
    	      //List<Object> urls = doc.sel("//table[@class='splclist']");
    	      //List<Object> urls = doc.sel("//div/@id");
    	      //<div id="p_splc">
              if(url.indexOf("ajinfo_mx.aspx")>0){
            	    List<Object> data1 = doc.sel("//td[@class='wh35 l']/text()");
            	    List<Object> data2 = doc.sel("//td[@class='wh85 l']/text()");
            	    List<Object> data3 = doc.sel("//td[@class='pwd l']/text()");
            	  
	    	        //案号  类别  案由 立案日期 当事人  法庭  状态
	            	System.out.println(data1.get(0)+"\001"+data1.get(1)+"\001"+data1.get(2)+"\001"+data1.get(3)+"\001"+data3.get(0)+"\001"
	            	+data2.get(0)+"\001"+data2.get(1)+"\n\r");	
	            	filew.write(data1.get(0)+"\001"+data1.get(1)+"\001"+data1.get(2)+"\001"+data1.get(3)+"\001"+data3.get(0)+"\001"
	            	+data2.get(0)+"\001"+data2.get(1)+"\n\r");	
	            	filew.flush();
		            
              }else{
	          	    List<Object> data1 = doc.sel("//td[@class='wh35 l']/text()");
	        	    List<Object> data2 = doc.sel("//td[@class='wh85 l']/text()");
	        	    


	    	        //案号  类别  案由 立案日期 当事人  法庭  状态
	            	System.out.println(data2.get(0)+"\001"+" "+"\001"+data1.get(2)+"\001"+data1.get(1)+"\001"+data2.get(1) +" "+data2.get(2)+"\001"
	            			+data1.get(6)+"\001"+data2.get(3)+"\n\r");		
	            	
	            	filew.write(data2.get(0)+"\001"+" "+"\001"+data1.get(2)+"\001"+data1.get(1)+"\001"+data2.get(1) +" "+data2.get(2)+"\001"
	            	+data1.get(6)+"\001"+data2.get(3)+"\n\r");	
	            	filew.flush();
	            	//filew.
              }
    	    }catch(Exception o){
    	    	o.printStackTrace();
    	    }
            //logger.info("{}", urls.size());
            

    }
//  @Scheduled(cron = "0/5 * * * * ?")
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
