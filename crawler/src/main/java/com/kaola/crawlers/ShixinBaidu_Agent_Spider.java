package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "shixinbaidu_agent_spider",proxy="123.56.139.108:8123")
public class ShixinBaidu_Agent_Spider extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
	private BufferedReader fileReader = null;
    @Override
    public String[] startUrls() {
    	try{
    		System.out.println("baidu begin");
    	    SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	 
    	    Calendar cal = Calendar.getInstance();
    	    String today = myFmt1.format(cal.getTime());
    	    System.out.println("today"+ today);
    	    cal.add(cal.DAY_OF_MONTH, -2);
    	    
    	    String yesterday = myFmt1.format(cal.getTime());
    	    System.out.println("yesterday"+ yesterday);
    	    
    	    fileReader = new BufferedReader(new FileReader("/data/crawler/shixin/courtlosecredited_"+yesterday));
    	    filew=new FileWriter("/data/crawler/shixin-"+today+".txt",true);
    	    filec=new FileWriter("/data/crawler/shixinc-"+today+".txt",true);
    		
    	}catch(Exception o){
    		o.printStackTrace();
    	}
        return new String[]{"http://www.baidu.com"};
    }
    
//    @Override
//    public String proxy() {
//        return "http://123.56.139.108:8123";
//    }
    
    @Override
    public void start(Response response) {
    	String line;
        try {
        	//最大页大小是50个
        	while((line = fileReader.readLine()) !=null){
	        	Map<String, String> header = new HashMap<String, String>();
	        	header.put("Proxy-Authorization",getSyncIp());
	        	header.put("Accept", "*/*");
	        	header.put("Accept-Encoding","gzip, deflate, sdch");
	        	
	        	line = line.split("\001")[1];
	        	
	        	System.out.println("-------------the request ------------------"+line);
	        	push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA%E5%90%8D%E5%8D%95&cardNum=&iname="+line.trim()+"&areaName=&pn=0&rn=50&&ie=utf-8&oe=utf-8&format=json&t="+new Date().getTime(),"getData").setSkipDuplicateFilter(true));
        	    Thread.sleep(2*1000);
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void getData(Response response){
    	String  str=response.getContent();
    	int begin =str.indexOf("result\":");
    	int end =str.indexOf(", \"otherinfo\"");
    	
    	if(end < 0 ){
    		return;
    	}
    	
    	String jsonString=str.substring(begin+8, end);

    	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    try {
	            Gson gson = new Gson();
	            list = gson.fromJson(jsonString,new TypeToken<List<Map<String, String>>>(){}.getType());
	            System.out.println("--------list.size()-----------"+list.size());
	            for(int i=0;i<list.size();i++){
	            	String id=list.get(i).get("loc").substring(list.get(i).get("loc").indexOf("id=")+3);
	            	
	            	System.out.println(i+"------id----"+id);
	            	
	            	if(list.get(i).get("cardNum").length()>11){//个人,在百度搜索"失信人被执行人"关键字,重起后又继续爬取
		            	filew.write(id+"\001"+id+"\001"+list.get(i).get("iname")+"\001"+list.get(i).get("caseCode")+"\001"+list.get(i).get("age")+"\001"+list.get(i).get("sexy")+"\001"
		            			+list.get(i).get("cardNum")+"\001"+list.get(i).get("courtName")+"\001"+list.get(i).get("areaName")+"\001"+list.get(i).get("gistId")+"\001"
		            			+list.get(i).get("regDate")+"\001"+list.get(i).get("gistUnit")+"\001"+list.get(i).get("duty")+"\001"+list.get(i).get("performance")+"\001"
		            			+list.get(i).get("disruptTypeName")+"\001"+list.get(i).get("publishDate")+"\001"+list.get(i).get("_update_time")+"\001"+list.get(i).get("partyTypeName")+"\n\r");
		            	filew.flush();
	            	}
	            	else{
	            		filec.write(id+"\001"+id+"\001"+list.get(i).get("iname")+"\001"+list.get(i).get("caseCode")+"\001"+list.get(i).get("businessEntity")+"\001"
	    		            	+list.get(i).get("cardNum")+"\001"+list.get(i).get("courtName")+"\001"+list.get(i).get("areaName")+"\001"+list.get(i).get("gistId")+"\001"
	    		            	+list.get(i).get("regDate")+"\001"+list.get(i).get("gistUnit")+"\001"+list.get(i).get("duty")+"\001"+list.get(i).get("performance")+"\001"
	    		            	+list.get(i).get("disruptTypeName")+"\001"+list.get(i).get("publishDate")+"\001"+list.get(i).get("_update_time")+"\001"+list.get(i).get("partyTypeName")+"\n\r");
                        filec.flush();
	            	}
	            }
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	      }

    }
    
  /*
   * 收费代理IP
   */
	public static String getSyncIp(){
		// 定义申请获得的appKey和appSecret
		String appkey = "215106405";
		String secret = "42a0fb022a9d5a82f77e63bc6ba2ab09";
		 
		// 创建参数表
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("app_key", appkey);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));//使用中国时间，以免时区不同导致认证错误
		paramMap.put("timestamp", format.format(new Date()));
		 
		// 对参数名进行排序
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);
		 
		// 拼接有序的参数名-值串
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(secret);
		for(String key : keyArray){
		    stringBuilder.append(key).append(paramMap.get(key));
		}
		     
		stringBuilder.append(secret);
		String codes = stringBuilder.toString();
		         
		// MD5编码并转为大写， 这里使用的是Apache codec
		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(codes).toUpperCase();

		paramMap.put("sign", sign);

		// 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
		String authHeader = "MYH-AUTH-MD5 sign=" +sign+"&timestamp="+format.format(new Date())+"&app_key=" +appkey;

		System.out.println(authHeader);

		//接下来使用蚂蚁动态代理进行访问
		   
		//这里以jsoup为例，将authHeader放入请求头中即可
		//原版的jsoup设置代理不方便，这里使用E-lai提供的修改版(https://github.com/E-lai/jsoup-proxy)
		//注意authHeader必须在每次请求时都重新计算，要不然会因为时间误差而认证失败
//		Jsoup.connect("http://1212.ip138.com/ic.asp");
		return authHeader;
	}
	
	
	  @Scheduled(cron = "0 0 15 * * ?")
	  public void callByCron(){
	      logger.info("(0 0 15 * * ?) 我是一个根据cron表达式执行的调度器,所在的类是："+this.getCrawlerName());
	      Date dd=new Date();
	      
	      try {
	    	   if(filew != null){
	    		   filew.close();
	    	   }
	    	   if(filec !=null){
	    		   filec.close();
	    	   }
	    	   
	    	   SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	 
	    	    Calendar cal = Calendar.getInstance();
	    	    String today = myFmt1.format(cal.getTime());
	    	    System.out.println("today"+ today);
	    	    cal.add(cal.DAY_OF_MONTH, -1);
	    	    
	    	    String yesterday = myFmt1.format(cal.getTime());
	    	    System.out.println("yesterday"+ yesterday);
	    	    
	    	    fileReader = new BufferedReader(new FileReader("/data/crawler/shixin/courtlosecredited_"+yesterday));
	    	    filew=new FileWriter("/data/crawler/shixin-"+today+".txt",true);
	    		filec=new FileWriter("/data/crawler/shixinc-"+today+".txt",true);
	    		
	    		
	    		String line;
            	//最大页大小是50个
            	while((line = fileReader.readLine()) !=null){
    	        	Map<String, String> header = new HashMap<String, String>();
    	        	header.put("Proxy-Authorization",getSyncIp());
    	        	header.put("Accept", "*/*");
    	        	header.put("Accept-Encoding","gzip, deflate, sdch");
    	        	
    	        	line = line.split("\001")[1];
    	        	
    	        	System.out.println("-------------the request ------------------"+line);
    	        	push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA%E5%90%8D%E5%8D%95&cardNum=&iname="+line.trim()+"&areaName=&pn=0&rn=50&&ie=utf-8&oe=utf-8&format=json&t="+new Date().getTime(),"getData").setSkipDuplicateFilter(true));
            	    Thread.sleep(2*1000);
            	}
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	  }
}