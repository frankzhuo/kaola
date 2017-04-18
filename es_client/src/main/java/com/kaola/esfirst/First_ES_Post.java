package com.kaola.esfirst;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.HttpClient;

public class First_ES_Post {
	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		String url ="http://10.5.28.13:9200/procure/procure_proxy_org/_search";
		PostMethod method = new PostMethod(url);
		
		String formData = "{\"query\":{\"bool\":{\"must\":[{\"multi_match\":{\"query\":\"深圳市建星项目管理顾问有限公司\",\"type\":\"phrase\",\"fields\":[\"agentNm\"]}}]}}}";
		
		//method.addParameter("data",formData);
		RequestEntity entry = new StringRequestEntity(formData, "application/json", "UTF-8");
		method.setRequestEntity(entry);
		
		HttpMethodParams param= method.getParams();
		param.setContentCharset("UTF-8");
		client.executeMethod(method);
		
		
		System.out.println(formData);
		System.out.println(method.getStatusLine());
		System.out.println(method.getPath());
		
		// 打印结果页面  
	    String response = new String(method.getResponseBodyAsString());  
	    // 打印返回的信息  
	    System.out.println(response);  
	    method.releaseConnection();
		
	}

}
