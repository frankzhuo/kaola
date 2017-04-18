package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;



//@Crawler(name = "zhidian",delay=6)
public class Zhidian extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;
		private BufferedWriter  filed=null;
		
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	    		filew=new BufferedWriter(new FileWriter("/data/crawler/zhidianresult.txt"));
	    		//filew=new BufferedWriter(new FileWriter("d:\\zhidianresult.txt"));
	    		//filed=new BufferedWriter(new FileWriter("d:\\51jobdetail.txt"));
	    		
	    	}catch(Exception o){
	    		
	    	}
	        return new String[]{"http://www.baidu.com"};
	    }
	    @Override
	    public String getUserAgent(){
//	        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
//	                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
//	                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
//	        };
	        String []uas= new String[]{"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"};
	        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
	    }

//	    @Override
//	    public String proxy() {
//	        String[] proxies = new String[]{"socket://10.5.13.22:3127"};
//	        return proxies[RandomUtils.nextInt()%proxies.length];
//	    }
	    @Override
	    public void start(Response response) {
            //System.out.println("oo");
	        try {    
	        	//System.out.println("oo1");
	        	//File file=new File("d:\\支点数据0~9999.csv");
	        	File file=new File("/data/zhidian.csv");
	        	BufferedReader reader=new BufferedReader(new FileReader(file));
	        	String tmp="";
	        	InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),"GBK");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i=0;
                while((tmp = bufferedReader.readLine()) != null){
	                i++;
	                if(i>2)
	                	break;
	        		//System.out.println(tmp);
	        		String []ar=tmp.split("\",\"");
	        		if(ar.length<23){
	        			//System.out.println("!!!!!!!!!!!!!"+tmp);
	        			continue;
	        		}
	        		//System.out.println(ar[22]);
	        		String url=ar[22];
	        		HashMap map=new HashMap();
	        		map.put("str", tmp);
	        		
	         	    HashMap<String,String> map1=new HashMap<String,String>();
	             	
	            	map1.put("Referer","http://www.ccgp.gov.cn/cggg/dfgg/zbgg/201410/t20141031_4684778.htm");
	            	map1.put("Accept","*/*");
	            	map1.put("Accept-Encoding", "gzip, deflate");
	            	map1.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
	            	map1.put("Connection", "keep-alive");
	            	//map.put("Content-Length", "354");
	           	
	            	//map.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	            	//map.put("Content-Length", "354");
	            	map1.put("Cookie", "Hm_lvt_9f8bda7a6bb3d1d7a9c7196bfed609b5=1464857123; Hm_lpvt_9f8bda7a6bb3d1d7a9c7196bfed609b5=1465183522");
	            	map1.put("Origin", "www.ccgp.gov.cn");
	            	map1.put("Host", "www.ccgp.gov.cn");
	            	map1.put("X-Requested-With", "XMLHttpRequest");
	            	map1.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

	            	
	        		if(url.indexOf("htm")>0)
	      	           push(new Request(url,"getUrls").setParams(map));		                  
	                                
            	 }
	          
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    public void getUrls(Response response){
	    	String  str=response.getContent();
	    	
	    	//response.getHttpResponse().getFirstHeader("")
	    	System.out.println("***"+response.getRealUrl());
	        JXDocument doc = response.document();
	        System.out.println(response.getContent());
	        try {
	        
	            //List<Object> urls = doc.sel("//ul[@class='ul_news_long']//a/@href");
	            
	        	//主体
	            List<Object> name = doc.sel("//div[@class='vT_detail_header']/h2/text()");
	   	        List<Object> detail = doc.sel("//div[@class='vT_detail_content w760c']/p/allText()");
	                  
	            System.out.println(name.get(0));
	            String content="";
	            for(Object ob : detail){
	            	System.out.println(ob);
	            	content=content+ob.toString();
	            }
	                                    
            	filew.write(name.get(0)+"\001"+content+"\n\r");	
            	filew.flush();;
            	
	          
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	//  @Scheduled(cron = "0/5 * * * * ?")
	//  public void callByCron(){
//	      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
//	   	try{
//			  filew=new FileWriter("d:\\shixin.txt");
//			  filec=new FileWriter("d:\\shixinc.txt");
//			  //DefaultLocalQueue qq=new DefaultLocalQueue();
//			  //this.setQueue(qq);
//		      for (long i=0;i<2000;i=i+50){
//	          	System.out.println(i);
//	          	 //push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//	              push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=10&ie=utf-8&oe=utf-8&format=json&t=1455870490009&cb=jQuery1102049660134187316657_1455863650458&_=1455863650463","getData").setSkipDuplicateFilter(true));
//	          }
//			
//		}catch(Exception o){
//			
//		}
//	      // 可定时发送一个Request
//	      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
	//  }

	}