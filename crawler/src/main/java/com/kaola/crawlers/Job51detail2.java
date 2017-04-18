package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;


//@Crawler(name = "job51")
@Crawler(name = "jobdetail2",delay=1)
//@Crawler(name = "job51",delay=1,proxy = "http://10.5.13.22:3127")
public class Job51detail2 extends BaseSeimiCrawler {
		//private FileWriter  filew=null;
		private FileWriter  filed=null;
		private FileWriter  filejobinfo=null;
		BufferedReader reader = null;
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	    	  	   
	    		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-ddHH");    	   
	    	    //filew=new FileWriter("/data/crawler/51job-"+myFmt1.format(new Date())+".txt",true);
	    		filed=new FileWriter("/data/crawler/51jobdetail-"+myFmt1.format(new Date())+".txt",true);
	    		filejobinfo=new FileWriter("/data/crawler/51jobinfo-"+myFmt1.format(new Date())+".txt",true);
	            reader = new BufferedReader(new FileReader("/data/crawler/51job/xaa"));

//	    		filew=new FileWriter("d:\\51job.txt");
//	    		filed=new FileWriter("d:\\51jobdetail.txt");
//	    		filejobinfo=new FileWriter("d:\\51jobinfo-"+myFmt1.format(new Date())+".txt");

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
            System.out.println("oo");
            
            String tempString = null;  
            try{
    	        while ((tempString = reader.readLine()) != null) {
    	        //System.out.println(doc.);       	 
    	                //System.out.println(tempString);   
    	                String strs[]=tempString.split("\\0001");
    	                //TimeUnit.MILLISECONDS.sleep(32000);
    	                // push(new Request(tempString,"getData"));
    	                System.out.println("strs"+strs.length);
    	                if(strs.length>1)
    	                	push(new Request(strs[0],"getUrls"));
    	                //TimeUnit.SECONDS.sleep(1);

    	            	//System.out.println(response.getRealUrl());
    	  	     }
            } catch (Exception e) {
            	System.out.println("page!!!");
                e.printStackTrace();
                
            }
           
	    }
	    public void getUrls(Response response){
	    	String  str=response.getContent();
	    	//System.out.println(str);
	    	//response.getHttpResponse().getFirstHeader("")
	    	System.out.println("***"+response.getRealUrl());
	        JXDocument doc = response.document();
	        try {	        
	            //List<Object> urls = doc.sel("//ul[@class='ul_news_long']//a/@href");	            
	        	//主体
	            List<Object> name = doc.sel("//div[@class='tHeader tHCop']//h1/text()");
//	            List<Object> property = doc.sel("//div[@class='tHeader tHCop']//p[@class='ltype']/text()");
//	            List<Object> detail = doc.sel("//div[@class='con_msg']//div[@class='in']/p/text()");
//	            List<Object> address = doc.sel("//div[@class='tBorderTop_box']//div[@class='bmsg inbox']/p/text()");
	            if(name==null || name.size()==0){
	            	System.out.println("没有该企业");
	            	return ;
	            }
//	            //System.out.println(name.get(0));
//	            //System.out.println(property.get(0));
//	            //System.out.println(detail.get(0));
//	            String add="";
//	            if(address.size()!=0){
//	            	add=address.get(0).toString();
//	                //System.out.println(address.get(0));
//	            }
	            //System.out.println(add);
	            List<Object> position = doc.sel("//input[@id='hidTotal']/@value");

            	if(position.size()==0)
            		return ;
            	System.out.println("position"+position.get(0));
                int total=Integer.parseInt(position.get(0).toString()	);	
	            //		<input type="hidden" id="hidTotal" name="hidTotal" value="126">
                
	            for(int i=1;i<total/20+2;i++){    
	            	System.out.println("total"+(total/20+2));
	            	HashMap<String,String> map=new HashMap<String,String>();
	            	map.put("pageno",i+"");
	            	map.put("hidTotal", total+"");       
	            	HashMap<String,String> map1=new HashMap<String,String>();
	            	map1.put("name", name.get(0).toString());
	            	System.out.println("url"+response.getRealUrl());
	                push(new Request(response.getRealUrl(),"getData").setHttpMethod(HttpMethod.POST).setParams(map).setTrans(map1).setSkipDuplicateFilter(true));
	            	//System.out.println(response.getRealUrl());
	  	         }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	        public void getData(Response response){	
	        	
	        	//System.out.println("%%%%%%%%%%%%%%%%%%%%%%55");
	        	String url=response.getRealUrl();
	        	
	        	//System.out.println(response.getRealUrl());
	        	//System.out.println(page);
	        	String  str=response.getContent();
	    
	    	    System.out.println("三级"+url);
	    	    //System.out.println(response.getTrans().toString());
	    	    //System.out.println("*********"+response.getTrans().get("name"));
	    	    JXDocument doc = response.document();
	    	    try{
		    	    List<Object> position = doc.sel("//div[@class='el']/p/a/text()");
		    	    List<Object> urls = doc.sel("//div[@class='el']/p/a/@href");
	    	        List<Object> require = doc.sel("//div[@class='el']/span[@class='t2']/text()");
		    	    List<Object> address = doc.sel("//div[@class='el']/span[@class='t3']/text()");
		    	    List<Object> money = doc.sel("//div[@class='el']/span[@class='t4']/text()");
		    	    List<Object> jtime = doc.sel("//div[@class='el']/span[@class='t5']/text()");
		    	    
		    	    for(int i=0;i<position.size();i++){
		    	
//	            	  System.out.println(position.get(i));
//	           	      System.out.println(require.get(i));
//	            	  System.out.println(address.get(i));
//	            	  System.out.println(money.get(i));
//	            	  System.out.println(jtime.get(i));
	            	  
		    	        //案号  类别  案由 立案日期 当事人  法庭  状态
		            	//System.out.println(title+"\001"+court.get(0)+"\001"+content.get(0)+"\001"+openTime.get(0)+"\n\r");	
		    	        String jobinfo = 	urls.get(i)+"\001"+response.getTrans().get("name")+"\001"+position.get(i)+"\001"+require.get(i)+"\001"+address.get(i)+"\001"+money.get(i)+"\001"+jtime.get(i);
		    	        filed.write(jobinfo+"\n\r");	
		             	
		                filed.flush();
		              	HashMap<String,String> map1=new HashMap<String,String>();
		            	map1.put("info", jobinfo);
		                push(new Request(urls.get(i).toString(),"getInfo").setTrans(map1).setSkipDuplicateFilter(true));

		    	    }

	    	    }catch(Exception o){
	    	    	o.printStackTrace();
	    	    }
	            //logger.info("{}", urls.size());
	            

	    }
	    public void getInfo(Response response){	
	        	
	        	//System.out.println("%%%%%%%%%%%%%%%%%%%%%%55");
	        	String url=response.getRealUrl();
	        	
	        	System.out.println(url);
	        	//System.out.println(page);
	        	String  str=response.getContent();
	    
	    	    //System.out.println(str);
	    	    //System.out.println(response.getTrans().toString());
	        	String info=response.getTrans().get("info");
	    	    //System.out.println("*********"+info);
	    	    JXDocument doc = response.document();
	    	    try{
		    	    List<Object> city = doc.sel("//div[@class='tHeader tHjob']/div[@class='in']/div[@class='cn']/span[@class='lname']/text()");
		    	    if(city==null||city.size()==0){
		    	    	System.out.println("city null");
		    	    	return;
		    	    }
		    	    //System.out.println(city.get(0));

		    	    String jobinfos="";
		    	    List<Object> jobinfo = doc.sel("//div[@class='tCompany_main']/div[@class='tBorderTop_box bt']/div[@class='jtag inbox']/div[@class='t1']/span/text()");
    	            for(int i=0;i<jobinfo.size();i++)
    	            	jobinfos=jobinfos+jobinfo.get(i)+"|";
    	            //System.out.println(jobinfos);
				    List<Object> companyinfo = doc.sel("//div[@class='tCompany_main']/div[@class='tBorderTop_box bt']/div[@class='jtag inbox']/p[@class='t2']/span/text()");
    	            String cominfos="";
    	            for(int i=0;i<companyinfo.size();i++)
    	            	cominfos=cominfos+companyinfo.get(i)+"|";
    	            //System.out.println(cominfos);
		    	    
		    	    List<Object> jobdesc = doc.sel("//div[@class='tBorderTop_box']/div[@class='bmsg job_msg inbox']/text()");
		    	    
		    	    //System.out.println(jobdesc.get(0));
		    	    
		    	    List<Object>  zhiwei = doc.sel("//div[@class='tBorderTop_box']/div[@class='bmsg job_msg inbox']/div[@class='mt10']/p[@class='fp f2']/span/text()");
		    	    String zhiweistr="";
		    	    String key="";
		    	    int flag=0;
		    	    for(int i=0;i<zhiwei.size();i++){
		    	    	if(zhiwei.get(i).toString().equals("职能类别：")){
		    	    		flag=1;
		    	    		continue;
		    	    	}
		    	    	else if(zhiwei.get(i).toString().equals("关键字：")){
		    	    		flag=2;
		    	    		continue;
		    	    	}
		    	        if(flag==1)	
		    	    		zhiweistr=zhiweistr+zhiwei.get(i)+"|";
		    	        if(flag==2)	
		    	        	key=key+zhiwei.get(i)+"|";
		    	    	
		    	    }
		    	    //System.out.println(zhiweistr);
		    	    //System.out.println(key);
		    	    //System.out.println(zhiwei.toString());

		    		//System.out.println(title+"\001"+court.get(0)+"\001"+content.get(0)+"\001"+openTime.get(0)+"\n\r");	
	    	        String allJobInfo = info+"\001"+city.get(0)+"\001"+jobinfos+"\001"+cominfos+"\001"+jobdesc.get(0)+"\001"+zhiweistr+"\001"+key;
	    	        filejobinfo.write(allJobInfo+"\n\r");	       	
	    	        filejobinfo.flush();		        		    	    

	    	    }catch(Exception o){
	    	    	o.printStackTrace();
	    	    }
	            //logger.info("{}", urls.size());
	            
	    }
	}