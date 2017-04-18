package com.kaola.crawlers;

import java.util.List;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultLocalQueue;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "beizhixin")
public class Beizhixin  extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
	private int page=0;
	private int num=0;
    @Override
    public String[] startUrls() {
    	try{
    		//filew=new FileWriter("d:\\beizhixin.txt");
    		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd-HH");    	   
      	    filew=new FileWriter("/data/crawler/beizhixing-"+myFmt1.format(new Date())+".txt");

    		
    	}catch(Exception o){
    		
    	}
    	//return null;
        return new String[]{"http://zhixing.court.gov.cn"};
    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            //for (long i=473327;i<1000000;i++){
            for (long i=2957900;i<10000000;i++){	
            	System.out.println(i);
                push(new Request("http://zhixing.court.gov.cn/search/detail?id="+i,"getData"));
            }
        } catch (Exception e) {
        	System.out.println("异常了");
            e.printStackTrace();
        }
    }
    public void getData(Response response){
    	
    	System.out.println(response.getRealUrl());
    	if(response.getHttpResponse().getStatusLine().toString().indexOf("500")>0)
    		return;
    	String  str=response.getContent();
    	System.out.println(str);
    			
    	//System.out.println(jsonString);
    	Map<String, String> map = new HashMap<String, String>();
	        try {
	            Gson gson = new Gson();
	            map = gson.fromJson(str,
	                    new TypeToken<Map<String, String>>() {
	                    }.getType());
	            	System.out.println(map.get("caseCode"));            	
	            	filew.write(map.get("id")+"\001"+map.get("caseCode")+"\001"+map.get("caseState")+"\001"+map.get("execCourtName")+"\001"+map.get("execMoney")+"\001"+map.get("partyCardNum")+"\001"
	            			    +map.get("pname")+"\001"+map.get("caseCreateTime")+"\n\r");
	            	filew.flush();

	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	           
	      }
    }

    
}