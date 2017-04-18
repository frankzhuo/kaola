package com.kaola.edata.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.CommonTermsQueryBuilder.Operator;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;

import com.kaola.edata.model.SearchData;
public class JudgeSearch {

	/**
	 * @param args
	 */
	public static SearchData Search(String str) {
		System.out.println("11");
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "lakala").build();
		Client client = new TransportClient(settings)
        .addTransportAddress(new InetSocketTransportAddress("trs-11", 9300))
        .addTransportAddress(new InetSocketTransportAddress("10.5.28.13", 9300));
		
		BoolQueryBuilder bool = QueryBuilders.boolQuery();
		String  [] splits=str.split(" ");
		for(String st : splits){
			//MatchQueryBuilder qb =QueryBuilders.matchPhraseQuery("*", st);
			MultiMatchQueryBuilder mu=QueryBuilders.multiMatchQuery(st, "content","title","name").type(Type.PHRASE);
			System.out.println(st);
			bool.must(mu);
		}
	
		SearchResponse response = client.prepareSearch("law")
		        //.setTypes("judgement")
		        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)       
				.setQuery(bool)

		        .setFrom(0).setSize(100).setExplain(true)		       
		        .addHighlightedField("content")
		        .addHighlightedField("title")
		        .addHighlightedField("name")
		        .setHighlighterPreTags("<span style=\"color:red\">")
		        .setHighlighterPostTags("</span>")
		        .execute()
		        .actionGet();

	
		SearchHits hits = response.getHits();
        System.out.println("记录条数=" + hits.getTotalHits());

       SearchData data=new SearchData();
       data.setPageNum(100);
       data.setTotal(hits.hits().length);

       for(SearchHit hit : hits.hits() ){
		   
		
	  
    	   List old=data.getList();
    	   String tmp="";
    	   boolean flag=false;
    	   HashMap<String,String> map=(HashMap<String,String>)hit.getSource().get("body");
    	   for(String key:map.keySet()){
    		   String value = ""; 
    		   if(key.equals("content")||key.equals("name") ||key.equals("title") ){
    			   if(hit.getHighlightFields().get(key)!=null){
		    		   Text[] titleTexts =  hit.getHighlightFields().get(key).fragments(); 
		    	       for(Text text : titleTexts){    
		    	    	   value += text;  
		    	       }
		    	       flag=true;
    			   }
    		   }else
    			   value=map.get(key);
    		   if(value.length()>100&&!flag)       		  
    			   tmp=tmp+"<span style=\"left:60px;\" >"+key+":"+value.substring(0, 100)+"</span><br>";
        	   else
        		   tmp=tmp+"<span style=\"left:60px;\" >"+key+":"+value+"</span><br>";	   
    	   }
    	   old.add(tmp);
    	   data.setList(old);
    	   
    	  
       }
       return data;
       //return hits.getAt(0).getSourceAsString();
	}

}
