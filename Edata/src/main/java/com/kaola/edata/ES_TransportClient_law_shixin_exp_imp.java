package com.kaola.edata;

import java.net.InetAddress;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

/**
 * 部署到10.1.80.181:/data/edata-1.0.0-BUILD-SNAPSHOT-jar-with-dependencies.jar
 * 每天同步到新构建的失信人和判决文书，
 * @author kaola
 *
 */
public class ES_TransportClient_law_shixin_exp_imp {
	
	 public Client client;
	 
	 public void init() throws Exception
	 {
		//version 1.7.1 in 正式网 
		 Settings settings = ImmutableSettings.settingsBuilder()
						//.put("cluster.name", "lakala")//设置集群名称
				 		 .put("cluster.name", "edata")//设置集群名称
						 .put("client.transport.sniff", true)
						 .build();//自
		client = new TransportClient(settings)
				     //.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.5.28.13"), Integer.parseInt("9300")));
				      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.80.181"), Integer.parseInt("9300")));
		 System.out.println("client is init");   
	 }

	 public void close()
	 {
	     client.close();
	     System.out.println("client is close");   
	 }

	 
	//清楚格式
	public static String cleanStr(String str){
		if(str != null){
			str  = str.trim().replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "").replaceAll("\r", "");
		}
		return str;		
	}
	
	 public static void main(String[] args) throws Exception {
		 String date_format = "";
		 String branch = "";
	     if(args.length >1){
	    	 date_format = args[0].trim();
	    	 branch  =args[1].trim();
	    	 System.out.println("date_format::::"+date_format+"::::branch"+branch);
	     }else{
	    	 System.exit(0);
	     }
		 ES_TransportClient_law_shixin_exp_imp client = new ES_TransportClient_law_shixin_exp_imp();
	     client.init();
	     if("judgement_new".equals(branch)){
	    	 client.tranport_judgement_new(date_format);
	     }else if ("c_personmore_new".equals(branch)){
	    	 client.tranport_c_personmore_new(date_format);
	     }else if ("c_unitmore_new".equals(branch)){
		     client.tranport_c_unitmore_new(date_format);
	     }else{ //c_personmore_unitmore
	    	 client.tranport_c_personmore_new(date_format);
		     client.tranport_c_unitmore_new(date_format);
	     }
	     client.close();

	 }
	 
	//同步个人失信人
	 public void tranport_c_personmore_new(String date_format){
	    	// 获取Client
	    	SearchResponse scrollResp = client.prepareSearch("law_personmore").setTypes("c_personmore")
	                .setScroll(new TimeValue(60000))
	                .setQuery(QueryBuilders.termQuery("sys_time", date_format))
	                .setSize(100000).execute().actionGet(); //100 hits per shard will be returned for each scroll
	        //Scroll until no hits are returned
	    	System.out.println(scrollResp.getHits().getTotalHits());
	    	
	    	int count = (int) scrollResp.getHits().getTotalHits();
	    	if(count>0){
		    	 DeleteByQueryResponse reop=client.prepareDeleteByQuery("law_shixin").setTypes("c_personmore_new").setQuery(QueryBuilders.termQuery("sys_time", date_format)).get();
		    	 System.out.println( reop.status());
	    	}else{
	    		return;
	    	}
	    	
	        while (true) {
	            BulkRequestBuilder bulkRequest = client.prepareBulk();
	            System.out.println(scrollResp.getHits().getTotalHits());
	            for (SearchHit hit : scrollResp.getHits().getHits()) {
	            	Map<String, Object> map = hit.getSource();
	            	Map<String, Object> body = (Map<String, Object>) map.get("body");
	            	//System.out.println(body);
	            	if(body == null){
	            		continue;
	            	}
	            	Object tt = body.get("regdate");
	            	System.out.println(tt);
	            	if(tt !=null ){
	            		tt=tt.toString().replace("年", "").replace("月", "").replace("日", "");
	            	}
	            	body.put("regdate",tt);
	            	
	                IndexRequestBuilder indexRequest = client
	                        .prepareIndex("law_shixin", "c_personmore_new")
	                        // 指定不重复的ID
	                       // .setSource(map);
	                        .setSource(body);
	                // 添加到builder中
	                bulkRequest.add(indexRequest);
	            }
	            BulkResponse bulkResponse = bulkRequest.get();
//	            System.out.println(bulkResponse.hasFailures());
	            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
	            //Break condition: No hits are returned
	            if (scrollResp.getHits().getHits().length == 0) {
	            	System.out.println("export--import---tranport_c_personmore_new()---is over");
	                break;
	            }
	        }
	    }
	 
	 //同步企业失信人
	 public void tranport_c_unitmore_new(String date_format){
	    	// 获取Client
	    	SearchResponse scrollResp = client.prepareSearch("law_unitmore").setTypes("c_unitmore")
	                .setScroll(new TimeValue(60000))
	                .setQuery(QueryBuilders.termQuery("sys_time", date_format))
	                .setSize(100000).execute().actionGet(); //100 hits per shard will be returned for each scroll
	        //Scroll until no hits are returned
	    	int count = (int) scrollResp.getHits().getTotalHits();
	    	if(count>0){
		    	 DeleteByQueryResponse reop=client.prepareDeleteByQuery("law_shixin").setTypes("c_unitmore_new").setQuery(QueryBuilders.termQuery("sys_time", date_format)).get();
		    	 System.out.println( reop.status());
	    	}else{
	    		return;
	    	}
	    		
	        while (true) {
	            BulkRequestBuilder bulkRequest = client.prepareBulk();
	            for (SearchHit hit : scrollResp.getHits().getHits()) {
	            	Map<String, Object> map = hit.getSource();
	            	
	            	Map<String, Object> body = (Map<String, Object>) map.get("body");
	            	//System.out.println(body);
	            	if(body == null){
	            		continue;
	            	}
	            	Object tt = body.get("regdate");
	            	System.out.println(tt);
	            	if(tt !=null ){
	            		tt=tt.toString().replace("年", "").replace("月", "").replace("日", "");
	            	}
	            	body.put("regdate",tt);
	            	
	                IndexRequestBuilder indexRequest = client
	                        .prepareIndex("law_shixin", "c_unitmore_new")
	                        // 指定不重复的ID
	                        .setSource(body);
	                // 添加到builder中
	                bulkRequest.add(indexRequest);
	            }
	            BulkResponse bulkResponse = bulkRequest.get();
//	            System.out.println(bulkResponse.hasFailures());
	            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
	            //Break condition: No hits are returned
	            if (scrollResp.getHits().getHits().length == 0) {
	            	System.out.println("export--import---tranport_c_unitmore_new()---is over");
	                break;
	            }
	        }
	    }
	 
	 //同步判决文书
	 public void tranport_judgement_new(String date_format){
	    	// 获取Client
	    	SearchResponse scrollResp = client.prepareSearch("law_judgement").setTypes("judgement")
	                .setScroll(new TimeValue(60000))
	                .setQuery(QueryBuilders.termQuery("sys_time", date_format))
	                .setSize(50000).execute().actionGet(); //100 hits per shard will be returned for each scroll
	        //Scroll until no hits are returned
	    	int count = (int) scrollResp.getHits().getTotalHits();
	    	if(count>0){
		    	 DeleteByQueryResponse reop=client.prepareDeleteByQuery("law_shixin").setTypes("judgement_new").setQuery(QueryBuilders.termQuery("sys_time", date_format)).get();
		    	 System.out.println( reop.status());
	    	}else{
	    		return;
	    	}
	    		
	        while (true) {
	            BulkRequestBuilder bulkRequest = client.prepareBulk();
	            for (SearchHit hit : scrollResp.getHits().getHits()) {
	            	Map<String, Object> map = hit.getSource();
	            	
	            	Map<String, Object> body = (Map<String, Object>) map.get("body");
	            	//System.out.println(body);
	            	if(body == null){
	            		continue;
	            	}
	            	
	                IndexRequestBuilder indexRequest = client
	                        .prepareIndex("law_shixin", "judgement_new")
	                        // 指定不重复的ID
	                        .setSource(body);
	                // 添加到builder中
	                bulkRequest.add(indexRequest);
	            }
	            BulkResponse bulkResponse = bulkRequest.get();
//	            System.out.println(bulkResponse.hasFailures());
	            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
	            //Break condition: No hits are returned
	          //  if (scrollResp.getHits().getHits().length == 0) {
	            	System.out.println("export--import---tranport_judgement_new()---is over");
	                break;
	           // }
	        }
	    }
}
