package com.kaola.edata;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
 * @param 法庭公告出重
 */
public class RemovalD {
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "lakala").build();
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "edata").build();

		Client client = new TransportClient(settings)
				
	    .addTransportAddress(new InetSocketTransportAddress("10.1.80.181", 9300));
        //.addTransportAddress(new InetSocketTransportAddress("trs-11", 9300))
        //.addTransportAddress(new InetSocketTransportAddress("10.5.28.13", 9300));
	    //BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery())  ;
	    File file = new File("E:\\esdata\\annouce\\clean_data.txt");
	    //File file = new File("");
	    try{
		    BufferedReader reader = new BufferedReader(new FileReader(file));
	        FileWriter fileWritter = new FileWriter("d:\\court.txt",true);
	      
	        String tempString="";
	        while ((tempString = reader.readLine()) != null) {
		        try {
		        	String []str=tempString.split("\001");
		        	String url=str[4];
		        	
		            System.out.println("*"+url+"**");
		            int  num =0;
		            try{
		            	num=JredisUtil.hget("court", url);
		            }catch(Exception o){
		            	o.printStackTrace();
		            }
		            if(num==0){
		            	JredisUtil.hset("court", url, 1);
		            	fileWritter.write(tempString+"\n\r");
		            }
		            
		         
			        System.out.println("记录条数=" + num);
		        }   
				 catch(Exception o){
					 o.printStackTrace();
				 }
		      
	        }
	    }catch(Exception o){
	    	
	    }
       //System.out.println(response.getHits().getTotalHits());
       //System.out.println("记录条数=" + hits.getTotalHits());
	}

}
