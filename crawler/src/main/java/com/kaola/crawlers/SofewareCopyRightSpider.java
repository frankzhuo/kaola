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
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;


@Crawler(name = "softwareCRSpider",delay=8)
public class SofewareCopyRightSpider extends BaseSeimiCrawler {
		private BufferedWriter  filew=null;
		private BufferedWriter  filed=null;
		private static boolean flag = true;
		
		private int page=0;
		private int num=0;
	    @Override
	    public String[] startUrls() {
	    	try{
	    		filew=new BufferedWriter(new FileWriter("/data/crawler/sofeware20161121.txt"));
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
	        try {    
	           	File file=new File("/data/crawler/huangyewangbeijing.txt");
	        	BufferedReader reader=new BufferedReader(new FileReader(file));
	        	String tmp="";
	        	InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),"UTF-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i=0;
                while((tmp = bufferedReader.readLine()) != null){
                	  flag =false;
                	  
                	  String data[]=tmp.split("\001");
                	  System.out.println(tmp);
                	  if(data.length<1)
                		  continue;
                	  System.out.println(data[0]);
	            	  System.out.println("http://www.ccopyright.com.cn/cpcc/RRegisterAction.do?method=list&no=fck&sql_name=&sql_regnum=&sql_author="+java.net.URLEncoder.encode(data[0],"GBK")+"&curPage=1");
	            	  HashMap<String,String> tran =new HashMap();
	            	  tran.put("name", data[0]);
	            	  push(new Request("http://www.ccopyright.com.cn/cpcc/RRegisterAction.do?method=list&no=fck&sql_name=&sql_regnum=&sql_author="+(java.net.URLEncoder.encode(data[0],"GBK"))+"&curPage=1","getUrls").setTrans(tran).setSkipDuplicateFilter(false));
	                  //push(new Request("http://www.bjcourt.gov.cn/ktgg/index.htm?court="+j+"&dateRange="+dstr+"&start="+dstr+"&end="+dstr+"&type&p=2","getUrls").setHttpMethod(HttpMethod.POST).setSkipDuplicateFilter(true));	
	            	  i ++ ;
//	            	  if(i%10==0){
//	            		  Thread.sleep(10*1000);
//	            		  System.out.println("SofewareCopyRightSpider Thread is sleep :"+i);
//	            	  }
	            	  int fn=0;
	            	  while (!flag){
	            		  Thread.sleep(1*1000);
	            		  System.out.println("SofewareCopyRightSpider Thread is sleep :"+i);
	            		  fn++ ;
	            		  if (fn >10){
	            			  flag =true;
	            			  System.out.println("SofewareCopyRightSpider waiting for :"+fn);
	            		  }
	            	  }
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
	    	}
//	    	else{
//	    		flag=true;
//	    		//return;
//	    	}
	    	
	    	
	    	try{
	    		for(int i=2;i<Integer.parseInt(num)+1 ;i++){
	             	  HashMap<String,String> tran =new HashMap();
		            	  tran.put("name", response.getTrans().get("name"));
		              try{
		                  push(new Request("http://www.ccopyright.com.cn/cpcc/RRegisterAction.do?method=list&no=fck&sql_name=&sql_regnum=&sql_author="+(java.net.URLEncoder.encode(response.getTrans().get("name"),"GBK"))+"&curPage="+i,"getAll").setTrans(tran).setSkipDuplicateFilter(false));
		              }catch(Exception o){
		            	  flag=true;
	                  }finally {
	                	  flag=true;
					}
		        }
	    	}
	    	catch (Exception e){
	    		flag=true;
	    	}finally {
	    		flag=true;
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

	    }
	
	}