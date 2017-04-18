package com.kaola.esfirst;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;

public class PostES_court_notice {
	
	private static BufferedWriter file = null;
	private static BufferedReader fileReader = null;
	
	public PostES_court_notice(){
		try {
			file = new BufferedWriter(new FileWriter("E:/data/es/gonggao.txt", false));
			fileReader = new BufferedReader(new FileReader("E:/data/es/gonggao_read.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public static void main(String[] args) throws Exception {
		PostES_court_notice p= new PostES_court_notice();
		HttpClient client = new HttpClient();
		//String url ="http://10.1.80.181:9200//law_court/court_notice/_search";
		//String url ="http://10.1.80.181:9200/law_judgement/judgement/_search";
		String url ="http://10.1.80.181:9200/law_court/court_notice/_search";
		

		String formData = "{\"query\":{\"bool\":{\"must\":["
				+ "{\"multi_match\":{\"query\":\"袁园\",\"type\":\"phrase\",\"fields\":[\"party\"]}}"
				+ ",{\"term\":{\"notice_user_name\":\"重庆\"}}"
				+ "]}}}";
		
		String line;
		while((line = fileReader.readLine()) !=null && line.trim().length()>0){
			PostMethod method = new PostMethod(url);
			String[] strs = line.split("	");
			formData = "{\"query\":{\"bool\":{\"must\":["
					+ "{\"multi_match\":{\"query\":\""+strs[2].trim()+"\",\"type\":\"phrase\",\"fields\":[\"party\"]}}"
					+ ",{\"term\":{\"notice_user_name\":\""+strs[0].trim()+"\"}}"
					+ "]}}}";
			
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
			
			String tmp = strs[0]+"	"+ strs[1]+"	"+ strs[2];
			
			if(hits.size() > 0){
				for (int i = 0; i < hits.size(); i++) {
					JSONObject jb = hits.getJSONObject(i).getJSONObject("_source").getJSONObject("body");
					String party = jb.getString("party");
					String notice_type = jb.getString("notice_type");  //前多一个回车
					String notice_user_name = jb.getString("notice_user_name");
					String publish_time = jb.getString("publish_time");
					String content = jb.getString("content").substring(0,30)+"...";
					
					String tt=tmp + "	idtype"+"	"+"id"+"	"+notice_type.trim()+"	"+notice_user_name+"	"+publish_time+"	"+content;
					file.write(tt+"\n");
					System.out.println(tt); 
				}
				 
			}else{
				file.write(tmp+"\n");
				System.out.println(tmp);  
			}
			
			file.flush();
		    method.releaseConnection();
		}
	
		
	}

}
