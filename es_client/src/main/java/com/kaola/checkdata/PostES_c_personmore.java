package com.kaola.checkdata;

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

public class PostES_c_personmore {
	
	private static BufferedWriter file = null;
	private static BufferedReader fileReader = null;
	
	public static void PostES_Init(){
		try {
			file = new BufferedWriter(new FileWriter("E:/data/es/c_personmore_read_shenfutong_write2.txt", false));
			fileReader = new BufferedReader(new FileReader("E:/data/es/c_personmore_read_shenfutong.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public static void main(String[] args) throws Exception {
		PostES_Init();
		HttpClient client = new HttpClient();
		String url ="http://10.1.80.181:9200/law_personmore/c_personmore/_search";
		

		String formData = "{\"query\":{\"bool\":{\"must\":["
				+ "{\"query_string\":{\"default_field\":\"name\",\"query\":\"柯连技\"}}"
				+ ",{\"prefix\":{\"cardnum\":\"4202221987\"}}"
				+ "]}}}";
		
		String line;
		while((line = fileReader.readLine()) !=null && line.trim().length()>0){
			PostMethod method = new PostMethod(url);
			String[] strs = line.split("	");
			formData = "{\"query\":{\"bool\":{\"must\":["
					+ "{\"query_string\":{\"default_field\":\"name\",\"query\":\""+strs[0]+"\"}}"
					+ ",{\"query_string\":{\"default_field\":\"cardnum\",\"query\":\""+strs[1].substring(14)+"\"}}"
					+ ",{\"prefix\":{\"cardnum\":\""+strs[1].substring(0,10)+"\"}}"
					+ "]}}}";
			System.out.println(strs[1].substring(0,10));
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
			
			String tmp = strs[0]+"	"+ strs[1];
			
			if(hits.size() > 0){
				for (int i = 0; i < hits.size(); i++) {
					JSONObject jb = hits.getJSONObject(i).getJSONObject("_source").getJSONObject("body");
					String cardnum = jb.getString("cardnum");  
					String casecode = jb.getString("casecode");
					String courtname = jb.getString("courtname");
					String regdate = jb.getString("regdate");
					String pulishdate = jb.getString("pulishdate");
					
					String duty = jb.getString("duty");					
					String performance = jb.getString("performance");
					
					
					
					
					String tt=tmp + "	"+cardnum+"	"+casecode+"	"+courtname+"	"+regdate+"	"+pulishdate+"	"+duty+"	"+performance;
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
