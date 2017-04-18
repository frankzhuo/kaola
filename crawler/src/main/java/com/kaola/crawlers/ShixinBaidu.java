package com.kaola.crawlers;

import java.util.List;
import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultLocalQueue;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "shixinbaidu",delay=5)
public class ShixinBaidu extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
    @Override
    public String[] startUrls() {
    	try{
    		System.out.println("baidu begin");
    	    SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	   
    	    filew=new FileWriter("/data/crawler/shixin-"+myFmt1.format(new Date())+".txt",true);
    		filec=new FileWriter("/data/crawler/shixinc-"+myFmt1.format(new Date())+".txt",true);
    		
//    		filew=new FileWriter("d://shixin.txt");
//    		filec=new FileWriter("d://shixinc.txt");
    	}catch(Exception o){
    		o.printStackTrace();
    	}
        return new String[]{"http://www.baidu.com"};
    }
//    @Override
//    public String proxy() {
//        String[] proxies = new String[]{"http://10.5.28.14:5656"};
//        return proxies[RandomUtils.nextInt()%proxies.length];
//    }
    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            for (long i=0;i<2000;i=i+50){
            	System.out.println(i);
                //push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=50&ie=utf-8&oe=utf-8&format=json&t=1462258871342&cb=jQuery11020023530175070375414_1462258829039&_=1462258829042","getData").setSkipDuplicateFilter(true));
                //push(new Request("https://220.181.112.244/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=50&ie=utf-8&oe=utf-8&format=json&t="+new Date().getTime(),"getData").setSkipDuplicateFilter(true));

            	push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=50&ie=utf-8&oe=utf-8&format=json&t="+new Date().getTime(),"getData").setSkipDuplicateFilter(true));
                                  //https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn=160&rn=10&ie=utf-8&oe=utf-8&format=json&t=1468818644387&cb=jQuery110205820257593877614_1468818560017&_=1468818560029
                TimeUnit.SECONDS.sleep(8); 
                //push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=50&ie=utf-8&oe=utf-8&format=json&t=1455870490009&cb=jQuery1102049660134187316657_1455863650458&_=1455863650463","getData"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getData(Response response){
    	String  str=response.getContent();
    	int begin =str.indexOf("result\":");
    	int end =str.indexOf(", \"otherinfo\"");
    	
    	String jsonString=str.substring(begin+8, end);
    	//System.out.println(str);
    	//System.out.println(jsonString);
    			
    	//System.out.println(jsonString);
    	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	        try {
	            Gson gson = new Gson();
	            list = gson.fromJson(jsonString,
	                    new TypeToken<List<Map<String, String>>>() {
	                    }.getType());
	            for(int i=0;i<list.size();i++){
	            	String id=list.get(i).get("loc").substring(list.get(i).get("loc").indexOf("id=")+3);
//	            	System.out.println("*****************");
//	            	System.out.println(id);
//	            	System.out.println(list.get(i).get("iname"));
//	            	System.out.println(list.get(i).get("cardNum"));
//	            	System.out.println(list.get(i).get("caseCode"));
//	            	System.out.println(list.get(i).get("age"));
//	            	System.out.println(list.get(i).get("sexy"));
//	            	System.out.println(list.get(i).get("areaName"));
//	            	System.out.println(list.get(i).get("courtName"));
//	            	System.out.println(list.get(i).get("duty"));
//	            	System.out.println(list.get(i).get("performance"));
//	            	System.out.println(list.get(i).get("disruptTypeName"));
//	            	System.out.println(list.get(i).get("publishDate"));
//	            	System.out.println(list.get(i).get("partyTypeName"));
//	            	System.out.println(list.get(i).get("businessEntity"));
//	            	System.out.println(list.get(i).get("gistId"));
//	            	System.out.println(list.get(i).get("regDate"));
//	            	System.out.println(list.get(i).get("gistUnit"));
//	            	System.out.println(list.get(i).get("performedPart"));
//	            	System.out.println(list.get(i).get("unperformPart"));
	            	if(list.get(i).get("cardNum").length()>11){//个人,在百度搜索"失信人被执行人"关键字,重起后又继续爬取
		            	filew.write(id+"\001"+id+"\001"+list.get(i).get("iname")+"\001"+list.get(i).get("caseCode")+"\001"+list.get(i).get("age")+"\001"+list.get(i).get("sexy")+"\001"
		            			+list.get(i).get("cardNum")+"\001"+list.get(i).get("courtName")+"\001"+list.get(i).get("areaName")+"\001"+list.get(i).get("gistId")+"\001"
		            			+list.get(i).get("regDate")+"\001"+list.get(i).get("gistUnit")+"\001"+list.get(i).get("duty")+"\001"+list.get(i).get("performance")+"\001"
		            			+list.get(i).get("disruptTypeName")+"\001"+list.get(i).get("publishDate")+"\001"+list.get(i).get("_update_time")+"\001"+list.get(i).get("partyTypeName")+"\n\r");
		            	filew.flush();
	            	}
	            	else{
	            		filec.write(id+"\001"+id+"\001"+list.get(i).get("iname")+"\001"+list.get(i).get("caseCode")+"\001"+list.get(i).get("businessEntity")+"\001"
	    		            	+list.get(i).get("cardNum")+"\001"+list.get(i).get("courtName")+"\001"+list.get(i).get("areaName")+"\001"+list.get(i).get("gistId")+"\001"
	    		            	+list.get(i).get("regDate")+"\001"+list.get(i).get("gistUnit")+"\001"+list.get(i).get("duty")+"\001"+list.get(i).get("performance")+"\001"
	    		            	+list.get(i).get("disruptTypeName")+"\001"+list.get(i).get("publishDate")+"\001"+list.get(i).get("_update_time")+"\001"+list.get(i).get("partyTypeName")+"\n\r");
                        filec.flush();
	            	}
	            }
	            
	        } catch (Exception e) {
	        	e.printStackTrace();
	            // TODO: handle exception
	      }

    }
    
  @Scheduled(cron = "0 29 * * * ?")
  public void callByCron(){
      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
      Date dd=new Date();
      
      try {
    	  try{
    	      filew.close();
    	      filec.close();
    	  }
    	  catch(Exception o){
    		  o.printStackTrace();
    	  }
     	  SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");    	   
    	  filew=new FileWriter("/data/crawler/shixin-"+myFmt1.format(new Date())+".txt",true);
    	  filec=new FileWriter("/data/crawler/shixinc-"+myFmt1.format(new Date())+".txt",true);
    	  //DefaultLocalQueue queue=new DefaultLocalQueue();
    	  //this.setQueue(queue);
          for (long i=0;i<2000;i=i+50){
          	  System.out.println(i);
              //push(new Request("https://220.181.112.244/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=50&ie=utf-8&oe=utf-8&format=json&t="+dd.getTime(),"getData").setSkipDuplicateFilter(true));

              //push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=50&ie=utf-8&oe=utf-8&format=json&t=1455870490009&cb=jQuery1102049660134187316657_1455863650458&_=1455863650463","getData").setSkipDuplicateFilter(true));
               push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=50&ie=utf-8&oe=utf-8&format=json&t="+dd.getTime(),"getData").setSkipDuplicateFilter(true));
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
      // 可定时发送一个Request
      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
  }
  
  public static void main(String[] args) {
	  Date dd=new Date();
	 String t = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+1+"&rn=50&ie=utf-8&oe=utf-8&format=json&t="+dd.getTime();
     System.out.println(t);
  }
}