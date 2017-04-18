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
public class CuishouService {

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
  
       //求所有直接关联卡
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
      int  breaknum=1;
      for(SearchHit hit : hitscomm.hits() ){	   
    	    card=((HashMap)hit.getSource().get("body")).get("cardno").toString();  	  
   	   		set.add(card);   
   	        System.out.println("cardbreaknum"+breaknum+":"+card);
   	        breaknum++;
   	        if(breaknum>80)
   	        	break;
      }
      set.add(cardno);
      System.out.println("card"+set.size());
      
      
      
        Set<String>  cardset=new HashSet<String>();
        Set<String>  mobileset=new HashSet<String>();
      //IN卡还款
		String qstr="";
		int n=0;
		for(String cardhk:set){
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
		
		SearchResponse responsehk = client.prepareSearch("graph_trade")
			        .setTypes("trade")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setSearchType(SearchType.QUERY_THEN_FETCH)       
					.setQuery(qs)
			        .setFrom(0).setSize(100).setExplain(true)		       
			        .execute()
			        .actionGet();
		  SearchHits hitshk = responsehk.getHits();
	      System.out.println("incardno记录条数=" + hitshk.getTotalHits());
	      HashMap<String,CuishouBean> maphk1=new HashMap<String,CuishouBean>();
		  HashMap<String,CuishouBean> mapzz1=new HashMap<String,CuishouBean>();

	      for(SearchHit hit : hitshk.hits() ){	
	    	  String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
	    	  String tv4name=((HashMap)hit.getSource().get("body")).get("tv4name").toString();
	    	  String tv3name=((HashMap)hit.getSource().get("body")).get("tv3name").toString();	
	    	  
	    	  if("信用卡还款".equals(tv4name) ){
	    		  cardset.add(cardtmp);
	    		  
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
	    	  }
	    	  
	    	  if("转账汇款".equals(tv3name) ){
	    		  cardset.add(cardtmp);
		    	  if(mapzz1.containsKey(cardtmp)){
			 		  CuishouBean bean = mapzz1.get(cardtmp);
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
	    	  }	  
	    	  //System.out.println(cardtmp+" :"+maphk1.get(cardtmp).getMobilenum());	    	
	      }	     	      
	         
	      
	      //替人还款
	      	   	
/*	    qstr="";
		n=0;
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
		System.out.println(qstr);*/
		QueryStringQueryBuilder qs2=QueryBuilders.queryStringQuery(qstr).field("outcdno");  	  
	  	  SearchResponse responsehk2 = client.prepareSearch("graph_trade")
	  		        .setTypes("trade")
	  		        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	  		        .setSearchType(SearchType.QUERY_THEN_FETCH)       
	  				.setQuery(qs2)
	  		        .setFrom(0).setSize(100).setExplain(true)		       
	  		        .execute()
	  		        .actionGet();
	  	    SearchHits hitshk2 = responsehk2.getHits();
	        System.out.println("outcard记录条数=" + hits.getTotalHits());
	        HashMap<String,CuishouBean> maphk2=new HashMap<String,CuishouBean>();
			HashMap<String,CuishouBean> mapgj1=new HashMap<String,CuishouBean>();
			HashMap<String,CuishouBean> mapcz1=new HashMap<String,CuishouBean>();
			HashMap<String,CuishouBean> mapzz2=new HashMap<String,CuishouBean>();

	        for(SearchHit hit : hitshk2.hits() ){	
	      	  String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
	    	  String tv4name=((HashMap)hit.getSource().get("body")).get("tv4name").toString();
	    	  String tv3name=((HashMap)hit.getSource().get("body")).get("tv3name").toString();	
	    	  if("信用卡还款".equals(tv4name) ){
	    		  cardset.add(cardtmp);
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
	    	  }
	    	  if("转账汇款".equals(tv3name) ){
	    		  cardset.add(cardtmp);
	    		  if(mapzz2.containsKey(cardtmp)){
	    			  CuishouBean bean = mapzz2.get(cardtmp);
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
	    	  }
	    	  if("话费充值".equals(tv3name) ){
	    		  
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
				    	  mobileset.add(cardtmp);
				    	  mapcz1.put(cardtmp, bean);
				    	  System.out.println("被充值手机："+cardtmp);
				    	  //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
				  }
	    	  }
	    	  if("公缴费-煤气水电".equals(tv4name) ){
		    	  if(mapgj1.containsKey(cardtmp)){
					  CuishouBean bean = mapgj1.get(cardtmp);
					  bean.setTimes(bean.getTimes()+1);					  
				  }
				  else{
			    	  CuishouBean bean= new CuishouBean(); 
			    	  bean.setMobile(((HashMap)hit.getSource().get("body")).get("mobile").toString());
			    	  bean.setAddress(((HashMap)hit.getSource().get("body")).get("address").toString());
			     	  bean.setRdate(((HashMap)hit.getSource().get("body")).get("ddate").toString());			 
			    	  bean.setType("公缴");
			    	  bean.setTimes(1);
			    	  bean.setSubType("公缴");
			    	  mapgj1.put(cardtmp, bean);			    	  
				      //String cardtmp=((HashMap)hit.getSource().get("body")).get("incdno").toString();
				  }
	    	  }
	      	  //System.out.println("idno"+idmobile);
	        }
	        
	    //求卡的手机号
		qstr="";
		n=0;
		for(String cardhk:cardset){
			if("".equals(cardhk))
				continue;
			if(n==0)
				qstr=cardhk;
			else
				qstr=qstr+ " OR "+cardhk ;
			n++;
		}	
		System.out.println(qstr);
		QueryStringQueryBuilder cardm=QueryBuilders.queryStringQuery(qstr).field("cardno");	
		SearchResponse responsecard = client.prepareSearch("graph_card_mobile")
			        .setTypes("card_mobile")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setSearchType(SearchType.QUERY_THEN_FETCH)       
					.setQuery(cardm)
			        .setFrom(0).setSize(100).setExplain(true)		       
			        .execute()
			        .actionGet();
		  SearchHits hitcard = responsecard.getHits();
	      System.out.println("求卡的手机号记录条数=" + hitcard.getTotalHits());
	      HashMap<String,String> mapMobile=new HashMap<String,String>();

	      for(SearchHit hit : hitcard.hits() ){	    	  
	    	  String cardtmp=((HashMap)hit.getSource().get("body")).get("cardno").toString();
	    	  String mobiletmp=((HashMap)hit.getSource().get("body")).get("mobile").toString();
		      mapMobile.put(cardtmp, mobiletmp);
		      mobileset.add(mobiletmp);
		      System.out.println(cardtmp+"："+mobiletmp);
	      } 
	     
	    //求用户名  
		qstr="";
		n=0;
		for(String cardhk:mobileset){
			if("".equals(cardhk))
				continue;
			if(n==0)
				qstr=cardhk;
			else
				qstr=qstr+ " OR "+cardhk ;
			n++;
		}	
		System.out.println(qstr);
		QueryStringQueryBuilder mobileu=QueryBuilders.queryStringQuery(qstr).field("mobile");
		
		SearchResponse responsemobile = client.prepareSearch("graph_user")
			        .setTypes("user")
			        //.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        .setSearchType(SearchType.QUERY_THEN_FETCH)       
					.setQuery(mobileu)
			        .setFrom(0).setSize(100).setExplain(true)		       
			        .execute()
			        .actionGet();
		  SearchHits hituser = responsemobile.getHits();
	      System.out.println("求用户名  记录条数=" + hituser.getTotalHits());
	      HashMap<String,String> mapUser=new HashMap<String,String>();

	      for(SearchHit hit : hituser.hits() ){	    	  
	    	  String name=((HashMap)hit.getSource().get("body")).get("name").toString();
	    	  String mobiletmp=((HashMap)hit.getSource().get("body")).get("mobile").toString();
	    	  mapUser.put(mobiletmp,name );
	    	  System.out.println(mobiletmp+name );
		     
	      }     
	   System.out.println("ES查询结束");
	
		//转JSON	 
	   List<Entry<String,CuishouBean>> listgj	= getList(mapgj1);
	   List<Entry<String,CuishouBean>> listhk1	= getList(maphk1);
	   List<Entry<String,CuishouBean>> listhk2	= getList(maphk2);
	   List<Entry<String,CuishouBean>> listzz1	= getList(mapzz1);
	   List<Entry<String,CuishouBean>> listzz2	= getList(mapzz2);
	   List<Entry<String,CuishouBean>> listcz1	= getList(mapcz1);
	   
       try{
     	  builder =XContentFactory.jsonBuilder() ;
     	 builder.startObject() ;
     	  if(!mobile.equals(idmobile)){    	   
     		      
     		 builder.field("idmobile", idmobile)  ; 			
     	  }
        }catch(Exception e){
      	  e.printStackTrace();
        }
	    //生成结果json
	    String  result="";
	    try{
	       //System.out.println(listhk1.size());
	       //System.out.println(listhk1.get(0).getValue().getAddress());
	       int times=1;
	       for(Entry<String,CuishouBean> bean :listhk1){
	    	   System.out.println(bean.getValue().getRdate());
	    	   System.out.println(bean.getValue().getAddress());
	    	   builder.field("hk1_date"+times, bean.getValue().getRdate());  			
	    	   builder.field("hk1_address"+times, bean.getValue().getAddress());
	    	   builder.field("hk1_amt"+times, bean.getValue().getTotalAM())  
     			.field("hk1_name"+times, mapUser.get(mapMobile.get(bean.getValue().getCardno())))  
     			.field("hk1_mobile"+times, mapMobile.get(bean.getValue().getCardno()))  ;
     			System.out.println("hk1_address"+times);
	    	   times++; 
	       }
	       times=1;
	       for(Entry<String,CuishouBean> bean :listhk2){
	    	   builder
     			.field("hk2_date"+times, bean.getValue().getRdate()) 
     			.field("hk2_address"+times, bean.getValue().getAddress())
     			.field("hk2_amt"+times, bean.getValue().getTotalAM())  
     			.field("hk2_name"+times, mapUser.get(mapMobile.get(bean.getValue().getCardno())))  
     			.field("hk2_mobile"+times, mapMobile.get(bean.getValue().getCardno())) ;
     			
	    	   times++; 
	       }
	       
	       times=1;
	       for(Entry<String,CuishouBean> bean :listzz1){
	    	   builder
     			.field("zz1_date"+times, bean.getValue().getRdate()) 
     			.field("zz1_address"+times, bean.getValue().getAddress())
     			.field("zz1_amt"+times, bean.getValue().getTotalAM())  
     			.field("zz1_name"+times, mapUser.get(mapMobile.get(bean.getValue().getCardno())))  
     			.field("zz1_mobile"+times, mapMobile.get(bean.getValue().getCardno()));  
     			
	    	   times++; 
	       }
	       
	       times=1;
	       for(Entry<String,CuishouBean> bean :listzz2){
	    	   builder
     			.field("zz2_date"+times, bean.getValue().getRdate()) 
     			.field("zz2_address"+times, bean.getValue().getAddress())
     			.field("zz2_amt"+times, bean.getValue().getTotalAM())  
     			.field("zz2_name"+times, mapUser.get(mapMobile.get(bean.getValue().getCardno())))  
     			.field("zz2_mobile"+times, mapMobile.get(bean.getValue().getCardno())) ; 
     			
	    	   times++; 
	       }
       
	       times=1;
	       for(Entry<String,CuishouBean> bean :listcz1){
	    	   builder
     			.field("zz2_date"+times, bean.getValue().getRdate()) 
     			.field("zz2_address"+times, bean.getValue().getAddress())
     			.field("zz2_amt"+times, bean.getValue().getTotalAM())  
     			.field("zz2_name"+times, mapUser.get(bean.getValue().getName()))  
     			.field("zz2_mobile"+times, bean.getValue().getMobilenum()) ;      		
	    	   times++; 
	       }
	       
	       times=1;
	       for(Entry<String,CuishouBean> bean :listgj){
	    	   builder
     			.field("gj_date"+times, bean.getValue().getRdate()) 
     			.field("gj_address"+times, bean.getValue().getAddress())  
     			.endObject();
	    	   times++; 
	       }
	       //builder.startObject().field("sd","sdf").endObject();
	       
	 	   result=builder.string();
	    }catch(Exception o){
	 	   o.printStackTrace();
	    }
	    System.out.println(result);
	    return result;
	    //return hits.getAt(0).getSourceAsString();
	}
	
	//取前三个
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
