package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "dianpingshanghuvalid", delay = 15)
public class DianPingShangHuValidSpider extends BaseSeimiCrawler {

	/**
	 * 大宗点评 根据shopid 获取点评信息    
	 */
	private BufferedWriter file = null;
	private BufferedReader fileReader = null;
    private static boolean flag= true;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("/data/crawler/dianping/dianpingshanghuvalid.txt", true));
//			fileReader = new BufferedReader(new FileReader("d://dianpingshanghu.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.dianping.com/" };
	}

	@Override
	public void start(Response response) {
		int cityid=4445;
		//int cityid=1;
		
		try {
			for(int i=1;i<= cityid;i++){
				flag = false; //未完成
			    push(new Request("http://www.dianping.com/shopall/"+i+"/0", "callPageNum").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
				
				while(!flag){//未完成则继续等
					Thread.sleep(10*1000);
				}
				System.out.println("------yunxing zhi ---------"+i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
	private static int count =1;
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callPageNum(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
		try {
			 List<Object> list = doc.sel("//div[@class='main_w']//div[@class='content_b']/div[@class='box shopallCate']");
			 
			 List<String> typeList= new ArrayList<String>();
			 String h2text_globle = "";
			 
			 for (Object tmp : list) {
				   Element el = (Element) tmp;
//		           System.out.println(tmp.toString());
		           List<Element> div_child= el.children();
	        	   
		           if(div_child.size()>0){
		        	   for(Element child :div_child){
		        		   if("h2".equals(child.tag().toString())){
		        			   String h2text = child.text();
		        			   h2text_globle = h2text;
		        			   if(!("分类".equals(h2text) || "商区".equals(h2text))){
		        				   break;
		        			   }
		        			   
		        		   }else if("dl".equals(child.tag().toString())){
		        			   List<Element> Bravia = child.getElementsByAttributeValue("class","Bravia");
		        			   List<Element> B = child.getElementsByAttributeValue("class","B");
		        			   
		        			   if("分类".equals(h2text_globle)){
		        				   if(B.size()>0){
			        				   for(Element b:B){
			        					   typeList.add("http://www.dianping.com"+b.attr("href"));
			        					   System.out.println("http://www.dianping.com"+b.attr("href"));
			        				   }
			        			   }else{
			        				   for(Element br:Bravia){
			        					   typeList.add("http://www.dianping.com"+br.attr("href"));
			        					   System.out.println("http://www.dianping.com"+br.attr("href"));
			        				   }
			        			   }
		        			   }else if("商区".equals(h2text_globle)){
		        				   //与商家进行笛卡尔积
		        				   
//		        				   if(B.size()>0){
//			        				   for(Element b:B){
//			        					   for(String str:typeList){
//			        						   String[] ttt = b.attr("href").split("/");
//			        						   String result = str+ttt[ttt.length-1];
//			        						   file.write(result+"\n");
//			        						   System.out.println(result);
//			        					   }
//			        				   }
//			        			   }else{
			        				   for(Element br:Bravia){
			        					   for(String str:typeList){
			        						   String[] ttt = br.attr("href").split("/");
			        						   String result = str+ttt[ttt.length-1];
			        						   file.write(result+"\n");
			        						   System.out.println(count++);
			        					   }
			        				   }
//			        			   }
		        			   }
		        		   }
		        	   }
		        	   
		        	  file.flush();
		        	   
		           }
		           
			 }
			 
			 flag =true;/////上一个商户id的评论已完成
		} catch (Exception e) {
			flag =true;/////上一个商户id的评论已完成
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
		try {
			 List<Object> list = doc.sel("//div[@class='main']//div[@class='comment-list']/ul/li");
			 
			 String shanghuID = url.split("/")[4];

			for (Object tmp : list) {
		            Element el = (Element) tmp;
		            List<Element> child_div = el.children();
		            
		            //div1
		            List<Element> child_p= child_div.get(0).getElementsByAttributeValue("class","name");
		            
		            if(child_div.size()>1){
		            	String publishName = "";
		            	if(child_p.get(0).children().size()>0){
		            		Element p_a  = child_p.get(0).child(0);
		            		publishName =p_a.text()+"\002"+ p_a.attr("href");
		            	}
			            
			            //div2/user-info
			            List<Element> child_user_info= child_div.get(1).getElementsByAttributeValue("class","user-info");
			            String user_info_pj ="";
			            String user_info_comment ="";
			            if(child_user_info.get(0).children().size()>0){
			            	user_info_pj = child_user_info.get(0).child(0).attr("title");
			            }
			            if(child_user_info.get(0).children().size()>1){
			            	user_info_comment = child_user_info.get(0).child(1).text();
			            }
			            String user_info_pingjia=user_info_pj +"\002"+user_info_comment;
			            
			            //div2/content
			            List<Element> child_comment_txt= child_div.get(1).getElementsByAttributeValue("class","comment-txt");
			            String comment_txt_content ="";
			            if(child_comment_txt.get(0).children().size()>0){
			            	comment_txt_content = child_comment_txt.get(0).child(0).text();
			            }
			            
			            //div2/publishDate or name
			            List<Element> child_misc_info= child_div.get(1).getElementsByAttributeValue("class","misc-info");
			            String misc_info_date ="";
			            String misc_info_name ="";
			            if(child_misc_info.get(0).children().size()>0){
			            	misc_info_date = child_misc_info.get(0).child(0).text();
			            	if(misc_info_date.length()==5){
			            		Calendar cal =  Calendar.getInstance();
			            		String year = cal.get(Calendar.YEAR)+"";
			            		misc_info_date  = year.substring(2)+"-"+misc_info_date;
			            	}
			            }
			            if(child_misc_info.get(0).children().size()>1){
			            	misc_info_name = child_misc_info.get(0).child(1).text();
			            }
			            
			            String str = shanghuID+"\001"+publishName+"\001"+user_info_pingjia+"\001"+comment_txt_content+"\001"+misc_info_date+"\001"+misc_info_name+"\n";
			            System.out.println(str);
			            
			            file.write(str);
			            file.flush();
		            }
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
