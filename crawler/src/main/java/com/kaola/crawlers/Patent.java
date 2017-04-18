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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;



//@Crawler(name = "patent",delay=20)
public class Patent extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;
		private BufferedWriter  filed=null;
		
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	            SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	   
			    filew=new BufferedWriter(new FileWriter("/data/crawler/patent"+myFmt1.format(new Date())+".txt"));

	    	    //SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	   
	    		//filew=new BufferedWriter(new FileWriter("/data/crawler/patent"+myFmt1.format(new Date())+".txt",true));	    		
	    	}catch(Exception o){
	    		
	    	}
	        return new String[]{"http://epub.sipo.gov.cn"};
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

			Date cur=new Date();
	    	Calendar cal=Calendar.getInstance();
	    	cal.setTime(cur);
	    	cal.add(Calendar.DATE, -54);
	    	SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy.MM.dd"); 
	    		Date date=cal.getTime();
	    		String dstr=myFmt2.format(date);
		    System.out.println(dstr);
   			HashMap<String,String> map=new HashMap<String,String>();
        	map.put("showType","1");
        	map.put("strSources", "");
        	map.put("strWhere", "OPD='"+dstr+"'");
        	map.put("pageSize", "10");
        	map.put("pageNow", "1");   	    	        
//        	map.put("numSortMethod","0");
//        	map.put("strLicenseCode","");
//        	map.put("numIp","55");
//        	map.put("numIpc","");
//        	map.put("numIg","0");
//        	map.put("numIgc","");
//        	map.put("numIgd","");
//        	map.put("numUg","1");
//        	map.put("numUgc","");
//        	map.put("numUgd","");
//        	map.put("numDg","2");
//            map.put("numDgc","");
        
        	
            push(new Request("http://epub.sipo.gov.cn/patentoutline.action","getUrls").setHttpMethod(HttpMethod.POST).setParams(map).setSkipDuplicateFilter(true));

 
	    }
	    public void getUrls(Response response){
	    	String  str=response.getContent();
	    	System.out.println(str);
    	    JXDocument doc = response.document();
    	    try{
	    	    List<Object> page = doc.sel("//input[@id='pn']/@onkeypress");
    	    	    
	    	    //for(int i=0;i<position.size();i++){
	    	    System.out.println(page.get(0));
	    	    
	    	    String tmp=page.get(0).toString();
	    	    int begin=tmp.indexOf("zl_tz(");
	    	    String o=tmp.substring(begin+6,tmp.length()-1);
	    	    System.out.println(o);
	    	    
	    	    Date cur=new Date();
		    	Calendar cal=Calendar.getInstance();
		    	cal.setTime(cur);
		    	cal.add(Calendar.DATE, -54);
		    	SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy.MM.dd"); 
		    		Date date=cal.getTime();
		    		String dstr=myFmt2.format(date);
			    System.out.println(dstr);
			    
	    	    
	    	    for(int i=1;i< Integer.parseInt(o);i++){
		    		HashMap<String,String> map=new HashMap<String,String>();
		        	map.put("showType","1");
		           	map.put("strSources", "pip");
		        	map.put("strWhere", "OPD='"+dstr+"'");
		        	map.put("pageSize", "10");
		        	map.put("pageNow", i+"");   	    	        
//		        	map.put("numSortMethod","0");
//		        	map.put("strLicenseCode","");
//		        	map.put("numIp","13805");
//		        	map.put("numIpc","");
//		        	map.put("numIg","9505");
//		        	map.put("numIgc","2");
//		        	map.put("numIgd","");
//		        	map.put("numUg","17017");
//		        	map.put("numUgc","2");
//		        	map.put("numUgd","");
//		        	map.put("numDg","6568");
//		            map.put("numDgc","1");  
		        	TimeUnit.SECONDS.sleep(20);
		            push(new Request("http://epub.sipo.gov.cn/patentoutline.action","getData").setHttpMethod(HttpMethod.POST).setParams(map).setSkipDuplicateFilter(true));
	    	    }

    	    }catch(Exception o){
    	    	o.printStackTrace();
    	    }
	    }
	    public void getData(Response response){	
	        	
	        	String url=response.getRealUrl();
	        	
		        String  str=response.getContent();
		        System.out.println(str);
	 	    	JXDocument doc = response.document();
	    	    try{
	
		    	    List<Object> box = doc.sel("//div[@class='cp_box']/div[@class='cp_linr']/node()");
	    	    	    
		      	    System.out.println(box.size());
		            int  num=0;
		            String  ip="";
		            String  port="";
		            int  jj=0;
		            
		            String class_no="";
		            String address="";		          
		            String application_date="";
		            String paten_name="";
		            String release_date="";
		            String summary="";
		            String  release_no="";
		            String agent_name="";
		            String application_no="";
		            String application_name="";
		            String patent_agency="";
		            String inventor="";
		            
		            for(Object  child:box){
		            	
		            	Element el=(Element)  child;
		            	System.out.println(el.tagName());
		            
		            	//System.out.println(el.tagName());
		            	if(el.tagName().equals("h1")){
		            		 jj=0;
		            		 class_no="";
				             address="";		          
				             application_date="";
				             paten_name="";
				             release_date="";
				             summary="";
				             release_no="";
				             agent_name="";
				             application_no="";
				             application_name="";
				             patent_agency="";
				             inventor="";
				             paten_name=el.text();
		            		 System.out.println("标题"+el.text());
		            	}
		            	if(el.tagName().equals("li")){
		            		jj++;
		            		System.out.println(el.text());
		            		System.out.println(jj);
		            		if(jj==1&&el.text()!=null){//公布号
		            			release_no=el.text().replaceAll("申请公布号：", "");
		            		}
		            		else if(jj==2&&el.text()!=null){//公布日期
		            			release_date=el.text().replaceAll("申请公布日：", "");
		            		}
                            else if(jj==3&&el.text()!=null){//申请号
                            	application_no=el.text().replaceAll("申请号：", "");
		            		}
                            else if(jj==4&&el.text()!=null){//申请日期
                            	application_date=el.text().replaceAll("申请日：", "");
                           }
                           else if(jj==5&&el.text()!=null){//申请人
                        	   application_name=el.text().replaceAll("申请人：", "");
                           }
                           else if(jj==6&&el.text()!=null){//发明人
                        	   inventor=el.text().replaceAll("发明人：", "");
                           }
                           else if(jj==8&&el.text()!=null){
                        	   address=el.text().replaceAll("地址：", "");
                           }
                           else if(jj==9&&el.text()!=null){
                        	   class_no=el.text().replaceAll("分类号：", "");
                        	   int begin=class_no.indexOf("专利代理机构：");
                        	   if(begin>-1)
                        		   class_no=class_no.substring(0, begin);
                        	   class_no=class_no.replaceAll("全部", "");
                        	   System.out.println("clasno"+el.text());
                           }
                           
		            	}
		            	System.out.println(el.attr("class"));
		            	if(el.tagName().equals("div")&&el.attr("class").toString().equals("cp_jsh")){
		            		if(el.text()!=null)
		            			summary=el.text().replaceAll("摘要：", "");
		            		String result=class_no+"\001"+address+"\001"+application_date+"\001"
		            		+paten_name+"\001"+release_date+"\001"+summary+"\001"+release_no+"\001"
		            				+agent_name+"\001"+application_no+"\001"+application_name+"\001"+patent_agency+"\001"+inventor+"\n";	
		            		System.out.println(result);
		            		filew.write(result);				            	
			            	filew.flush();
			         
		            	}
		            }

		    	    //}

	    	    }catch(Exception o){
	    	    	o.printStackTrace();
	    	    }
	            //logger.info("{}", urls.size());
	            

	    }
	  @Scheduled(cron = "1 1 1 * * ?")
	  public void callByCron(){
	      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
	   	try{
	   		    
    	        SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	   
    		    filew=new BufferedWriter(new FileWriter("/data/crawler/patent"+myFmt1.format(new Date())+".txt"));
    		    Date cur=new Date();
    	    	Calendar cal=Calendar.getInstance();
    	    	cal.setTime(cur);
    	    	cal.add(Calendar.DATE, -54);
    	    	SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy.MM.dd"); 
 	    		Date date=cal.getTime();
 	    		String dstr=myFmt2.format(date);
			    System.out.println(dstr);
	   			HashMap<String,String> map=new HashMap<String,String>();
	        	map.put("showType","1");
	        	map.put("strSources", "");
	        	map.put("strWhere", "OPD='"+dstr+"'");
	        	map.put("pageSize", "10");
	        	map.put("pageNow", "1");    
		    		
	            push(new Request("http://epub.sipo.gov.cn/patentoutline.action","getUrls").setHttpMethod(HttpMethod.POST).setParams(map).setSkipDuplicateFilter(true));

			
		}catch(Exception o){
			
		}
	      // 可定时发送一个Request
	      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
	  }

	}