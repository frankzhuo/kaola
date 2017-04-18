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



import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class TermQ {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "lakala").build();
		Client client = new TransportClient(settings)
        .addTransportAddress(new InetSocketTransportAddress("trs-11", 9300))
        .addTransportAddress(new InetSocketTransportAddress("10.5.28.13", 9300));
		
		//MatchQueryBuilder qb =QueryBuilders.matchQuery("yuangao", "王恩豹").operator(MatchQueryBuilder.Operator.AND);
		MatchQueryBuilder qb =QueryBuilders.matchPhraseQuery("yuangao", "李旭明");

		//BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.Q("jcase", "劳动争议")).mustNot(QueryBuilders.termQuery("yuangao", "")).mustNot(QueryBuilders.termQuery("shangshuren", ""))  ;
		SearchResponse response = client.prepareSearch("law")
		        .setTypes("judgement")
		        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)
		        .setQuery(qb)
		        .setFrom(0).setSize(80000).setExplain(true)
		       
		        .addHighlightedField("content")
		        .setHighlighterPreTags("<span style=\"color:red\">")
		        .setHighlighterPostTags("</span>")
		        .execute()
		        .actionGet();

		
		SearchHits hits = response.getHits();
        System.out.println("记录条数=" + hits.getTotalHits());
        System.out.println(hits.toString());
        SearchHit[] searchHists = hits.getHits();
        if(searchHists.length>0){
            for(SearchHit hit:searchHists){        
            	//System.out.println(hit.getId());
            	//System.out.println("content:"+ ((HashMap)hit.getSource().get("body")));
                System.out.println("jcase:"+ ((HashMap)hit.getSource().get("body")).get("jcase"));
                System.out.println("yuangao:"+ ((HashMap)hit.getSource().get("body")).get("yuangao"));
                System.out.println("shangshuren:"+ ((HashMap)hit.getSource().get("body")).get("shangshuren"));             	    
//            	    updateRequest.doc(jsonBuilder()
//            	            .startObject()
//            	                .field("gender", "male")
//            	            .endObject());          
            }
        }
        
 

       System.out.println(response.getHits().getTotalHits());
       System.out.println("记录条数=" + hits.getTotalHits());
	}

}
