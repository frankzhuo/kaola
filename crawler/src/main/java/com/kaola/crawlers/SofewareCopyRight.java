package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;


@Crawler(name = "softwareCR",delay=8)
public class SofewareCopyRight extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;
		private BufferedWriter  filed=null;
		
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	    		filew=new BufferedWriter(new FileWriter("/data/crawler/sofeware.txt"));
	    		//filed=new BufferedWriter(new FileWriter("d:\\51jobdetail.txt"));
	    		
	    	}catch(Exception o){
	    		
	    	}
	        return new String[]{"http://www.baidu.com"};
	    }
	    @Override
	    public String getUserAgent(){
	        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
	                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
	                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
	        };
	        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
	    }

	    @Override
	    public void start(Response response) {
            //System.out.println("oo");
	        try {    
	           	File file=new File("/data/softwarename.csv");
	        	BufferedReader reader=new BufferedReader(new FileReader(file));
	        	String tmp="";
	        	InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),"UTF-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i=0;
                while((tmp = bufferedReader.readLine()) != null){
            		  //System.out.println("oo2");
                	  //if(i++>0)
                		//  break;
                	  String data[]=tmp.split(",");
                	  System.out.println(tmp);
                	  if(data.length<3)
                		  continue;
                	  System.out.println(data[2]);
                	  //java.net.URLEncoder.encode(s, enc)
                	  System.out.println(java.net.URLEncoder.encode("小米","GBK"));	
                	  System.out.println("%D0%A1%C3%D7");
	            	  System.out.println("http://www.ccopyright.com.cn/cpcc/RRegisterAction.do?method=list&no=fck&sql_name=&sql_regnum=&sql_author="+java.net.URLEncoder.encode(data[2],"GBK")+" %D0%A1%C3%D7 &curPage=1");
	            	  System.out.println("%D0%A1%C3%D7");
	            	  //push(new Request("http://www.ccopyright.com.cn/cpcc/RRegisterAction.do?method=list&no=fck&sql_name=&sql_regnum=&sql_author=%D0%A1%C3%D7&curPage=1","getUrls"));
	            	  HashMap<String,String> tran =new HashMap();
	            	  tran.put("name", data[2]);
	            	  push(new Request("http://www.ccopyright.com.cn/cpcc/RRegisterAction.do?method=list&no=fck&sql_name=&sql_regnum=&sql_author="+(java.net.URLEncoder.encode(data[2],"GBK"))+"&curPage=1","getUrls").setTrans(tran));
	                  //push(new Request("http://www.bjcourt.gov.cn/ktgg/index.htm?court="+j+"&dateRange="+dstr+"&start="+dstr+"&end="+dstr+"&type&p=2","getUrls").setHttpMethod(HttpMethod.POST).setSkipDuplicateFilter(true));		                  
	                                
            	 }
	          
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    public void getUrls(Response response){
	    	String  str=response.getContent();    	
	    	//response.getHttpResponse().getFirstHeader("")
	    	//System.out.println(str);
	    	JXDocument doc = response.document();
	    	getData(doc,response.getTrans().get("name"));
	    	Pattern pa=Pattern.compile("共([0-9]+)页");
	    	Matcher match=pa.matcher(str);
	    	String num="0";    	
	    	if(match.find()){
	    		num=match.group(1);
	    		System.out.println("num"+num);
	    	}else
	    		return;
            for(int i=2;i<Integer.parseInt(num)+1 ;i++){
        		  //System.out.println("oo2");
             	  HashMap<String,String> tran =new HashMap();
	            	  tran.put("name", response.getTrans().get("name"));
	              try{
	                  push(new Request("http://www.ccopyright.com.cn/cpcc/RRegisterAction.do?method=list&no=fck&sql_name=&sql_regnum=&sql_author="+(java.net.URLEncoder.encode(response.getTrans().get("name"),"GBK"))+"&curPage="+i,"getAll").setTrans(tran));
	              }catch(Exception o){
                	  
                  }
	        }
	    }
    public void getAll(Response response){
    	String  str=response.getContent();    	
    	//response.getHttpResponse().getFirstHeader("")
    	//System.out.println(str);
    	JXDocument doc = response.document();
    	getData(doc,response.getTrans().get("name"));  
    }
    public void getData(JXDocument doc,String name ){	
	    try{
	      
    	    List<Object> tr = doc.sel("//table[@bordercolor='#CCCCCC']/tbody/node()");		    	    
    	    int trnum=0;
	    	for(Object  child:tr){
            	
            	Element el=(Element)  child;
            	//System.out.println(el.tagName());	               
            	//System.out.println(el.tagName());
            	if(el.tagName().equals("tr")){
            		  trnum++;
            		  if(trnum<3){
            			  	continue;
            		  }
            		  
	            	  System.out.println(el.child(0).text());
	            	  System.out.println(el.child(1).text());

            	  
	    	        //案号  类别  案由 立案日期 当事人  法庭  状态
	            	//System.out.println(title+"\001"+court.get(0)+"\001"+content.get(0)+"\001"+openTime.get(0)+"\n\r");	
	    	    	 filew.write(name+"\001"+el.child(0).text()+"\001"+el.child(1).text()+"\001"+el.child(2).text()+"\001"+el.child(3).text()+"\001"+el.child(4).text()+"\001"+el.child(5).text()+"\001"+el.child(6).text()+"\001"+el.child(7).text()+"\n");	
	    	    	 filew.flush();
            	}
    	    }

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