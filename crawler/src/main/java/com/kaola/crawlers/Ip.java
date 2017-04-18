package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;



//@Crawler(name = "ip")
public class Ip extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;
		private BufferedWriter  filed=null;
		
		private int page=0;
		private int num=0;
		
	    @Override
	    public String[] startUrls() {
	    	System.out.println("***************begin");
	    	try{
	    		filew=new BufferedWriter(new FileWriter("/data/ipnew.txt"));     		
	    	}catch(Exception o){
	    		o.printStackTrace();
	    	}
	        return new String[]{"http://www.proxy360.cn/default.aspx"};
	    }
		public  ProxyIP  getProxyIpOld(){
			System.out.println("进入更换IP");
			BufferedReader reader = null;
			ProxyIP  ip=new ProxyIP();
			try{
				FileWriter file=new FileWriter("/data/ipnew.txt");
				//reader = new BufferedReader(new FileReader("/data/ip.txt"));
				reader = new BufferedReader(new FileReader("/data/ipall.txt"));

				String tempString="";
				ArrayList<ProxyIP > ar=new ArrayList<ProxyIP >();
				while ((tempString = reader.readLine()) != null) {
					String split[]=tempString.split(" ");
					try{
			    	    HttpClient client = new HttpClient(); 
			    	    HostConfiguration config =client.getHostConfiguration();	    	    
			    	    config.setProxy(split[0], Integer.parseInt(split[1]));
			    	    System.out.println(client.getHostConfiguration().getProxyHost());			    
					    GetMethod get = new GetMethod("http://www.baidu.com");					    
					    client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
					    client.getHttpConnectionManager().getParams().setSoTimeout(2000);
					    client.executeMethod(get);
					    //System.out.println(get.getResponseBodyAsString());
					    if(get.getStatusLine().getStatusCode()==200){
					    	
					    	ProxyIP  tmp=new ProxyIP();
					    	tmp.setIp(split[0]);
					    	tmp.setPort(Integer.parseInt(split[1]));
					    	ar.add(tmp);
					    	//hash.put(split[0], Integer.parseInt(split[1]));
					    	file.write(tempString+"\n");
					    	file.flush();
							System.out.println("ok"+tempString);
							//break;
					    }
					    get.releaseConnection();				    
					}catch(Exception o){
						o.printStackTrace();
					}
					
				}				       
		       ip=ar.get( Math.abs(new Random().nextInt())%ar.size());			
		       file.close();
			}catch(Exception o){
				//ip.setIp("124.200.181.50");
				//ip.setPort(8118);
				o.printStackTrace();
			}
			
			return  ip;
		}
		public  ProxyIP  getProxyIp(){
			System.out.println("进入更换IP");
			BufferedReader reader = null;
			ProxyIP  ip=new ProxyIP();
			try{
				FileWriter file=new FileWriter("/data/iptime.txt");
				//reader = new BufferedReader(new FileReader("/data/ip.txt"));
				reader = new BufferedReader(new FileReader("/data/ipnew.txt"));

				String tempString="";
				ArrayList<ProxyIP > ar=new ArrayList<ProxyIP >();
				while ((tempString = reader.readLine()) != null) {
					String split[]=tempString.split(" ");
					try{
			    	    HttpClient client = new HttpClient(); 
			    	    HostConfiguration config =client.getHostConfiguration();	    	    
			    	    config.setProxy(split[0], Integer.parseInt(split[1]));
			    	    System.out.println(client.getHostConfiguration().getProxyHost());			    
					    GetMethod get = new GetMethod("http://www.baidu.com");					    
					    client.getHttpConnectionManager().getParams().setConnectionTimeout(4000);
					    client.getHttpConnectionManager().getParams().setSoTimeout(4000);
					    client.executeMethod(get);
					    //System.out.println(get.getResponseBodyAsString());
					    if(get.getStatusLine().getStatusCode()==200){
					    	
					    	ProxyIP  tmp=new ProxyIP();
					    	tmp.setIp(split[0]);
					    	tmp.setPort(Integer.parseInt(split[1]));
					    	ar.add(tmp);
					    	//hash.put(split[0], Integer.parseInt(split[1]));
					    	file.write(tempString+"\n");
					    	file.flush();
							System.out.println("ok"+tempString);
							//break;
					    }
					    get.releaseConnection();
					    client=null;
					}catch(Exception o){
						o.printStackTrace();
					}
					
				}				       
		       ip=ar.get( Math.abs(new Random().nextInt())%ar.size());			
		       file.close();
			}catch(Exception o){
				//ip.setIp("124.200.181.50");
				//ip.setPort(8118);
				o.printStackTrace();
			}
			
			return  ip;
		}
		

	    @Override
	    public String getUserAgent(){
	        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
	                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
	                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
	        };
	        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
	    }
        public  void parse(Response response){
        	JXDocument doc = response.document();
  	        try {    
  		        List<Object> name = doc.sel("//div[@class='proxylistitem']/div[@style='float:left; display:block; width:630px;']/span/text()");
  	            String ip="";
  	            String port="";
  	            for(int i=0;i<name.size();i++){
  	            	if(i%8==0){
  	            		ip=name.get(i).toString();
  	            	}
  	            	if(i%8==1){
  	            		port=name.get(i).toString();
  	            		filew.write(ip + " " + port+"\n");
  	            		filew.flush();
  	            		System.out.println(ip + " " + port);
  	            	}
  	            	
  	            }
  	        	
  	            //filew.close();
  	           //getProxyIpOld();
  	           getProxyIp();
  	        } catch (Exception e) {
  	            e.printStackTrace();
  	        }
        }
	    @Override
	    public void start(Response response) {
            //System.out.println("oo");
	    	parse(response);
	    	
	    }
	 
	  //@Scheduled(cron = "5 1 * * * ?")
	  public void callByCron(){
	    logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
	   	try{
      	  //push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
          push(new Request("http://www.proxy360.cn/default.aspx","parse").setSkipDuplicateFilter(true));
	        		
		}catch(Exception o){
			
		}
	      // 可定时发送一个Request
	      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
	  }

	}