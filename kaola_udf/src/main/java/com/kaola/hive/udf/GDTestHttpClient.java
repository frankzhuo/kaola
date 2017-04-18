package com.kaola.hive.udf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GDTestHttpClient {
	/**
	 * 测试光大接口
	 * url：http://10.1.80.166:8080/gd?merid=822584055411973&type=1&starttime=2016-01-12&endtime=2016-12-26&page=2&size=100
	 * param：merid：商户id
	 * 		type：接口分类（1：内部数据；2：外部数据；3：风控数据）
	 * 		starttime：开始日期（格式：yyyy-MM-dd）
	 * 		endtime：截至时间（格式：yyyy-MM-dd）
	 * 		page：页码（从0开始）
	 * 		size：每页大小
	 * 
	 */
	public static void main(String[] args) {
		    HttpClient client = new HttpClient(); 
		    client.getHttpConnectionManager().getParams().setConnectionTimeout(40000);
		    client.getHttpConnectionManager().getParams().setSoTimeout(40000);
		    String merid = "822584055411973";
		    String type = "1";
		    String starttime = "2016-01-12";
		    String endtime = "2016-12-26";
		    int page = 0; //从0开始
		    int size = 600;
		    int maxSize = 500 ;
		    if(size > maxSize){
		    	size = maxSize;
		    }
		    changeData(client, merid, type, starttime, endtime, page, size);
	}
	
	
	public static void changeData( HttpClient client,String merid,String type,String starttime,String endtime,int page,int size){
		    String url = "http://10.1.80.166:8080/gd?merid="+merid+"&type="+type+"&starttime="+starttime+"&endtime="+endtime+"&page="+page+"&size="+size;
		    GetMethod method1 = new GetMethod(url); 
		    method1.setRequestHeader("Accept","application/json");
		    try{
		    	BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("C:/Users/kaola/Desktop/822584055411973_1.csv"),true));
		    	client.executeMethod(method1);  
		    	byte[] bt =method1.getResponseBody();
		   
		    	String re = new String(bt);
		     	if(re.contains("Not found"))
		    	{
		    		System.out.println("-----------没有数据---------------");
		    		System.exit(-1);
		    	}
		    	System.out.println(new String(re));
		    	 
		    	JSONObject json = JSONObject.fromObject(re);
		    	JSONArray result = json.getJSONArray("result");
		    	int total = json.getInt("total");
		    	
		    	for (int i = 0; i < result.size(); i++) {
		    		String mer_id = result.getJSONObject(i).getString("mer_id");
		    		String card_type = result.getJSONObject(i).getString("card_type");
		    		String bank_short_name = result.getJSONObject(i).getString("bank_short_name");
		    		String trans_amt = result.getJSONObject(i).getString("trans_amt");
		    		String txn_data = result.getJSONObject(i).getString("txn_data");
		    		String ccycod = result.getJSONObject(i).getString("ccycod");
		    		String refund_identification = result.getJSONObject(i).getString("refund_identification");
		    		String log_no = result.getJSONObject(i).getString("log_no");
		    		String txn_sts = result.getJSONObject(i).getString("txn_sts");
		    		String txn_cd = result.getJSONObject(i).getString("txn_cd");
		    		String rr = mer_id+","+card_type+","+bank_short_name+","+trans_amt+","+txn_data+","+ccycod+","+refund_identification+","+log_no+","+txn_sts+","+txn_cd+"\n";
		    		System.out.println(rr);
		    		bufferwrite.write(rr);
				}
		    	
		    	int pages = ((total-1) / size)+1;
		    	page ++;
		    	System.out.println("pages+==============="+ pages);
		    	
		    	if(total > size	&& page < pages){
		    		System.out.println("page-----------"+ page);
		    		changeData(client, merid, type, starttime, endtime, page, size);
		    	}
		    	bufferwrite.flush();
		    	
		    }catch(Exception o){
		    	o.printStackTrace();
		    }
	}
	  
}
