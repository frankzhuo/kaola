package com.kaola.edata;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermFilterBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import static org.elasticsearch.common.xcontent.XContentFactory.*;
/**
 * @param 聚合测试
 */
public class Agg {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "lakala").build();
		//Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "edata").build();

		Client client = new TransportClient(settings)
				
	    //.addTransportAddress(new InetSocketTransportAddress("10.1.80.181", 9300));
        .addTransportAddress(new InetSocketTransportAddress("trs-11", 9300))
        .addTransportAddress(new InetSocketTransportAddress("10.5.28.13", 9300));
	    BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery())  ;
		int num=100;
	    for(int i=0;i<2;i++){
		    SearchResponse response = client.prepareSearch("law")
			        .setTypes("court_notice")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        //.setSearchType(SearchType.QUERY_THEN_FETCH)
			        //.addAggregation(AggregationBuilders.terms("url"))
			      //.addAggregation(AggregationBuilders.terms("url"))
			       // .setQuery(qb)
			        .addAggregation(AggregationBuilders.terms("url1").field("url"))
			        //.setFrom(0+num*i).setSize(num).setExplain(true)			      	
			        .execute()
			        .actionGet();
	        Terms terms = response.getAggregations().get("url1");
	        if (terms != null) {
	            for (Terms.Bucket entry : terms.getBuckets()) {
	            	System.out.println(entry.getKey()+"："+String.valueOf(entry.getDocCount()));
	                
	            }
	        }
		    //response.getAggregations();
		   
			SearchHits hits = response.getHits();
	        System.out.println("记录条数=" + hits.getTotalHits());
	        System.out.println(hits.toString());
	        SearchHit[] searchHists = hits.getHits();
	        if(searchHists.length>0){
	            for(SearchHit hit:searchHists){        
	            	//System.out.println(hit.getId());
	            	System.out.println("content:"+ ((HashMap)hit.getSource().get("body")));
	 //                System.out.println("jtype:"+ ((HashMap)hit.getSource().get("body")).get("jtype"));
	//                System.out.println("court:"+ ((HashMap)hit.getSource().get("body")).get("court"));
	//                System.out.println("title:"+ ((HashMap)hit.getSource().get("body")).get("title")); 
	//                System.out.println("title1:"+ hit.getSource().toString()); 
	//                //client.up
	                try{
//	                   UpdateRequest request =new UpdateRequest();
//	                   
//	                     //System.out.println("$$$$$"+client.prepareGet("law","judgement",hit.getId()).execute().get().getId());
//	            	    //System.out.println(client.prepareUpdate("law","judgement",hit.getId())..execute().get().getId());
//	                   String yuangao=((HashMap)hit.getSource().get("body")).get("yuangao").toString();
//	             	   String shangshuren=((HashMap)hit.getSource().get("body")).get("shangshuren").toString();
//	                   //System.out.println(yuangao.trim().length());
//	                   if(yuangao.trim().length()==0||shangshuren.trim().length()==0)
//	                	   continue;
//	                   //if(filterPerson(yuangao)||(yuangao.trim().length()>3&&getPerson(yuangao))){ 
//		                	UpdateRequest updateRequest = new UpdateRequest();
//		            	    updateRequest.index("law");
//		            	    updateRequest.type("judgement");
//		            	    updateRequest.id(hit.getId());	            	                	    
//		            	    XContentBuilder builder = jsonBuilder()
//		            	    	    .startObject()
//		            	    	        .field("body").startObject()
//		            	    	        .field("yuangao", "")
//		            	    	        .endObject()
//		            	    	    .endObject();
//		            	    updateRequest.doc(builder);
//		                    System.out.println("jcase:"+ ((HashMap)hit.getSource().get("body")).get("jcase"));
//		                    System.out.println("yuangao:"+ ((HashMap)hit.getSource().get("body")).get("yuangao"));
//		                    System.out.println("shangshuren:"+ ((HashMap)hit.getSource().get("body")).get("shangshuren"));
//	
//		            	    System.out.println("**yuangao"+yuangao);
//		            	    client.update(updateRequest).get().getId();
//		              	//}
//	                   //if(filterPerson(shangshuren)||(shangshuren.length()>3&&getPerson(shangshuren))){ 
//		                	UpdateRequest updateRequest = new UpdateRequest();
//		            	    updateRequest.index("law");
//		            	    updateRequest.type("judgement");
//		            	    updateRequest.id(hit.getId());	            	                	    
//		            	    XContentBuilder builder = jsonBuilder()
//		            	    	    .startObject()
//		            	    	        .field("body").startObject()
//		            	    	        .field("shangshuren", "")
//		            	    	        .endObject()
//		            	    	    .endObject();
//		            	    updateRequest.doc(builder);
//		            	    System.out.println("**shangshuren"+shangshuren);
//		            	    client.update(updateRequest).get().getId();
		              	//}
		             	    
	//            	    updateRequest.doc(jsonBuilder()
	//            	            .startObject()
	//            	                .field("gender", "male")
	//            	            .endObject());
	            	    System.out.println();
	                }catch(Exception d){
	                	d.printStackTrace();
	                }
	            }
	        }
	        
	
			}
       //System.out.println(response.getHits().getTotalHits());
       //System.out.println("记录条数=" + hits.getTotalHits());
	}

}
