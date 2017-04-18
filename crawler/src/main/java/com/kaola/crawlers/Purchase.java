package com.kaola.crawlers;

import java.util.List;
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
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

@Crawler(name = "purchase",delay=60)
public class Purchase  extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
	private FileWriter  fileurl=null;
	private int page=0;
	private int num=0;
	public static int ipnum=0;
	public static int ipplace=0;
	public static ArrayList<ProxyIP > ar=new ArrayList<ProxyIP >();
    @Override
    public String[] startUrls() {
    	try{
    		System.out.println("33333333333333333");
    		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	   
      	    filew=new FileWriter("/data/crawler/purchase-"+myFmt1.format(new Date())+".txt",true);
      	    fileurl=new FileWriter("/data/crawler/purchaseurl-"+myFmt1.format(new Date())+".txt",true);

    		//filew=new FileWriter("\data\purchase.txt");
    		
    	}catch(Exception o){
    		
    	}
    	//return null;
//    	Map<String,String> map1= new HashMap();
// 	    map1.put("Proxy-Authorization",getSyncIp());
        return new String[]{"http://www.sohu.com"};
    }
    public synchronized static String getSyncIp(){
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

//		System.out.println(authHeader);

		//接下来使用蚂蚁动态代理进行访问
		   
		//这里以jsoup为例，将authHeader放入请求头中即可
		//原版的jsoup设置代理不方便，这里使用E-lai提供的修改版(https://github.com/E-lai/jsoup-proxy)
		//注意authHeader必须在每次请求时都重新计算，要不然会因为时间误差而认证失败
//		Jsoup.connect("http://1212.ip138.com/ic.asp");
		return authHeader;
				
	}
    @Override
    public String getUserAgent(){
        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
        };
        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
    }
	public synchronized  static ProxyIP  getProxyIp(){
		System.out.println("进入更换IP");
		BufferedReader reader = null;
		
		ProxyIP  ip=new ProxyIP();
		try{
			reader = new BufferedReader(new FileReader("/data/ip5.txt"));
			String tempString="";
			if(ipnum%50==0){
				ar=new ArrayList<ProxyIP >();
				while ((tempString = reader.readLine()) != null) {
					String split[]=tempString.split(" ");
					try{
			    	    HttpClient client = new HttpClient(); 
			    	    HostConfiguration config =client.getHostConfiguration();	    	    
			    	    config.setProxy(split[0], Integer.parseInt(split[1]));
			    	    System.out.println(client.getHostConfiguration().getProxyHost());			    
					    //PostMethod post = new UTF8PostMethod("http://www.baidu.com");					    
					    GetMethod get = new GetMethod("http://www.baidu.com");					    
					    //GetMethod get = new GetMethod("http://search.ccgp.gov.cn/dataB.jsp?searchtype=2&page_index=1&bidSort=0&buyerName=&projectId=&pinMu=0&bidType=9&dbselect=bidx&kw=&start_time=2016%3A09%3A13&end_time=2016%3A09%3A20&timeType=2&displayZone=&zoneId=&pppStatus=&agentName=");					    

					    client.getHttpConnectionManager().getParams().setConnectionTimeout(4000);
					    client.getHttpConnectionManager().getParams().setSoTimeout(4000);
					    client.executeMethod(get);
					    String body=get.getResponseBodyAsString();					    
					    if(get.getStatusLine().getStatusCode()==200 && body.indexOf("hao123")>0){
					    //if(get.getStatusLine().getStatusCode()==200 && body.indexOf("ccgp.gov.cn")>0){
					    	
					    	ProxyIP  tmp=new ProxyIP();
					    	tmp.setIp(split[0]);
					    	tmp.setPort(Integer.parseInt(split[1]));
					    	ar.add(tmp);
					    	//hash.put(split[0], Integer.parseInt(split[1]));
					    	
							System.out.println("ok"+tempString);
							//break;
					    }
					    get.releaseConnection();
					    
					}catch(Exception o){
						o.printStackTrace();
					}
					
				}
		   }
		   ipnum++;
		   ProxyIP iptmp=ip;

		   if(ipplace>(ar.size()-1)){
			   ipplace=0;  
		   }
		   System.out.println("ipplace"+ ipplace+" ar:"+ar.size());
		   ip=ar.get(ipplace);
		   boolean flag=true;
		   while(flag){
			   ipplace++;
			   try{
		    	    HttpClient client = new HttpClient(); 
		    	    HostConfiguration config =client.getHostConfiguration();	    	    
		    	    config.setProxy(ip.getIp(),ip.getPort() );
		    	    System.out.println(client.getHostConfiguration().getProxyHost());			    
				    //PostMethod post = new UTF8PostMethod("http://www.baidu.com");					    
				    GetMethod get = new GetMethod("http://www.baidu.com");					    
				    //GetMethod get = new GetMethod("http://search.ccgp.gov.cn/dataB.jsp?searchtype=2&page_index=1&bidSort=0&buyerName=&projectId=&pinMu=0&bidType=9&dbselect=bidx&kw=&start_time=2016%3A09%3A13&end_time=2016%3A09%3A20&timeType=2&displayZone=&zoneId=&pppStatus=&agentName=");					    
	
				    client.getHttpConnectionManager().getParams().setConnectionTimeout(4000);
				    client.getHttpConnectionManager().getParams().setSoTimeout(4000);
				    client.executeMethod(get);
				    String body=get.getResponseBodyAsString();	
				    //System.out.println(body);
				    if(get.getStatusLine().getStatusCode()==200 && body.indexOf("hao123")>0){
				    	flag=false;
						break;
				    }
				    get.releaseConnection();
				    
				}catch(Exception o){
					o.printStackTrace();
				}
			   
			   if(ipplace>(ar.size()-1)){
				   ipplace=0;  
			   }
			   System.out.println("ipplace"+ ipplace+" ar:"+ar.size());
			   ip=ar.get(ipplace);
		   }
		   
		   

//		   while(iptmp.equals(ip)){
//			   System.out.println("变换IP"+ip.getIp());
//			   ip=ar.get( Math.abs(new Random().nextInt())%ar.size());
//		   }
//		   if(ipnum%100==25){
//			   ip=new ProxyIP();
//		   }
		}catch(Exception o){
		
			o.printStackTrace();
		}
		
		return  ip;
	}
	
    @Override
    public String proxy() {
        //String[] proxies = new String[]{"http://222.45.233.2:3128","http://119.6.136.122:80","http://122.96.59.104:80","http://39.1.42.86:8080" };
    	
        //String[] proxies = new String[]{"http://39.1.36.117:8080","http://82.48.113.11:8088","http://183.61.236.54:3128","http://222.170.17.74:2226","http://119.6.136.122:80","http://103.247.159.106:8080","http://122.96.59.104:80","http://39.1.42.86:8080" };
        //return proxies[RandomUtils.nextInt()%proxies.length];
    	ProxyIP iptmp =getProxyIp();
    	System.out.println("http://"+iptmp.getIp()+":"+iptmp.getPort());
    	//return  "http://123.56.139.108:8123"; 
    	return  "http://"+iptmp.getIp()+":"+iptmp.getPort(); 
    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        for(int j=34 ;j<38;j++){
	    	Calendar cal=Calendar.getInstance();
	    	SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");   
	    	cal.set(Calendar.YEAR,2016);
	    	cal.set(Calendar.MONTH,8);
	    	cal.set(Calendar.DAY_OF_MONTH,1);
	    	cal.add(Calendar.DATE, -7*j);
    		String bstr=myFmt1.format(cal.getTime()).replaceAll("-", "%3A");   		
    		cal.add(Calendar.DATE, -6);  		
    		String estr=myFmt1.format(cal.getTime()).replaceAll("-", "%3A"); ;
    		System.out.println(bstr);
    		System.out.println(estr);
	        try {
	        	     
	            //for (long i=100;i<102;i++){
	            //for (long i=1;i<108;i++){
	            	//System.out.println(i);
	        	   Map<String,String> map= new HashMap();
	        	   map.put("url", "http://search.ccgp.gov.cn/dataB.jsp?searchtype=2&bidSort=0&buyerName=&projectId=&pinMu=0&bidType=0&dbselect=bidx&kw=&start_time="+estr+"&end_time="+bstr+"&timeType=0&displayZone=&zoneId=&pppStatus=&agentName=&&page_index=");
	        	   Map<String,String> map1= new HashMap();
	        	   map1.put("Proxy-Authorization",getSyncIp());
	        	   push(new Request("http://search.ccgp.gov.cn/dataB.jsp?searchtype=2&bidSort=0&buyerName=&projectId=&pinMu=0&bidType=0&dbselect=bidx&kw=&start_time="+estr+"&end_time="+bstr+"&timeType=0&displayZone=&zoneId=&pppStatus=&agentName=&&page_index=1","getPage").setTrans(map).setHeader(map1));
	               //TimeUnit.MILLISECONDS.sleep(40000000);
	               //}
	        } catch (Exception e) {
	        	System.out.println("异常了");
	            e.printStackTrace();
	        }
        }   
    }
    public void getPage(Response response){
    	String  str=response.getContent();
    	
    	//response.getHttpResponse().getFirstHeader("")
    	//System.out.println("***"+response.getRealUrl());
    	//System.out.println("***"+response.getContent());
    	String  purl=response.getTrans().get("url");
    	System.out.println("page:"+response.getHttpResponse().getStatusLine());
        JXDocument doc = response.document();
        //System.out.println(str);
        try {
        
            //List<Object> urls = doc.sel("//ul[@class='ul_news_long']//a/@href");
        	//<div style="font-size:14px"><p style="float:left">关键字：<span
        
            List<Object> urls = doc.sel("//div[@style='font-size:14px']/p[@style='float:left']/span/text()"); 
            //List<Object> urls = doc.sel("//ul[@id='J_PicMode']/text()"); 
            //System.out.println(urls.size());
            Map<String,String> map1= new HashMap();
     	    map1.put("Proxy-Authorization",getSyncIp());
            for (long i=1;i<Integer.parseInt(urls.get(1).toString())/20+2;i++){
            //for (long i=1;i<3;i++){	
            	   System.out.println("!!!!!!!!!!!!!!!!!!!"+i+":"+urls.get(1));
	               push(new Request(purl+i,"getUrls").setSkipDuplicateFilter(true).setHeader(map1));
	               //TimeUnit.MILLISECONDS.sleep(30000);
                 //System.out.println(url.toString());
                 // push(new Request("http://detail.zol.com.cn/"+url.toString(),"getData"));
            	//System.out.println(response.getRealUrl());
  	         }
        } catch (Exception e) {
        	System.out.println("page"+str);
            e.printStackTrace();
        }
    }
    public void getUrls(Response response){
    	String  str=response.getContent();
       	System.out.println("***"+response.getRealUrl());
    	System.out.println("url:"+response.getHttpResponse().getStatusLine());

    	if(str!=null && str.length()<2000)
    	    System.out.println(str);
    	//response.getHttpResponse().getFirstHeader("")
 
    	//System.out.println("***"+response.getContent());
        JXDocument doc = response.document();
        //System.out.println(doc.);
        try {
        
            List<Object> urls = doc.sel("//ul[@class='vT-srch-result-list-bid']/li/a/@href"); 
            //List<Object> urls = doc.sel("//ul[@id='J_PicMode']/text()"); 
            System.out.println(urls.size());
            Map<String,String> header =new HashMap();
            header.put("Accept-Language", "zh-CN,zh;");
            //Map<String,String> map1= new HashMap();
            header.put("Proxy-Authorization",getSyncIp());
            for(Object  url:urls){    
            	 
                 System.out.println(url.toString());               
                 //push(new Request(url.toString(),"getData").setHeader(header));
                 
                 fileurl.write(url.toString()+"\n");
                 fileurl.flush();
                 //TimeUnit.MILLISECONDS.sleep(32000);
                 //push(new Request("http://www.ccgp.gov.cn/cggg/dfgg/gkzb/201609/t20160918_7324648.htm","getData"));
            	//System.out.println(response.getRealUrl());
  	         }
        } catch (Exception e) {
        	System.out.println("page!!!");
            e.printStackTrace();
            System.out.println(str);
        }
    }
    public void getData(Response response){
    	
    	System.out.println("data:"+response.getHttpResponse().getStatusLine());

    	String  url=response.getRealUrl();
    	if(response.getHttpResponse().getStatusLine().toString().indexOf("500")>0)
    		return;
    	System.out.println(response.getCharset());
//    	if(!response.getCharset().equals("utf-8"));	  
//    	    response.setCharset("utf-8");
    	String  str=response.getContent();
    	//System.out.println(str);
    	JXDocument doc = response.document();	
    	try{
    		List<Object> title = doc.sel("//div[@class='vT_detail_header']/h2/text()"); 
	    	List<Object> times = doc.sel("//div[@class='vT_detail_header']/p/span[@id='pubTime']/text()"); 
		   	List<Object> detail = doc.sel("//div[@class='vT_detail_content w760c']/node()"); 
	    	System.out.println("****"+title.get(0));
	    	System.out.println("*****"+response.getRealUrl());
	    	System.out.println("****"+times.get(0));	    	
	    	String content="";
            int  n=0;
            boolean flag=false;
            for(Object ob : detail){        	
            	Element el=(Element)  ob;           	
            	if(el.tagName().equals("div")||el.tagName().equals("p")||el.tagName().equals("table")){          		
            		flag=true;        		
            		String table="\003";
            		if(el.tagName().equals("table")){
            			Elements els =el.getElementsByTag("tr");
            			for(Element tr : els){
            				Elements tds =tr.getElementsByTag("td");
            				String trt="";
            				for(Element td : tds){
            					trt =trt+ td.text()+ "\005";
            					//System.out.println(trt);
            				}
            				table=table+trt+"\004";
            				//System.out.println(table);
            			}
            			content=content+table+"\006";
            		}else{	    
            			content=content+"\002"+el.text();
            			//doc.sel("//div[@class='vT_detail_content w760c']/p);
            		    //content=content+"\002"+(el.html().replace("<STYLE>.*<\\/STYLE>", "").replaceAll("<[^>]+>", ""));
            		    //System.out.println("&&***********************"+el.html().replaceAll("<[^>]+>", ""));
            		    //System.out.println("&&***********************"+el.html());
            		}
            	}
	            		            	
	        }
            if(flag==false||content.replaceAll("\002", "").equals("")){
            	//System.out.println("$$$$");
            	List<Object> text = doc.sel("//div[@class='vT_detail_content w760c']/allText()");
            	content=text.get(0).toString();
            }
            String header="";
            List<Object> head = doc.sel("//div[@class='vT_detail_nav_lks']/allText()");
            header=head.get(0).toString();
            System.out.println(header);
            String type=header.split("»")[2];
	    	System.out.println("****!!!"+content);
	    	List<Object> simple = doc.sel("//div[@class='table']/table/tbody/node()"); 
	    	Map<String ,String> map=new HashMap();
	    	for(Object  child:simple){	            	
	            Element el=(Element)  child;
	            //System.out.println(el.tagName());
	            if(el.tagName().equals("tr")){
	            	Elements els= el.getElementsByTag("td");
	            	//System.out.println("@@@"+els.text());
	            	if(els.size()>1){
	            		map.put(els.get(0).text(), els.get(1).text());	            		
	            	}
	            	if(els.size()>3){
	            		map.put(els.get(2).text(), els.get(3).text());	            		
	            	}
	            }
	    	}
//	    	for(String key:map.keySet()){
//	    		System.out.println(key +":"+map.get(key));
//	    	}
	    	filew.write(title.get(0)+"\001"+times.get(0)+"\001"+url+"\001"+header+"\001"+type.replaceAll(" ", "")+"\001"+content+"\001"
	    	+map.get("采购项目名称")+"\001"+map.get("品目")+"\001"+map.get("采购单位")+"\001"+map.get("行政区域")+"\001"+map.get("公告时间")+"\001"
	    	+map.get("本项目招标公告日期")+"\001"+map.get("中标日期")+"\001"+map.get("评审专家名单")+"\001"+map.get("总中标金额")+"\001"
	    	+map.get("获取招标文件时间")+"\001"+map.get("招标文件售价")+"\001"+map.get("获取招标文件的地点")+"\001"
	    	+map.get("开标时间")+"\001"+map.get("开标地点")+"\001"+map.get("预算金额")+"\001"+map.get("项目联系人")+"\001"
	    	+map.get("项目联系电话")+"\001"+map.get("采购单位")+"\001"+map.get("采购单位地址")+"\001"+map.get("采购单位联系方式")+"\001"
	    	+map.get("代理机构名称")+"\001"+map.get("代理机构地址")+"\001"+map.get("代理机构联系方式")+"\n");
            //System.out.println(config.size());
	      	//filew.write(name.get(0)+"\001"+strAS+"\001"+priceStr+"\n\r");
            
	      	//filew.write(name.get(0)+"\001"+nameAs.get(0)+"\001"+price.get(0)+"\001"+config.get(0)+"\001"+config.get(1)+"\001"+config.get(2)+"\001"
    		//	    +config.get(3)+"\001"+config.get(4)+"\001"+config.get(5)+"\001"+config.get(6)+"\001"+config.get(7)+"\n\r");
    	    filew.flush();

    	}catch(Exception o){
    		System.out.println("data"+str);
    		o.printStackTrace();
    	}
      	//<span id="J_PriceTrend">
    	//<div class="page-title clearfix">

    	//System.out.println(jsonString);
    }
//  @Scheduled(cron = "0 18 14 10 * ?")
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