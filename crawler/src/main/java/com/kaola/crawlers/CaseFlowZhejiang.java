package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "caseflowzhejiang")

public class CaseFlowZhejiang extends BaseSeimiCrawler {
	private BufferedWriter  filew=null;
	private BufferedWriter  filee=null;
	
	private int page=0;
	private int num=0;
    @Override
    public String[] startUrls() {
    	try{
    		//filew=new BufferedWriter(new FileWriter("d:\\caseflowzhejiang.txt"));
       		filew=new BufferedWriter(new FileWriter("/data/crawler/ttcaseflowzhejiang2015.txt"));
       		filee=new BufferedWriter(new FileWriter("/data/crawler/caseflowerror.txt",true));
    	}catch(Exception o){
    		
    	}
        return new String[]{"http://www.zjsfgkw.cn"};
    }
    @Override
    public void handleErrorRequest(Request request) {
    	try{
    		filee.write(request.getUrl());
    		filee.flush();
    	}catch(Exception o){
    		o.printStackTrace();
    	}
    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            for (long i=1;i<23693;i++){
            	System.out.println("ok"+i);
            	HashMap<String,String> map=new HashMap<String,String>();
            	map.put("PageNo",i+"");
            	map.put("PageSize", 5+"");
            	map.put("AH_NH", "2015");
            	map.put("AH_BH", "");
            	map.put("DSR", "");               
                push(new Request("http://www.zjsfgkw.cn/Execute/ExecuteCaseSearchList","getData").setHttpMethod(HttpMethod.POST).setParams(map).setSkipDuplicateFilter(true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public void getData(Response response){	
        	page++;
        	String url=response.getRealUrl();
        	
        	System.out.println(response.getRealUrl());
        	System.out.println(page);
        	String  str=response.getContent();
        	System.out.println(str);
          	int begin =str.indexOf("{\"informationmodels\":");
        	int end =str.indexOf(",\"total\":");
        	
        	String jsonString=str.substring(begin+21, end);
        	
    	    System.out.println(jsonString);
    	  
    	    JXDocument doc = response.document();    	    	    
	    	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	        try {
	            Gson gson = new Gson();
	            list = gson.fromJson(jsonString,
	                    new TypeToken<List<Map<String, String>>>() {
	                    }.getType());
	            for(int i=0;i<list.size();i++){
	            	//String id=list.get(i).get("loc").substring(list.get(i).get("loc").indexOf("id=")+3);
                   	  
//	    	        //案号  类别  案由 立案日期 当事人  法庭  状态
	            	System.out.println(list.get(i).get("AH")+"\001 \001 \001"+list.get(i).get("LARQ")+"\001 申请执行人："+list.get(i).get("ExecuteDSR")+" 被申请执行人："+list.get(i).get("ExecutedDSR")+"\001"
                       +list.get(i).get("CourtName")+"\001"+list.get(i).get("AJZT")+"\n\r");	
	            	filew.write(list.get(i).get("AH")+"\001 \001 \001"+list.get(i).get("LARQ")+"\001 申请执行人："+list.get(i).get("ExecuteDSR")+" 被申请执行人："+list.get(i).get("ExecutedDSR")+"\001"
	                        +list.get(i).get("CourtName")+"\001"+list.get(i).get("AJZT")+"\n\r");		            	
	            	filew.flush();
	            }
         
	    }catch(Exception o){
	    	o.printStackTrace();
	    }
            //logger.info("{}", urls.size());
            
    	  
    }
//  @Scheduled(cron = "0/5 * * * * ?")
//  public void callByCron(){
//      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
//   	try{
//		  filew=new FileWriter("d:\\shixin.txt");
//		  filec=new FileWriter("d:\\shixinc.txt");
//		  //DefaultLocalQueue qq=new DefaultLocalQueue();
//		  //this.setQueue(qq);
//	      for (long i=0;i<2000;i=i+50){
//          	System.out.println(i);
//          	 //push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//              push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=10&ie=utf-8&oe=utf-8&format=json&t=1455870490009&cb=jQuery1102049660134187316657_1455863650458&_=1455863650463","getData").setSkipDuplicateFilter(true));
//          }
//		
//	}catch(Exception o){
//		
//	}
//      // 可定时发送一个Request
//      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//  }

}
