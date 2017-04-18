package com.kaola.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Post {
	private static HttpMethod getPostMethod() {  
	    String url = "http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+2+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E6%B0%91%E4%BA%8B%E6%A1%88%E4%BB%B6";  
	    PostMethod post = new UTF8PostMethod(url);
        NameValuePair[] data = { 
      		//new NameValuePair("Param", "*"),  
				new NameValuePair("Param", "案件类型:民事案件,一级案由:民事案由,二级案由:劳动争议、人事争议"),
			    new NameValuePair("Index", "1"),
			    new NameValuePair("Page", "5"),
			    new NameValuePair("Order", "法院层级"),
			    new NameValuePair("Direction", "asc")   						
        };
	    //NameValuePair message = new NameValuePair("message", "消息内容。");  
	    post.setRequestBody(data);  
	    return post;  
	}  
	  
	//Inner class for UTF-8 support     
	public static class UTF8PostMethod extends PostMethod{     
	    public UTF8PostMethod(String url){     
	    super(url);     
	    }     
	    @Override     
	    public String getRequestCharSet() {     
	        //return super.getRequestCharSet();     
	        return "UTF-8";     
	    }  
	}  
	public static void main(String[] args) {
		HttpClient client = new HttpClient();  
		
	    //client.getHostConfiguration().setHost("127.0.0.1", 8081, "http");  
	    // 使用POST方式提交数据  
	    try{
		    HttpMethod method = getPostMethod();  
		    client.executeMethod(method);  
		    // 打印服务器返回的状态  
		    System.out.println(method.getStatusLine());  
		    // 打印结果页面  
		    String response = new String(method.getResponseBodyAsString());  
		    // 打印返回的信息  
		    System.out.println(response);  
		    method.releaseConnection();  
	    }catch(Exception o){
	    	o.printStackTrace();
	    }

	}

}
