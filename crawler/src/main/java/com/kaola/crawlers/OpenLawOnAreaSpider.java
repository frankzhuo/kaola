package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "openlawonarea", delay = 15)
public class OpenLawOnAreaSpider extends BaseSeimiCrawler {

	/**
	 *  
	 */
	private BufferedWriter file = null;
    private static boolean flag= true;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://openlawonarea.txt", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.openlaw.cn/" };
	}

	@Override
	public void start(Response response) {
		
		try {
			for(int i=1;i<= 1;i++){
				flag = false; //未完成
				
				HashMap<String,String> map=new HashMap<String,String>();
				
				map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				map.put("Accept-Encoding","gzip, deflate, sdch");
				map.put("Accept-Language","zh-CN,zh;q=0.8");
				map.put("Cache-Control","max-age=0");
				map.put("Connection","keep-alive");
				map.put("Cookie", "j_token=7an_p54c9e92bba9da6fa9ce730a200125d7; JSESSIONID=2B7A04E0C548835C27E82337019450D4; Hm_lvt_a105f5952beb915ade56722dc85adf05=1473305528,1473646600,1473666747,1473818562; Hm_lpvt_a105f5952beb915ade56722dc85adf05=1473823512");
				map.put("Host","openlaw.cn");
				map.put("Upgrade-Insecure-Requests","1");
				map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

				push(new Request("http://openlaw.cn/judgement/c3ff0f66b9584c4194713d359e09d9b2", "callPageNum").setSkipDuplicateFilter(false).setParams(map).setHttpMethod(HttpMethod.GET));
				
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
		System.out.println(url);
		try {

			System.out.println(response.getContent());
			
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
