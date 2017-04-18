package com.kaola.esfirst;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;

public class First_ES_Post_Or {
	public static void main(String[] args) throws Exception {
		HttpClient client = new HttpClient();
		//String url ="http://10.5.28.13:9200/law_shixin/_search";
		String url ="http://10.1.80.181:9200/law_shixin/_search";
		
		PostMethod method = new PostMethod(url);
		
		String formData = "{\"query\":{\"bool\":{\"should\":[{\"bool\":{\"must\":[{\"term\":{\"name\":\"王宝兴\"}},{\"term\":{\"cardnum\":\"3307221969****2110\"}}]}},{\"term\":{\"businessentity\":\"王\"}}]}},\"sort\":{\"_type\":\"asc\",\"regdate\":\"desc\"}}";
		
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
	    
	    JSONObject json = JSONObject.fromObject(response).getJSONObject("hits");
		
		JSONArray hits = json.getJSONArray("hits");
		
		
		if(hits.size() > 0){
			for (int i = 0; i < hits.size(); i++) {
				JSONObject jb = hits.getJSONObject(i).getJSONObject("_source");
				String businessentity = "~~~";
//				System.out.println(hits.getJSONObject(i).getString("_type"));
				if(hits.getJSONObject(i).getString("_type").equals("c_unitmore_new")){
					 businessentity = jb.getString("businessentity");  
				}
				Object businessentity1 = jb.get("businessentity"); 
				String cid = jb.getString("cid");
				String cardnum = jb.getString("cardnum");
				String name = jb.getString("name"); 
				
 				String regdate = jb.getString("regdate");					
				String performance = jb.getString("performance");
				
				
				String tt= businessentity1+"	"+cid+"	"+cardnum+"	"+name+"	"+regdate+"	"+performance;
				System.out.println(tt); 
			}
			 
		}
	    
	    // 打印返回的信息  
	    System.out.println(response);  
	    method.releaseConnection();
		
	}

}
