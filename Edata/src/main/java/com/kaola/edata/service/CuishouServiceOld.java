package com.kaola.edata.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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

import com.kaola.edata.model.CuishouBean;
import com.kaola.edata.model.SearchData;
public class CuishouServiceOld {

	/**
	 * @param args
	 */
	public static String Search(String  mobile,String idno,String cardno) {
		
		//连接信息
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "lakala").build();
		Client client = new TransportClient(settings)
        .addTransportAddress(new InetSocketTransportAddress("10.5.28.11", 9300))
        //.addTransportAddress(new InetSocketTransportAddress("10.5.28.13", 9200))
        //.addTransportAddress(new InetSocketTransportAddress("10.5.28.11", 9200))
        .addTransportAddress(new InetSocketTransportAddress("10.5.28.13", 9300));		
	   
		//身份证查疑似本人
		MatchQueryBuilder qb =QueryBuilders.matchPhraseQuery("idno", idno);
		SearchResponse response = client.prepareSearch("graph_user")
		        .setTypes("user")
		        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)       
				.setQuery(qb)
		        .setFrom(0).setSize(100).setExplain(true)		       
		        .execute()
		        .actionGet();
	   SearchHits hits = response.getHits();
       System.out.println("身份证查疑似本人记录条数=" + hits.getTotalHits());
       String idmobile="";
       for(SearchHit hit : hits.hits() ){	   
    	   idmobile=((HashMap)hit.getSource().get("body")).get("mobile").toString();  	  
           System.out.println("idno"+idmobile);
       }
       XContentBuilder builder = null;     
       try{
    	  builder =XContentFactory.jsonBuilder() ;
    	  if(!mobile.equals(idmobile)){    	   
    		     builder.startObject()  
     			.field("idmobile", idmobile)   
     			.endObject(); 
    	  }
       }catch(Exception e){
     	  e.printStackTrace();
       }
       
     
       
       System.out.println("卡开始");
	    //卡查疑似本人
		MatchQueryBuilder qbc =QueryBuilders.matchPhraseQuery("cardno", cardno);
		SearchResponse responsec = client.prepareSearch("graph_card_mobile")
		        .setTypes("card_mobile")
		        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		        .setSearchType(SearchType.QUERY_THEN_FETCH)       
				.setQuery(qbc)		
		        .setFrom(0).setSize(100).setExplain(true)		       
		        .execute()
		        .actionGet();
	   SearchHits hitsc = responsec.getHits();
       System.out.println("卡查疑似本人记录条数=" + hitsc.getTotalHits());
       String cardmobile="";
       int  i=1;
       for(SearchHit hit : hitsc.hits() ){	 
          try{
        	  cardmobile=((HashMap)hit.getSource().get("body")).get("mobile").toString();
        	  if(!mobile.equals(cardmobile)){
	         	  builder.startObject()         			
	         	  .field("cardmobile"+i, cardmobile)    
	         	  .endObject(); 
	         	 i++;
        	  }
         	  if(i>3){
         		  break;
         	  }
         	 System.out.println("card"+cardmobile);
           }catch(Exception e){
         	 e.printStackTrace(); 
           } 	 
       }
  
       //求所有卡
      Set<String>  set=new HashSet<String>();
	   MatchQueryBuilder qbm =QueryBuilders.matchPhraseQuery("mobile", mobile);
	   BoolQueryBuilder bool = QueryBuilders.boolQuery();
	   MatchQueryBuilder qbid=QueryBuilders.matchPhraseQuery("mobile", idmobile);		
	   bool.should(qbm).should(qbid);
	   SearchResponse responsecomm = client.prepareSearch("graph_card_mobile")
	        .setTypes("card_mobile")
	        .setSearchType(SearchType.QUERY_THEN_FETCH)       
			.setQuery(bool)
	        .setFrom(0).setSize(100).setExplain(true)		       
	        .execute()
	        .actionGet();
      SearchHits hitscomm = responsecomm.getHits();
      System.out.println("求所有卡记录条数=" + hitscomm.getTotalHits());
      String card="";
      for(SearchHit hit : hitscomm.hits() ){	   
    	    card=((HashMap)hit.getSource().get("body")).get("cardno").toString();  	  
   	   		set.add(card);   
   	        System.out.println("card"+card);
      }
      set.add(cardno);
      System.out.println("card"+set.size());
      
      
      
      //卡对应的手机号
  	  //BoolQueryBuilder boolk = QueryBuilders.boolQuery();  	
      String qstr1="";
      int n1=0;
	  for(String cardk:set){
		  if("".equals(cardk))
				continue;
			if(n1==0)
				qstr1=cardk;
			else
				qstr1=qstr1+ " OR "+cardk ;
			n1++;
		//MatchQueryBuilder qbtmp=QueryBuilders.matchPhraseQuery("cardno", cardk);
		//boolk.should(qbtmp);
		  
	  }	 
	  System.out.println(qstr1);
	  QueryStringQueryBuilder qs1=QueryBuilders.queryStringQuery(qstr1).field("cardno");
	  //boolhk.must(qs);

	  SearchResponse responsek = client.prepareSearch("graph_card_mobile")
		        .setTypes("card_mobile")
		        .setSearchType(SearchType.QUERY_THEN_FETCH)       
				.setQuery(qs1)
				.setTimeout("10000")
		        .setFrom(0).setSize(100).setExplain(true)		       
		        .execute()
		        .actionGet();
	  SearchHits hitsk = responsek.getHits();
      System.out.println("卡对应的手机号记录条数=" + hitsk.getTotalHits());
      HashMap<String,String > mapk=new HashMap<String,String>();
      for(SearchHit hit : hitsk.hits() ){	
  	      String cardtmp=((HashMap)hit.getSource().get("body")).get("cardno").toString();
 	      String mobiletmp=((HashMap)hit.getSource().get("body")).get("mobile").toString();
 	     System.out.println("**"+cardtmp+":"+mobiletmp);
 	      if(!mapk.containsKey(cardtmp) && !mobile.equals(mobiletmp)){
	  		  System.out.println(cardtmp+":"+mobiletmp);
	  		  mapk.put(cardtmp, mobiletmp); 
	  	  }
       }
          
      //信用卡还款
	  	MatchQueryBuilder qbtypehk =QueryBuilders.matchPhraseQuery("tv4name", "信用卡还款");
		//被还款  	
	  	BoolQueryBuilder boolhk = QueryBuilders.boolQuery();
		boolhk.must(qbtypehk);
		String qstr="";
		int n=0;
		for(String cardhk:set){
			//MatchQueryBuilder qbtmp=QueryBuilders.matchPhraseQuery("incdno", cardhk);
			if("".equals(cardhk))
				continue;
			if(n==0)
				qstr=cardhk;
			else
				qstr=qstr+ " OR "+cardhk ;
			n++;
			//boolhk.should(qbtmp);
		}	
		//qstr =qstr+ " OR 6226808008374754";
		System.out.println(qstr);
		QueryStringQueryBuilder qs=QueryBuilders.queryStringQuery(qstr).field("incdno");
		boolhk.must(qs);
		SearchResponse responsehk = client.prepareSearch("graph_trade")
			        .setTypes("trade")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setSearchType(SearchType.QUERY_THEN_FETCH)       
					.setQuery(boolhk)
			        .setFrom(0).setSize(100).setExplain(true)		       
			        .execute()
			        .actionGet();
		  SearchHits hitshk = responsehk.getHits();
	      System.out.println("被还款记录条数=" + hitshk.getTotalHits());
	      HashMap<String,CuishouBean> maphk1=new HashMap<String,CuishouBean>();
	      for(SearchHit hit : hitshk.hits() ){	
	    	  String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();	
	    	  if(maphk1.containsKey(cardtmp)){
	    		  CuishouBean bean = maphk1.get(cardtmp);
	    		  bean.setTimes(bean.getTimes()+1);
	    		  if(((HashMap)hit.getSource().get("body")).get("ddate").toString().compareTo(bean.getRdate())>0){
	    			  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
	    		  }
	    	  }
	    	  else{
		    	  CuishouBean bean= new CuishouBean(); 
		    	  bean.setMobile(((HashMap)hit.getSource().get("body")).get("mobile").toString());
		    	  bean.setAddress(((HashMap)hit.getSource().get("body")).get("address").toString());
		    	  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
		    	  bean.setTotalAM(Integer.parseInt(((HashMap)hit.getSource().get("body")).get("total_am").toString()));
		    	  bean.setCardno(cardtmp);
		    	  //bean.setMobilenum(mapk.get(cardtmp));
		    	  bean.setType("信用卡还款");
		    	  bean.setTimes(1);
		    	  bean.setSubType("被还款");
		    	  maphk1.put(cardtmp, bean);
		    	  //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
	    	  }
	    	  System.out.println(cardtmp+" :"+maphk1.get(cardtmp).getMobilenum());
	    	
	      }	     	      
	    
	      //替人还款
	      
	      BoolQueryBuilder boolhk2 = QueryBuilders.boolQuery();
	  	  boolhk2.must(qbtypehk);
	  
	  	

		String qstr2="";
		int n2=0;
		for(String cardhk:set){
			//MatchQueryBuilder qbtmp=QueryBuilders.matchPhraseQuery("incdno", cardhk);
			if("".equals(cardhk))
				continue;
			if(n==0)
				qstr=cardhk;
			else
				qstr=qstr+ " OR "+cardhk ;
			n++;
			//boolhk.should(qbtmp);
		}	
		//qstr =qstr+ " OR 6226808008374754";
		System.out.println(qstr);
		QueryStringQueryBuilder qs2=QueryBuilders.queryStringQuery(qstr).field("outcdno");
		boolhk2.must(qs2);

	  	  
	  	  SearchResponse responsehk2 = client.prepareSearch("graph_trade")
	  		        .setTypes("trade")
	  		        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	  		        .setSearchType(SearchType.QUERY_THEN_FETCH)       
	  				.setQuery(boolhk2)
	  		        .setFrom(0).setSize(100).setExplain(true)		       
	  		        .execute()
	  		        .actionGet();
	  	    SearchHits hitshk2 = responsehk2.getHits();
	        System.out.println("替人还款记录条数=" + hits.getTotalHits());
	        HashMap<String,CuishouBean> maphk2=new HashMap<String,CuishouBean>();
	        for(SearchHit hit : hitshk2.hits() ){	
	      	  String cardtmp=((HashMap)hit.getSource().get("body")).get("outcdno").toString();
	
	      	  if(maphk2.containsKey(cardtmp)){
	      		  CuishouBean bean = maphk2.get(cardtmp);
	      		  bean.setTimes(bean.getTimes()+1);
	      		  if(((HashMap)hit.getSource().get("body")).get("ddate").toString().compareTo(bean.getRdate())>0){
	      			  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
	      		  }
	      	  }
	      	  else{
	  	    	  CuishouBean bean= new CuishouBean(); 
	  	    	  bean.setMobile(((HashMap)hit.getSource().get("body")).get("mobile").toString());
	  	    	  bean.setAddress(((HashMap)hit.getSource().get("body")).get("address").toString());
	  	    	  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
	  	    	  bean.setTotalAM(Integer.parseInt(((HashMap)hit.getSource().get("body")).get("total_am").toString()));
	  	    	  bean.setType("信用卡还款");
	  	    	  bean.setTimes(1);
	  	    	  bean.setSubType("替人还款");
	  	    	  maphk2.put(cardtmp, bean);
	  	    	  //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
	      	  }
	      	  
	      	  //System.out.println("idno"+idmobile);
	        }
	        
	        
		
	    //转账
		MatchQueryBuilder qbtypezz =QueryBuilders.matchPhraseQuery("tv3name", "转账汇款");
		//转入
		BoolQueryBuilder boolzz = QueryBuilders.boolQuery();
		
		boolzz.must(qbtypezz);
		String qstr3="";
		for(String cardhk:set){
			//MatchQueryBuilder qbtmp=QueryBuilders.matchPhraseQuery("incdno", cardhk);
			if("".equals(cardhk))
				continue;
			if(n==0)
				qstr3=cardhk;
			else
				qstr3=qstr3+ " OR "+cardhk ;
			n++;
			//boolhk.should(qbtmp);
		}	
		//qstr3 =qstr3+ " OR 6226808008374754";
		System.out.println(qstr3);
		QueryStringQueryBuilder qs3=QueryBuilders.queryStringQuery(qstr).field("incdno");
		boolzz.must(qs3);
		
		SearchResponse responsezz = client.prepareSearch("graph_trade")
			        .setTypes("trade")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setSearchType(SearchType.QUERY_THEN_FETCH)       
					.setQuery(boolzz)
			        .setFrom(0).setSize(100).setExplain(true)		       
			        .execute()
			        .actionGet();
		  SearchHits hitszz = responsezz.getHits();
	   System.out.println("转入记录条数=" + hits.getTotalHits());
	   HashMap<String,CuishouBean> mapzz1=new HashMap<String,CuishouBean>();
	   for(SearchHit hit : hitszz.hits() ){	
	 	  String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
	
	 	  if(mapzz1.containsKey(cardtmp)){
	 		  CuishouBean bean = maphk1.get(cardtmp);
	 		  bean.setTimes(bean.getTimes()+1);
	 		  if(((HashMap)hit.getSource().get("body")).get("ddate").toString().compareTo(bean.getRdate())>0){
	 			  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
	 		  }
	 	  }
	 	  else{
		    	  CuishouBean bean= new CuishouBean(); 
		    	  bean.setMobile(((HashMap)hit.getSource().get("body")).get("mobile").toString());
		    	  bean.setAddress(((HashMap)hit.getSource().get("body")).get("address").toString());
		    	  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
		    	  bean.setTotalAM(Integer.parseInt(((HashMap)hit.getSource().get("body")).get("total_am").toString()));
		    	  bean.setType("银行卡转账");
		    	  bean.setTimes(1);
		    	  bean.setSubType("转入");
		    	  mapzz1.put(cardtmp, bean);
		    	  //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
	 	  }
	 	  
	 	  //System.out.println("idno"+idmobile);
	   }
//	   if(true) 
//	        return "111111111"; 
	   
		//转出
		BoolQueryBuilder boolzz2 = QueryBuilders.boolQuery();		
		boolzz2.must(qbtypezz);
		String qstr4="";
		n=0;
		for(String cardhk:set){
			if("".equals(cardhk))
				continue;
			if(n==0)
				qstr4=cardhk;
			else
				qstr4=qstr4+ " OR "+cardhk ;
			n++;
			//boolhk.should(qbtmp);
		}	
		//qstr3 =qstr3+ " OR 6226808008374754";
		System.out.println(qstr3);
		QueryStringQueryBuilder qs4=QueryBuilders.queryStringQuery(qstr4).field("outcdno");
		boolzz2.must(qs4);			
		SearchResponse responsezz2 = client.prepareSearch("graph_trade")
			        .setTypes("trade")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setSearchType(SearchType.QUERY_THEN_FETCH)       
					.setQuery(boolzz2)
			        .setFrom(0).setSize(100).setExplain(true)		       
			        .execute()
			        .actionGet();
	  SearchHits hitszz2 = responsezz2.getHits();
	  System.out.println("转出记录条数=" + hits.getTotalHits());
	  HashMap<String,CuishouBean> mapzz2=new HashMap<String,CuishouBean>();
	  for(SearchHit hit : hitszz2.hits() ){	
		  String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
	
		  if(mapzz1.containsKey(cardtmp)){
			  CuishouBean bean = maphk2.get(cardtmp);
			  bean.setTimes(bean.getTimes()+1);
			  if(((HashMap)hit.getSource().get("body")).get("ddate").toString().compareTo(bean.getRdate())>0){
				  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
			  }
		  }
		  else{
		    	  CuishouBean bean= new CuishouBean(); 
		    	  bean.setMobile(((HashMap)hit.getSource().get("body")).get("mobile").toString());
		    	  bean.setAddress(((HashMap)hit.getSource().get("body")).get("address").toString());
		    	  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
		    	  bean.setTotalAM(Integer.parseInt(((HashMap)hit.getSource().get("body")).get("total_am").toString()));
		    	  bean.setType("银行卡转账");
		    	  bean.setTimes(1);
		    	  bean.setSubType("转出");
		    	  mapzz2.put(cardtmp, bean);
		    	  //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
		  }
		  
		  System.out.println("idno"+idmobile);
	   }
		
	    //充值	  
	    MatchQueryBuilder qbtypecz =QueryBuilders.matchPhraseQuery("tv3name", "话费充值");
		BoolQueryBuilder boolcz = QueryBuilders.boolQuery();	
		boolcz.must(qbtypecz);
		n=0;
		qstr4="";
		for(String cardhk:set){
			if("".equals(cardhk))
				continue;
			if(n==0)
				qstr4=cardhk;
			else
				qstr4=qstr4+ " OR "+cardhk ;
			n++;
			//boolhk.should(qbtmp);
		}	
		//qstr4 =qstr4+ " OR 6226808008374754";
		System.out.println(qstr4);
		QueryStringQueryBuilder qs5=QueryBuilders.queryStringQuery(qstr4).field("outcdno");
		boolcz.must(qs5);	
		SearchResponse responsecz = client.prepareSearch("graph_trade")
			        .setTypes("trade")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setSearchType(SearchType.QUERY_THEN_FETCH)       
					.setQuery(boolcz)
			        .setFrom(0).setSize(100).setExplain(true)		       
			        .execute()
			        .actionGet();
		 SearchHits hitscz = responsecz.getHits();
		 System.out.println("记录条数=" + hitscz.getTotalHits());
		 HashMap<String,CuishouBean> mapcz1=new HashMap<String,CuishouBean>();
		 for(SearchHit hit : hitscz.hits() ){	
			  String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();	
			  if(mapcz1.containsKey(cardtmp)){
				  CuishouBean bean = mapcz1.get(cardtmp);
				  bean.setTimes(bean.getTimes()+1);
				  if(((HashMap)hit.getSource().get("body")).get("ddate").toString().compareTo(bean.getRdate())>0){
					  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
				  }
			  }
			  else{
			    	  CuishouBean bean= new CuishouBean(); 
			    	  bean.setMobile(((HashMap)hit.getSource().get("body")).get("mobile").toString());
			    	  bean.setAddress(((HashMap)hit.getSource().get("body")).get("address").toString());
			    	  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());
			    	  bean.setTotalAM(Integer.parseInt(((HashMap)hit.getSource().get("body")).get("total_am").toString()));
			    	  bean.setType("充值");
			    	  bean.setTimes(1);
			    	  bean.setSubType("充值");
			    	  bean.setMobilenum(cardtmp);
			    	  mapcz1.put(cardtmp, bean);
			    	  System.out.println("被充值手机："+cardtmp);
			    	  //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
			  }
	        }
	
		    //公缴		  
		    MatchQueryBuilder qbtypegj =QueryBuilders.matchPhraseQuery("tv4name", "公缴费-煤气水电");		
			BoolQueryBuilder boolgj = QueryBuilders.boolQuery();			
			boolgj.must(qbtypegj);
			n=0;
			qstr4="";
			for(String cardhk:set){
				if("".equals(cardhk))
					continue;
				if(n==0)
					qstr4=cardhk;
				else
					qstr4=qstr4+ " OR "+cardhk ;
				n++;
				//boolhk.should(qbtmp);
			}	
			//qstr4 =qstr4+ " OR 6226808008374754";
			System.out.println(qstr4);
			QueryStringQueryBuilder qs6=QueryBuilders.queryStringQuery(qstr4).field("outcdno");
			boolgj.must(qs6);
			SearchResponse responsegj = client.prepareSearch("graph_trade")
				        .setTypes("trade")
				        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				        .setSearchType(SearchType.QUERY_THEN_FETCH)       
						.setQuery(boolcz)
				        .setFrom(0).setSize(100).setExplain(true)		       
				        .execute()
				        .actionGet();
			 SearchHits hitsgj = responsegj.getHits();
			 System.out.println("公缴记录条数=" + hitsgj.getTotalHits());
			 HashMap<String,CuishouBean> mapgj1=new HashMap<String,CuishouBean>();
			 for(SearchHit hit : hitsgj.hits() ){	
				  String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
			
				  if(mapgj1.containsKey(cardtmp)){
					  CuishouBean bean = mapgj1.get(cardtmp);
					  bean.setTimes(bean.getTimes()+1);					  
				  }
				  else{
			    	  CuishouBean bean= new CuishouBean(); 
			    	  bean.setMobile(((HashMap)hit.getSource().get("body")).get("mobile").toString());
			    	  bean.setAddress(((HashMap)hit.getSource().get("body")).get("address").toString());
			 	      bean.setType("公缴");
			    	  bean.setTimes(1);
			    	  bean.setSubType("公缴");
			    	  mapgj1.put(cardtmp, bean);			    	  
				      //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
				  }
		 }
	    List<Entry<String,CuishouBean>> listgj	= getList(mapgj1);
	       try{
	     	  builder =XContentFactory.jsonBuilder() ;
	     	  if(!mobile.equals(idmobile)){    	   
	     		     builder.startObject()  
	      			.field("idmobile", idmobile)   
	      			.endObject(); 
	     	  }
	        }catch(Exception e){
	      	  e.printStackTrace();
	        }
	    //生成结果json
	    String  result="";
	    try{
	       int times=1;
	       for(Entry<String,CuishouBean> bean :listgj){
	    	   builder.startObject()  
     			.field("gj_date"+times, bean.getValue().getRdate()) 
     			.field("gj_address"+times, bean.getValue().getAddress())  
     			.endObject();
	    	   times++; 
	       }
	       builder.startObject().field("sd","sdf").endObject();
	       
	 	   result=builder.string();
	    }catch(Exception o){
	 	   
	    }
	    System.out.println(result);
	    return result;
	    //return hits.getAt(0).getSourceAsString();
	}
	public static List<Entry<String,CuishouBean>> getList(HashMap<String,CuishouBean> map) {
		List<Entry<String,CuishouBean>> result= new ArrayList<Entry<String,CuishouBean>>();
		int num1=0;
		int num2=0;
		int num3=0;
		Entry<String,CuishouBean> one =null;
		Entry<String,CuishouBean> two =null;		
		Entry<String,CuishouBean> three =null;
		List<Entry<String,CuishouBean>> list=new ArrayList<Entry<String,CuishouBean>>();
		for(Entry<String,CuishouBean> entry:map.entrySet()){
			if(entry.getValue().getTimes()>=num1){
				three=two;
				two=one;
				one=entry;
				num1=entry.getValue().getTimes();
				if(two!=null)
					num2=two.getValue().getTimes();
				if(three!=null)
					num3=three.getValue().getTimes();
				continue;
			}
			if(entry.getValue().getTimes()>=num2){
				three=two;
				two=entry;
				num2=entry.getValue().getTimes();
				if(three!=null)
					num3=three.getValue().getTimes();
				continue;
			}
			if(entry.getValue().getTimes()>=num3){
				one=entry;
				num3=entry.getValue().getTimes();
				if(three!=null)
					num3=three.getValue().getTimes();
				continue;
			}
		}
		if(one !=null)
			result.add(one);
		if(two !=null)
			result.add(two);
		if(three !=null)
			result.add(three);
		return result;
	}
}
