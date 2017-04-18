package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import cn.wanghaomiao.xpath.model.JXDocument;


/**
 * 研发购买的代理ip
 * 为了多线程爬去数据，而不被封ip
 * 同一个ip请求多次，会导致返回超时
 * 
 * 
 * 需要用研发代理ip时 告知周勇（抱备），允许并发数是30
 * 用框架代理不能用
 */
public class DianPingShangHuValidIdDetailHttpClient{

	private static BufferedReader fileReader = null;
	private static BufferedWriter fileWriter = null;
	private static BufferedWriter fileWriter_no = null;
	

	public static void main(String[] args) {
		String line;
		HttpClient client = new HttpClient(); 
	    client.getHostConfiguration().setProxy("123.56.139.108", 8123);
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
	    client.getHttpConnectionManager().getParams().setSoTimeout(10000);
		try {
			fileReader = new BufferedReader(new FileReader("/data/crawler/dianping/valid/dianpingshanghuid3.txt"));
			fileWriter = new BufferedWriter(new FileWriter("/data/crawler/dianping/valid/dianpingshanghuidDetail3.txt", true));
			fileWriter_no = new BufferedWriter(new FileWriter("/data/crawler/dianping/valid/dianpingshanghuidDetail_no.txt", true));
			while((line = fileReader.readLine()) !=null){
				Thread.sleep(5*1000);
				try{
					String url = "http://www.dianping.com/shop/"+line.trim();
					PostMethod method = new PostMethod(url); 
					method.setRequestHeader("Proxy-Authorization",getSyncIp());
					method.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
					method.setRequestHeader("Accept-Encoding","UTF-8"); //必须是utf8
					method.setRequestHeader("Accept-Language","zh-CN,zh;q=0.8");
					method.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
					method.setRequestHeader("Host","www.dianping.com");
					method.setRequestHeader("Cache-Control","max-age=0");
					method.setRequestHeader("Proxy-Connection","keep-alive");
					
					StringBuffer sbf = new StringBuffer();
				
					client.executeMethod(method);  
					
					System.out.println(method.getURI());
					
					InputStream in = method.getResponseBodyAsStream();
					if(in == null){
						continue;
					}
					BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					while((line =bf.readLine()) != null){
						sbf.append(line);
					}
					callback(sbf.toString(),url);
				}catch(Exception e){
					e.printStackTrace();
					continue; //防止抛错导致程序退出
				}
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
			
	 }

    
	 /**
     * UA库可以自行查找整理，这里仅为样例
     * @return
     */
    public String getUserAgent(){
        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
        };
        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
    }
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public static void callback(String html,String url) {
		JXDocument doc =new JXDocument(html);
		try {
			
			//商户id
			String shanghuiID= url.substring(url.lastIndexOf("/")+1);
			System.out.println("url:"+url+"------>"+shanghuiID);
			
			//城市
			List<Object> city = doc.sel("//a[@class='city J-city']/text()");
			String cityString="";
			if(city.size()>0){
				cityString = (String)city.get(0);
			}
			
			//导航地址
			List<Object> daohang = doc.sel("//div[@class='breadcrumb']/allText()");
			String daohangString="";
			if(daohang.size()>0){
				daohangString = (String)daohang.get(0);
			}
			
			//商户名称
			List<Object> shopname = doc.sel("//div[@id='basic-info']/h1[@class='shop-name']/text()");
			String shopnameString="";
			if(shopname.size()>0){
				shopnameString = (String)shopname.get(0);
			}

			//商户评价
			List<Object> pingjia = doc.sel("//div[@id='basic-info']/div[@class='brief-info']/span[first()]/@title");
			List<Object> briefinfo = doc.sel("//div[@id='basic-info']/div[@class='brief-info']/span[@class='item']/text()");
			String pingjiaString = (String) (pingjia.size()>0?pingjia.get(0):"");
			for (int i = 0; i < briefinfo.size(); i++) {
				pingjiaString = pingjiaString +"\002"+briefinfo.get(i);
			}
			
			//地址信息			
			List<Object> address = doc.sel("//div[@id='basic-info']/div[@class*='address']/span[@class='item']/text()");
			String addressString =(String) (address.size()>0?address.get(0):"");
			
			
			//电话，联系方式
			List<Object> telphone = doc.sel("//div[@id='basic-info']/p[@class*='tel']/span[last()]/text()");
			String telphoneString =(String) (telphone.size()>0?telphone.get(0):"");
 
			//其它补充说明
			List<Object> other = doc.sel("//div[@id='basic-info']/div[@class*='J-other']/p[@class^='info info-indent']/allText()");
			String otherString = "-";
			for (int i = 0; i < other.size(); i++) {
				if(i == 0 ){
					otherString = other.get(i) +"";
					continue;
				}
				otherString = otherString +"\002"+other.get(i);
			}
			
			String Str = shanghuiID + "\001" +cityString +"\001"+daohangString+"\001"+shopnameString+"\001"+pingjiaString+"\001"+addressString+"\001"+telphoneString+"\001"+otherString+"\n";
			System.out.println(Str);
			
			if("".equals(cityString) && "".equals(daohangString)){
				fileWriter_no.write(Str);
				fileWriter_no.flush();
			}else{
				fileWriter.write(Str);
				fileWriter.flush();
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
	//Inner class for UTF-8 support     
//	public static class UTF8PostMethod extends PostMethod{     
//	    public UTF8PostMethod(String url){     
//	    super(url);     
//	    }     
//	    @Override     
//	    public String getRequestCharSet() {     
//	        //return super.getRequestCharSet();     
//	        return "UTF-8";     
//	    }  
//	} 

}
