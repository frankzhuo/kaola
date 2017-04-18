package com.kaola.hive.udf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

public class HbaseTestHttpClient {
	/**
	 * curl -H "Accept:application/json" http://10.1.80.172:20550/lklpos_atm_risk_control_new/a4bacf9add59c1f31c87a1b0eb3b229f_detail_*\/cf:col/1449849600000,1481472000000
	 * @param args
	 */
	
	public static void main(String[] args) {
		    HttpClient client1 = new HttpClient(); 
		    client1.getHttpConnectionManager().getParams().setConnectionTimeout(40000);
		    client1.getHttpConnectionManager().getParams().setSoTimeout(40000);
		    GetMethod method1 = new GetMethod("http://10.1.80.172:20550/lklpos_atm_risk_control/55e567e4216f38a37bb4b503b538632b_detail_*/cf:col/1451145600000,1482768000000"); 
		    method1.setRequestHeader("Accept","application/json");
		    try{
		    	BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("C:/Users/kaola/Desktop/822100050392640_detail_old.csv")));
		    	client1.executeMethod(method1);  
		    	byte[] bt =method1.getResponseBody();
		    	
		   
		    	String re = new String(bt);
		     	if(re.contains("Not found"))
		    	{
		    		System.out.println("-----------没有数据---------------");
		    		System.exit(-1);
		    	}
		    	System.out.println(new String(re));
		    	 
		    	JSONObject json = JSONObject.fromObject(re);
		    	JSONArray rows = json.getJSONArray("Row");
		    	
		    	for (int i = 0; i < rows.size(); i++) {
		    		JSONArray tmp = rows.getJSONObject(i).getJSONArray("Cell");
		    		String rr  = new String(new BASE64Decoder().decodeBuffer(((JSONObject)tmp.get(0)).getString("$")));
		    		System.out.println(rr);
		    		bufferwrite.write(rr+"\n");
				}
		    	bufferwrite.flush();
		    }catch(Exception o){
		    	o.printStackTrace();
		    }
	}
	  
}
