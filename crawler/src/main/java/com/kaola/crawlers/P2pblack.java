package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "p2p" ,delay=1)

public class P2pblack extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;
		
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	    		//filew=new BufferedWriter(new FileWriter("d:\\p2p.txt"));
	    		filew=new BufferedWriter(new FileWriter("/data/crawler/p2p.txt",true));
	    		
	    	}catch(Exception o){
	    		
	    	}
	        return new String[]{"http://www.p2pblack.com"};
	    }
	    @Override
	    public void start(Response response) {
	    	//System.out.println(response.getContent());
	        JXDocument doc = response.document();
	        
	        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	        try {
	        	     
	            for (long i=1;i<900000;i++){
	            	System.out.println("ok"+i);              
	                push(new Request("http://www.p2pblack.com/pageHome.html","getData").setSkipDuplicateFilter(true));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	        public void getData(Response response){	
	        	page++;
	        	String url=response.getRealUrl();
	     	    JXDocument doc = response.document();
	    	    try{
                  //姓名 证件号码 借款金额 逾期本金 状态 平台
	    	      List<Object> lista = doc.sel("//div[@class='index_blackname_left']//a/@href");
	    	      List<Object> list1 = doc.sel("//div[@class='index_blackname_left']//span[@class='index_blk1']/text()");
	    	      List<Object> list2 = doc.sel("//div[@class='index_blackname_left']//span[@class='index_blk2']/text()");
	    	      List<Object> list3 = doc.sel("//div[@class='index_blackname_left']//span[@class='index_blk3']/text()");
	    	      List<Object> list4 = doc.sel("//div[@class='index_blackname_left']//span[@class='index_blk4']/text()");
	    	      List<Object> list5 = doc.sel("//div[@class='index_blackname_left']//span[@class='index_blk5']/text()");
	      	      List<Object> list6 = doc.sel("//div[@class='index_blackname_left']//a[@target='_blank']/text()");

		          for(int i=1;i<list1.size();i++){		            	 
	                 filew.write(lista.get(2*(i-1))+"\001"+list1.get(i)+"\001"+list2.get(i)+"\001"+list3.get(i)+"\001"
		                        +list4.get(i)+"\001"+list5.get(i)+"\001"+list6.get(i-1)+"\n\r");		            	
		             filew.flush();
		          }
		            	//String id=list.get(i).get("loc").substring(list.get(i).get("loc").indexOf("id=")+3);
	                   	  
/*//		    	        //案号  类别  案由 立案日期 当事人  法庭  状态
		            	System.out.println(list.get(i).get("AH")+"\001 \001 \001"+list.get(i).get("LARQ")+"\001 申请执行人："+list.get(i).get("ExecuteDSR")+" 被申请执行人："+list.get(i).get("ExecutedDSR")+"\001"
	                       +list.get(i).get("CourtName")+"\001"+list.get(i).get("AJZT")+"\n\r");	
		            	filew.write(list.get(i).get("AH")+"\001 \001 \001"+list.get(i).get("LARQ")+"\001 申请执行人："+list.get(i).get("ExecuteDSR")+" 被申请执行人："+list.get(i).get("ExecutedDSR")+"\001"
		                        +list.get(i).get("CourtName")+"\001"+list.get(i).get("AJZT")+"\n\r");		            	
		            	filew.flush();*/
		            //}
	         
		    }catch(Exception o){
		    	o.printStackTrace();
		    }
	            //logger.info("{}", urls.size());
	            
	    	  
	    }
	//  @Scheduled(cron = "0/5 * * * * ?")
	//  public void callByCron(){
//	      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
//	   	try{
//			  filew=new FileWriter("d:\\shixin.txt");
//			  filec=new FileWriter("d:\\shixinc.txt");
//			  //DefaultLocalQueue qq=new DefaultLocalQueue();
//			  //this.setQueue(qq);
//		      for (long i=0;i<2000;i=i+50){
//	          	System.out.println(i);
//	          	 //push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//	              push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=10&ie=utf-8&oe=utf-8&format=json&t=1455870490009&cb=jQuery1102049660134187316657_1455863650458&_=1455863650463","getData").setSkipDuplicateFilter(true));
//	          }
//			
//		}catch(Exception o){
//			
//		}
//	      // 可定时发送一个Request
//	      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
	//  }

	}
