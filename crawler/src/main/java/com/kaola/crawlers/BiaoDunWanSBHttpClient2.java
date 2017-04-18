package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 商标爬取地址（商盾网）：http://www.shangdun.org/search/ 
 * 
 */

public class BiaoDunWanSBHttpClient2{

	private static BufferedReader fileReader = null;
	private static BufferedWriter fileWriter = null;

	public static void main(String[] args) {
		
		String line;
		try {
			fileReader = new BufferedReader(new FileReader("/data/crawler/shangbiao/companyofshangbiao2.txt"));
			fileWriter = new BufferedWriter(new FileWriter("/data/crawler/shangbiao/shangbiao2.txt", true));
			
			while((line = fileReader.readLine()) !=null && line.trim().length()>0){
				String ZHcx = line.trim();
				int PG2 = 1; //第一页
				String result = requestListUrl(ZHcx, PG2);
				
				int pageNum = getListTotal(result);
				
				System.out.println(ZHcx+" have " +pageNum+" pages");
				
				try{
					if(pageNum > 0){
						releaseList(result);
						int size = 40;
						int pages = ((pageNum-1)/ size) +1;
						for(int page=2;page<pages;page++){
							result = requestListUrl(ZHcx, page);
							releaseList(result);
						}
					}
				}
				catch(Exception e){
					continue;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	 }

    //httpclient 请求
    public static String requestDetailUrl(String NowIdOn,String NowCLOn){
    	String  result = "";
    	HttpClient client = new HttpClient(); 
	    client.getHostConfiguration().setProxy("123.56.139.108", 8123);
 	    client.getParams().setContentCharset("gb2312");
 	    PostMethod method = new PostMethod("http://www.shangdun.org/show/"); 
		try {
				
				method.setRequestHeader("Proxy-Authorization",getSyncIp());
				method.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				method.setRequestHeader("Accept-Encoding","gzip, deflate");
			
				method.setRequestHeader("Cache-Control","max-age=0");
				method.setRequestHeader("Connection","keep-alive");
				method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				//method.setRequestHeader("Cookie","ASPSESSIONIDSQBBSADA=BNDMLCEBGKIHMOLJEEDIMEGA; td_cookie=678959325; CNZZDATA5381036=cnzz_eid%3D1369700558-1487575124-http%253A%252F%252Fwww.shangdun.org%252F%26ntime%3D1487638377");
				method.setRequestHeader("Host","www.shangdun.org");
				method.setRequestHeader("Origin","http://www.shangdun.org");
				method.setRequestHeader("Referer","http://www.shangdun.org/search/");
				method.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
				
				NameValuePair[] data = {
						new NameValuePair("NowIdOn", NowIdOn),
						new NameValuePair("NowCLOn", NowCLOn)					
				};
				method.setRequestBody(data);
				
				client.executeMethod(method);  
				
				System.out.println("requestDetailUrl---status:"+method.getStatusLine()+ " statusCode:"+method.getStatusLine().getStatusCode());  
				
				byte[] bt  = method.getResponseBody();
				result  = new String(bt,"gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			method.releaseConnection(); 
		}
		return result;
    }
    
    //httpclient 请求
    public static String requestListUrl(String ZHcx,int PG2){
    	String  result = "";
    	HttpClient client = new HttpClient(); 
	    client.getHostConfiguration().setProxy("123.56.139.108", 8123);
 	    client.getParams().setContentCharset("gb2312");
 	    PostMethod method = new PostMethod("http://www.shangdun.org/search/"); 
		try {
				
				method.setRequestHeader("Proxy-Authorization",getSyncIp());
				method.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				method.setRequestHeader("Accept-Encoding","gzip, deflate");
			
				method.setRequestHeader("Cache-Control","max-age=0");
				method.setRequestHeader("Connection","keep-alive");
				method.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
				//method.setRequestHeader("Cookie","ASPSESSIONIDSQBBSADA=BNDMLCEBGKIHMOLJEEDIMEGA; td_cookie=678959325; CNZZDATA5381036=cnzz_eid%3D1369700558-1487575124-http%253A%252F%252Fwww.shangdun.org%252F%26ntime%3D1487638377");
				method.setRequestHeader("Host","www.shangdun.org");
				method.setRequestHeader("Origin","http://www.shangdun.org");
				method.setRequestHeader("Referer","http://www.shangdun.org/search/");
				method.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
				
				NameValuePair[] data = {
						new NameValuePair("ZHcx", ZHcx),
						new NameValuePair("Md2", "申请人"),
						new NameValuePair("PG2", PG2+""),
						new NameValuePair("PGt", "2"),
						new NameValuePair("ProcChgDate", ""),
						new NameValuePair("XZDate", ""),
						new NameValuePair("ProcInfoZH", ""),
						new NameValuePair("ShowstyZH", "4"),
						new NameValuePair("ClassNo", "")					
				};
				method.setRequestBody(data);
				
				client.executeMethod(method);  
				
				System.out.println("requestListUrl---status:"+method.getStatusLine()+ " statusCode:"+method.getStatusLine().getStatusCode());  
				
				byte[] bt  = method.getResponseBody();
				result  = new String(bt,"gb2312");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			method.releaseConnection(); 
		}
		return result;
    }
    
    //获取页面总记录数
    public static int getListTotal(String result){
    	String reg = "<a href='#tolsall' name='tolsall'><u>共(\\d+)条</u></a>";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(result);
		int pageNum = 0;
		while(mat.find()){
			pageNum = Integer.parseInt(mat.group(1));
			break;
		}
		return pageNum;
    }
    
    //解析列表中的，注册号和分类号
    public static void releaseList(String result){
    	String reg = "img.shangdun.org/Img.asp\\?R=(\\d+)&T=(\\d+)";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(result);
		
		try {
			while(mat.find()){
				System.out.println("applyNum :"+mat.group(1)+"---classNum :"+mat.group(2));
				String NowIdOn = mat.group(1);
				String NowCLOn = mat.group(2);	
				
				Thread.sleep(5*1000); //休眠5秒
				
				String detailStr  = requestDetailUrl(NowIdOn, NowCLOn); //请求商标详情
	           
				try{
					releaseDetail(detailStr); //解析商标详情
				}catch(Exception ex){
					continue;
				}
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
    }
    
    //解析列表中的，注册号和分类号
    public static void releaseDetail(String result){
    	Element e = Jsoup.parse(result);
    	Elements trs  =  e.getElementsByTag("tr");
    	
    	String temp  =  "";
    	try {
	    	for(int i = 0 ; i < trs.size();i++){
	    		if(i == 4){
	    			continue;
	    		}
	    		Element tr=  trs.get(i);
	    		Elements tds  =tr.getElementsByTag("td");
	    		if(i ==0){ //商标名称
	    			String shangbiaoname =  tds.get(1).text().trim();
	    			String zhucehao =  cleanStr(tds.get(3).text());
	    			temp = zhucehao +"\001" + shangbiaoname +"\001"; 
	    		}
	    		if(i ==1){//国际分类号
	    			String guojitypenum =  cleanStr(tds.get(1).text());
	    			String applyDate =  cleanStr(tds.get(3).text());
	    			temp += guojitypenum +"\001" + applyDate +"\001" ; 
	    		}
	    		if(i ==2){//申请人名称(中文)
	    			String applyPersonZhName =  cleanStr(tds.get(1).text());
	    			String applyZhAddress =  cleanStr(tds.get(3).text());
	    			temp += applyPersonZhName +"\001" + applyZhAddress +"\001" ; 
	    		}
	    		if(i ==3){//申请人名称(英文)
	    			String applyPersonEnName =  cleanStr(tds.get(1).text());
	    			String applyEnAddress =  cleanStr(tds.get(3).text());
	    			temp += applyPersonEnName +"\001" + applyEnAddress +"\001" ; 
	    		}
	    		
	    		if(i ==4){ //商标图像 :多余
	    			
	    		}
	    		
	    		if(i ==5){ //商标图像
	    			String shangbiaoPicName =  cleanStr(tds.get(1).children().get(0).attr("href")).replace("../Img", "http://www.shangdun.org/Img");
	    			String shangbiaoList =  cleanStr(tds.get(5).text());
	    			temp += shangbiaoPicName +"\001" + shangbiaoList +"\001" ; 
	    		}
	    		
	    		
	    		if(i ==6){//初审公告期号
	    			String chushenDateNum =  cleanStr(tds.get(1).text());
	    			String zhuceDateNum =  cleanStr(tds.get(3).text());
	    			temp += chushenDateNum +"\001" + zhuceDateNum +"\001" ; 
	    		}
	    		if(i ==7){ //初审公告日期
	    			String chushenDate =  cleanStr(tds.get(1).text());
	    			String zhuceDate =  cleanStr(tds.get(3).text());
	    			temp += chushenDate +"\001" + zhuceDate +"\001" ; 
	    		}
	    		if(i ==8){ //专用权期限
	    			String zhuanyongLimit =  cleanStr(tds.get(1).text());
	    			temp += zhuanyongLimit +"\001" ; 
	    		}
	    		if(i ==9){ //后期指定日期
	    			String houqiZhidingDate =  cleanStr(tds.get(1).text());
	    			String guojiZhuceDate =  cleanStr(tds.get(3).text());
	    			temp += houqiZhidingDate +"\001" + guojiZhuceDate +"\001" ; 
	    		}
	    		if(i ==10){ //优先权日期
	    			String youxianQuanDate =  cleanStr(tds.get(1).text());
	    			String agentName =  cleanStr(tds.get(3).text());
	    			temp += youxianQuanDate +"\001" + agentName +"\001" ; 
	    		}
	    		if(i ==11){ //指定颜色
	    			String zhidingColor =  cleanStr(tds.get(1).text());
	    			String shangbiaoType =  cleanStr(tds.get(3).text());
	    			temp += zhidingColor +"\001" + shangbiaoType +"\001" ; 
	    		}
	    		if(i ==12){ //是否共有商标
	    			String isShare =  cleanStr(tds.get(1).text());
	    			String note =  cleanStr(tds.get(3).text());
	    			temp += isShare +"\001" +(note.length() > 0?note:"-"); 
	    		}
	    		if(i ==13){ //商标流程
	    			//这个ajax请求的，待采集完后同一爬取
	    			//http://www.shangdun.org/getTMproc.asp?R=12526418&L=16
	    			//String flow =  tds.get(1).text();
	    		}
	    	}
	    	temp= temp +"\n";
	    	System.out.println(temp);
			fileWriter.write(temp);
			fileWriter.flush();
		} catch (IOException e1) {
			//e1.printStackTrace();
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

	//清楚格式
	public static String cleanStr(String str){
		if(str != null){
			str  = str.trim().replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
		}
		return str;		
	}
	
}
