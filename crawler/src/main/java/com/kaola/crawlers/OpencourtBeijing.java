package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;


//@Crawler(name = "opencourtbeijing",delay=10)
public class OpencourtBeijing extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;
		
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	    		//filew=new BufferedWriter(new FileWriter("d:\\opencourtbeijing.txt"));
	    		filew=new BufferedWriter(new FileWriter("/data/crawler/opencourtbeijing.txt",true));
		
	    	}catch(Exception o){
	    		
	    	}
	        return new String[]{"http://www.bjcourt.gov.cn"};
	    }
/*	    @Override
	    public String proxy() {
	        String[] proxies = new String[]{"socket://10.5.13.22:3127","http://10.5.13.22:3127"};
	        return proxies[RandomUtils.nextInt()%proxies.length];
	    }*/
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

	        try {
	        	Calendar cal=Calendar.getInstance();
    	    	cal.set(Calendar.YEAR,2013);
    	    	cal.set(Calendar.MONTH,10);
    	    	cal.set(Calendar.DAY_OF_MONTH,15);
    	    	SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
	            for (long i=1;i<365;i++){
	            //for (long i=1;i<2;i++){
	           		Date date=cal.getTime();
		    		String dstr=myFmt1.format(date);
		    		//System.out.println(dstr);
	            	cal.add(Calendar.DATE, 1);
	            	for(int j=1;j<31;j++){
	            	//for(int j=1;j<2;j++){
	            		  //System.out.println(i +" "+j);
		            	  if(j==4||j==14||j==24||j==25||j==26||j==27||j==28)
		            		  continue;
		            	  TimeUnit.SECONDS.sleep(10);
		            	  //System.out.println("http://www.bjcourt.gov.cn/ktgg/index.htm?court="+j+"&dateRange="+dstr+"&start="+dstr+"&end="+dstr);
		                  push(new Request("http://www.bjcourt.gov.cn/ktgg/index.htm?court="+j+"&dateRange="+dstr+"&start="+dstr+"&end="+dstr+"&type&p=1","getUrls").setHttpMethod(HttpMethod.POST).setSkipDuplicateFilter(true));
		                  TimeUnit.SECONDS.sleep(10);
		                  push(new Request("http://www.bjcourt.gov.cn/ktgg/index.htm?court="+j+"&dateRange="+dstr+"&start="+dstr+"&end="+dstr+"&type&p=2","getUrls").setHttpMethod(HttpMethod.POST).setSkipDuplicateFilter(true));		                  
		                                
	            	 }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    public void getUrls(Response response){
	    	String  str=response.getContent();
	    	//response.getHttpResponse().getFirstHeader("")
	    	//System.out.println("***"+str);
	        JXDocument doc = response.document();
	        try {
	        
	            List<Object> urls = doc.sel("//ul[@class='ul_news_long']//a/@href");
	            //List<Object> urls = doc.sel("//<ul/text()");	            
	            //List<Object> list6 = doc.sel("//div[@class='index_blackname_left']//a[@class='index_blk6']/text()");
                List<Object> title = doc.sel("//ul[@class='ul_news_long']//a/@title");
	            //logger.info("{}", urls.size());
	            
          	        
	            for(int i=0;i<urls.size();i++){
	                HashMap<String,String> map=new HashMap<String,String>();	             	
	            	map.put("title",title.get(i).toString());	 
	            	System.out.println("列表！！！！！！！！！！"+title.get(i).toString());
//	            	System.out.println(urls.get(i).toString());
//	            	System.out.println("http://www.bjcourt.gov.cn"+urls.get(i).toString().trim());
	            	//push(new Request("http://www.bjcourt.gov.cn/ktgg/ktggDetailInfo.htm?NId=57629&NAjbh=8750221","getData"));
	                push(new Request("http://www.bjcourt.gov.cn"+urls.get(i).toString().trim(),"getData").setTrans(map));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	        public void getData(Response response){	
	        	        	
	        	String url=response.getRealUrl();	        	
	        	//System.out.println(response.getRealUrl());
	        	//System.out.println(page);
	        	String  str=response.getContent();
	    
	    	    //System.out.println(str);
	    	    //System.out.println(response.getTrans().toString());
	    	    System.out.println("detail*****"+response.getTrans().get("title"));
	    	    JXDocument doc = response.document();
	    	    try{
		    	    List<Object> court = doc.sel("//div[@class='article_con']/p[@class='p_text_max2']/text()");
		    	    List<Object> content = doc.sel("//div[@class='article_con']/p[@class='p_text p_ktgg_content']/text()");
		    	    List<Object> openTime = doc.sel("//div[@class='article_con']/p[@class='p_sign p_ggsj']/text()");

            	    String title=response.getTrans().get("title");
	    	        //案号  类别  案由 立案日期 当事人  法庭  状态
	            	System.out.println(title+"\001"+court.get(0)+"\001"+content.get(0)+"\001"+openTime.get(0)+"\n\r");	
	            	filew.write(title+"\001"+court.get(0)+"\001"+content.get(0)+"\001"+openTime.get(0)+"\n\r");	
	            	
	            	filew.flush();
			  

	    	    }catch(Exception o){
	    	    	o.printStackTrace();
	    	    }
	            //logger.info("{}", urls.size());
	    }


	}
