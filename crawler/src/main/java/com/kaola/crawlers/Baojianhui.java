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

//@Crawler(name = "baojianhui",delay=6,proxy = "http://10.5.28.14:5656")
//@Crawler(name = "baojianhui",delay=6,proxy = "socket://10.5.28.14:5656")
public class Baojianhui extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;	
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	    		filew=new BufferedWriter(new FileWriter("d:\\baojianhui.txt"));
	    		//filew=new BufferedWriter(new FileWriter("/data/crawler/baojianhui.txt",true));
		
	    	}catch(Exception o){
	    		
	    	}
	        return new String[]{"http://www.circ.gov.cn/"};
	    }

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
            System.out.println("121212");
	        try {
	            for (long i=1;i<9;i++){
		             //System.out.println("http://www.bjcourt.gov.cn/ktgg/index.htm?court="+j+"&dateRange="+dstr+"&start="+dstr+"&end="+dstr);
		             push(new Request("http://www.circ.gov.cn/web/site0/tab5240/module14430/page"+i+".htm","getUrls").setHttpMethod(HttpMethod.GET).setSkipDuplicateFilter(true));		                                     	            	
	            }
	            for (long i=1;i<664;i++){
		             //System.out.println("http://www.bjcourt.gov.cn/ktgg/index.htm?court="+j+"&dateRange="+dstr+"&start="+dstr+"&end="+dstr);
		             push(new Request("http://www.circ.gov.cn/web/site0/tab5241/module14458/page"+i+".htm","getUrlsd").setHttpMethod(HttpMethod.GET).setSkipDuplicateFilter(true));	
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
	        	                                          //ess_ctr14430_ListC_Info_LstC_Info                 
	            List<Object> urls = doc.sel("//table[@id='ess_ctr14430_ListC_Info_LstC_Info']//a/@href");	                 	        
	            for(int i=0;i<urls.size();i++){
	            
	            	System.out.println("http://www.circ.gov.cn"+urls.get(i).toString().trim());
//	            	System.out.println(urls.get(i).toString());
//	            	System.out.println("http://www.bjcourt.gov.cn"+urls.get(i).toString().trim());
	            	push(new Request("http://www.circ.gov.cn"+urls.get(i).toString().trim(),"getData"));
	            	TimeUnit.SECONDS.sleep(5);
	                //push(new Request("http://www.bjcourt.gov.cn"+urls.get(i).toString().trim(),"getData").setTrans(map));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    public void getUrlsd(Response response){
	    	String  str=response.getContent();
	    	//response.getHttpResponse().getFirstHeader("")
	    	//System.out.println("***"+str);
	        JXDocument doc = response.document();
	        try {
	        	                                    
	            List<Object> urls = doc.sel("//table[@id='ess_ctr14458_ListC_Info_LstC_Info']//a/@href");	                 	        
	            for(int i=0;i<urls.size();i++){
	            
	            	System.out.println("http://www.circ.gov.cn"+urls.get(i).toString().trim());
//	            	System.out.println(urls.get(i).toString());
//	            	System.out.println("http://www.bjcourt.gov.cn"+urls.get(i).toString().trim());
	            	push(new Request("http://www.circ.gov.cn"+urls.get(i).toString().trim(),"getData"));
	            	TimeUnit.SECONDS.sleep(5);
	                //push(new Request("http://www.bjcourt.gov.cn"+urls.get(i).toString().trim(),"getData").setTrans(map));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	        public void getData(Response response){	
	        	        	
	        	String url=response.getRealUrl();	        	
	        	//System.out.println(response.getRealUrl());
	        	//System.out.println(page);
	        	System.out.println("@@@"+url);
	        	String  str=response.getContent();
	    
	    	    //System.out.println(str);
	    	    //System.out.println(response.getTrans().toString());
	    	    //System.out.println("detail*****"+response.getTrans().get("title"));
	    	    JXDocument doc = response.document();
	    	    try{
		    	    List<Object> court = doc.sel("//table[@id='tab_content']/tbody/tr/td/text()");
		            //System.out.println(court.size());
		    	    System.out.println(court.get(0));
		    	    System.out.println(court.get(1));
		    	    List<Object> detail = doc.sel("//table[@id='tab_content']/tbody/tr/td/span/p/allText()");
			        if(detail.size()==0){
			    	    detail = doc.sel("//table[@id='tab_content']/tbody/tr/td/span/allText()");
			        }
		    	    String title=court.get(0).toString();
			        String times=court.get(1).toString();
			        int b=times.indexOf("：");
			        int e=times.indexOf(" ");
			        if(e>b){
			        	times=times.substring(b+1, e);
			        }
		    	    System.out.println(times);
			        String content="";
                    for(Object s:detail){
                    	content=content+s.toString()+"\002";
                    	//System.out.println(s.toString());
                    }
	    	        //案号  类别  案由 立案日期 当事人  法庭  状态
	            	System.out.println(title+"\001"+times+"\001"+content+"\n");	
	            	filew.write(title+"\001"+times+"\001"+content+"\n");	
	            	filew.flush();
			  

	    	    }catch(Exception o){
	    	    	o.printStackTrace();
	    	    }
	            //logger.info("{}", urls.size());
	    }


	}
