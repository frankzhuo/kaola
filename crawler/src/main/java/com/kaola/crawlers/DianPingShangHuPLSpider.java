package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "dianpingshanghupl", delay = 15)
public class DianPingShangHuPLSpider extends BaseSeimiCrawler {

	/**
	 * 大宗点评 根据shopid 获取点评信息    
	 */
	private BufferedWriter file = null;
	private BufferedReader fileReader = null;
    private static boolean flag= true;
	
//    @Override
//    public String proxy() {
//        String[] proxies = new String[]{
//        		"http://119.29.54.15:80",
//        		"http://119.29.191.76:80",
//        		"http://101.201.143.54:80",
//        		"http://119.29.151.68:80",
//        		"http://119.29.151.68:80"};
//        return proxies[RandomUtils.nextInt()%proxies.length];
//    }
    
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("/data/crawler/dianping/pinglun.txt", true));
			fileReader = new BufferedReader(new FileReader("/data/crawler/dianping/shanghuid.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.dianping.com/" };
	}

	@Override
	public void start(Response response) {
//		String shanghuID= "3539560";
		String shanghuID= "512064";
		
		String line="";
		try {
			while((line = fileReader.readLine()) !=null){
				String[] splits = line.split("\001");
				shanghuID = splits[0];
				if(!"".equals(shanghuID) && shanghuID!=null){
					flag = false; //未完成
					push(new Request("http://www.dianping.com/shop/"+shanghuID+"/review_more?pageno=1", "callPageNum").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
				}
				
				while(!flag){//未完成则继续等
					Thread.sleep(10*1000);
				}
				System.out.println("---------------"+shanghuID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callPageNum(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
		//System.out.println(response.getContent());
		try {
			 List<Object> list = doc.sel("//div[@class='main']//div[@class='Pages']/div[@class='Pages']/a[@class='PageLink']/text()");
			 
             int pageNum  = 0;
			 if(list.size()>0){
				 pageNum = Integer.parseInt((String) list.get(list.size()-1)); 
             }
			 
			 callback(response);
			 
			 // pageNum  = 6;
			 System.out.println(url+"---商户评论页数共---"+pageNum);
			 
			 for (int i = 2; i <= pageNum; i++) {
				 Thread.sleep(15*1000);
				 push(new Request(url.split("=")[0]+"="+i, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
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
		            //System.out.println(tmp.toString());
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
	
	 /**
     * UA库可以自行查找整理，这里仅为样例
     * @return
     */
    @Override
    public String getUserAgent(){
    	return "Mozilla/5.0 (X11; Linux x86_64; rv:45.0) Gecko/20100101 Firefox/45.0";
    }
}
