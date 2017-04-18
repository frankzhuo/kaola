package com.kaola.esfirst;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.InetAddress;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import net.sf.json.JSONObject;

public class ES_TransportClient_unitmore_export {
	
	 public Client client;
	 private static BufferedWriter fileWriter = null;
	 
	 
	 public void init() throws Exception
	 {
	  
		 fileWriter = new BufferedWriter(new FileWriter("E:/data/c_unitmore.txt", false));
		 
		//version 1.7.1 in 正式网 
		 Settings settings = ImmutableSettings.settingsBuilder()
						.put("cluster.name", "edata")//设置集群名称
						 .put("client.transport.sniff", true)
						 .build();//自
		client = new TransportClient(settings)
				     .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.80.181"), Integer.parseInt("9300")));
		
		 System.out.println("init");   
	 }

	 public void close()
	 {
	     client.close();
	 }


	 public void search() {
	     SearchResponse response = client.prepareSearch("law_unitmore")
	             .setTypes("c_unitmore")
	             .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	             .setQuery(QueryBuilders.matchAllQuery()) 
	            // .setQuery(QueryBuilders.rangeQuery("sys_time").from("2016-02-27").includeLower(true))
	             .setSize(1000000).setScroll(new TimeValue(1000*20))
	             .setSearchType(SearchType.SCAN)
	             .execute().actionGet();
	     String scrollId = response.getScrollId();
	     
	     System.out.println("scrollid-------------"+scrollId);
	     
	     try{
	    	 while(true){
		    	 SearchResponse respose2 = client.prepareSearchScroll(scrollId).setScroll(new TimeValue(1000*20)).execute().actionGet();
		    	 SearchHits hits = respose2.getHits();
		    	 
		    	 if(hits.getHits().length == 0){
		    		 System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		    		 break;
		    	 }
		    	 
		    	 System.out.println(hits.getTotalHits());
		    	 System.out.println("查询数量是 "+ hits.getHits().length);
		    	 
		    	 for (int i = 0; i < hits.getHits().length; i++) {
		    		 String tmp = hits.getHits()[i].getSourceAsString();
		    		 JSONObject json = JSONObject.fromObject(tmp);
		    		 JSONObject body = json.getJSONObject("body");
		    		 
		    	     String id = cleanStr(body.getString("id"));
		    	     String cid = cleanStr(body.getString("cid"));
		    	     String name = cleanStr(body.getString("name"));
		    	     String casecode = cleanStr(body.getString("casecode"));
		    	     String businessentity = cleanStr(body.getString("businessentity"));
		    	     String cardnum = cleanStr(body.getString("cardnum"));
		    	     String courtname =cleanStr(body.getString("courtname"));
		    	     
		    	     String areaname =cleanStr(body.getString("areaname"));
		    	     String gistid =cleanStr(body.getString("gistid"));
		    	     String regdate =cleanStr(body.getString("regdate")).replace("年", "").replace("月", "").replace("日", "");
		    	     String gistunit =cleanStr(body.getString("gistunit"));
		    	     String duty =cleanStr(body.getString("duty"));
		    	     String performance =cleanStr(body.getString("performance"));
		    	     String disrupttypename =cleanStr(body.getString("disrupttypename"));
		    	     String pulishdate =cleanStr(body.getString("pulishdate"));
		    	     
		    		 String str = id +"\001"+cid +"\001"+name +"\001"+casecode +"\001"+businessentity+"\001"+cardnum +"\001"+courtname +"\001"
		    		 +areaname +"\001"+gistid +"\001"+regdate +"\001"+gistunit +"\001"+duty +"\001"+performance +"\001"+disrupttypename+"\001"+pulishdate +"\n";
			         System.out.println(i + "------"+ str);
			         
			         fileWriter.write(str);
			     }
		    	 System.out.println(hits.getTotalHits());
		    	 fileWriter.flush();
		     }
	     }
	     catch(Exception ex){
	    	ex.printStackTrace(); 
	     }
	     
	     
	 }    
	//清楚格式
	public static String cleanStr(String str){
		if(str != null){
			str  = str.trim().replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "").replaceAll("\r", "");
		}
		return str;		
	}
	
	 public static void main(String[] args) throws Exception {
	     
		 ES_TransportClient_unitmore_export client = new ES_TransportClient_unitmore_export();
	     client.init();
	     client.search();
	     client.close();

	 }
}
