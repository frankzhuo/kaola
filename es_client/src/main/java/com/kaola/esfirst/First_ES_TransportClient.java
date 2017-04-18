package com.kaola.esfirst;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

public class First_ES_TransportClient {
	
	 public Client client;
	 public void init() throws Exception
	 {
	     //ip可以在http://localhost:9200/_plugin/head/中自行查询
		//version 2.2.2
//		 Settings settings = Settings.settingsBuilder()
//					.put("cluster.name", "lakala2")//设置集群名称
//					 .put("client.transport.sniff", true)
//					 .build();//自
//		 client =TransportClient.builder().settings(settings).build()
//			 		 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("trs-14"), Integer.parseInt("9300")));
		 
		//version 2.2.2
//		 Settings settings = Settings.settingsBuilder()
//				.put("cluster.name", "lakala")//设置集群名称
//				.put("node.name","node1")
//				.put("node.name","node2")
//				 //.put("client.transport.sniff", true)
//				 .build();//自
//		 client =TransportClient.builder().settings(settings).build()
//				 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("trs-11"), Integer.parseInt("9300")))
//		 		 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("trs-13"), Integer.parseInt("9300")));
		 
		 //version 1.7.1 in 测试网
//		 Settings settings = ImmutableSettings.settingsBuilder()
//					.put("cluster.name", "lakala")//设置集群名称
//					 .put("client.transport.sniff", true)
//					 .build();//自
//		 client = new TransportClient(settings)
//					 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("trs-11"), Integer.parseInt("9300")))
//			 		 .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("trs-13"), Integer.parseInt("9300")));
			 
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

	 /*
	  * create index
	  **/
	 public void createIndex() {
	     for (int i = 0; i < 1000; i++) {
	         User user = new User();
	         user.setId(new Long(i));
	         user.setName("xxx0624 " + i);
	         user.setAge(i % 100);
	         System.out.println("ok:"+i);
	         client.prepareIndex("users", "user").setSource(generateJson(user))
	                 .execute().actionGet();
	     }
	 }

	 /*
	  * 转换成json对象
	  *
	  * @param user
	  * @return json(String)
	  **/
	 private String generateJson(User user) {
	     String json = "";
	     try {
	         XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
	         contentBuilder.field("id", user.getId() + "");
	         contentBuilder.field("name", user.getName());
	         contentBuilder.field("age", user.getAge() + "");
	         json = contentBuilder.endObject().string();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	     return json;
	 }

	 public void search() {
	     QueryBuilder qb = QueryBuilders
	             .boolQuery()
	             .must(QueryBuilders.termQuery("publish_time", "2016"))
	            // .should(QueryBuilders.termQuery("id", "0"))
	             //.mustNot(QueryBuilders.termQuery("content", "test2"))
	             //.should(QueryBuilders.termQuery("content", "test3"))
	             ;
	     
	     SearchResponse response = client.prepareSearch("law_court")
	             .setTypes("court_notice")
	             .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	             .setQuery(qb) // Query
	            // .setFilter(FilterBuilders.rangeFilter("age").from(0).to(100)) // Filter
	             .setFrom(0).setSize(100).setExplain(true)
	             .execute().actionGet();
	     SearchHits hits = response.getHits();
	     System.out.println(hits.getTotalHits());
	     for (int i = 0; i < hits.getHits().length; i++) {
	         System.out.println(hits.getHits()[i].getSourceAsString());
	     }
	 }    

	 public static void main(String[] args) throws Exception {
	     
		 First_ES_TransportClient client = new First_ES_TransportClient();
	     client.init();
	     //client.createIndex();
	     client.search();
	     client.close();

	 }
}
