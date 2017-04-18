package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "qianshui", delay = 15)
public class QianShuiBeiJingSpider extends BaseSeimiCrawler {

	/**
	 *  
	 */
	private BufferedWriter file = null;
    private static boolean flag= true;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://qianshu.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://shiju.tax861.gov.cn/nsfw/qshcx/qsgg_index.asp" };
	}

	@Override
	public void start(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
//		System.out.println(response.getContent());
		
		
		try {
			List<Object> list = doc.sel("//table[@class='center_04']//tr/td[@valign='top']/a[last()]/@href");
			
			Set<String> set = new HashSet<String>();
			
			for (Object t :list) {
				set.add(t.toString());
			}
			
			for (String f : set) {
				
			}
			
			for(String i : set){
				flag = false; //未完成
				String href = "http://shiju.tax861.gov.cn/nsfw/qshcx/"+i;
				HashMap<String,String> map=new HashMap<String,String>();
				

				push(new Request(href, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
				
				while(!flag){//未完成则继续等
					Thread.sleep(10*1000);
				}
				System.out.println("------yunxing zhi ---------"+i);
			}
			System.out.println("------运行完毕！！！ ---------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
  		System.out.println(url);
		try {
			 List<Object> list = doc.sel("//div[@class='detailed01']/table//tr/td//td[@class='detailed01_03_01']/allText()");
			 
			 
			 //compName,taxNum,legalRep,idType,idNum,adress,taxType,ownSum,taxUnit
			 String result = list.get(0)+"\001"+list.get(1)+"\001"+list.get(2)+"\001-\001"+list.get(3)+"\001"+list.get(4);
			 
			 List<Object> list2 = doc.sel("//div[@class='sz_01']/table//tr/td[@align='left']/table/tbody/tr");
			 for (int i = 0; i < list2.size(); i++) {
				 Element el = (Element) list2.get(i);
		         List<Element> child_div = el.children();
		         
		         if(i ==0 || i == list2.size() - 1){
		        	 continue;
		         }
		         
		         String first = child_div.get(0).text().trim();
		         
		        
		         
		         List<Double> heji = new ArrayList<Double>();
		         
		         for(int j=0 ; j<child_div.size();j++){
		        	 if(j==0){
		        		 first = child_div.get(0).text().trim();
		        	 }else{
		        		 heji.add(Double.valueOf(changeString(child_div.get(j).text())));
		        	 }
		         }
		         
		         String last  = changeHeji(heji);
		         
		         String result_tmp  =result + "\001" + first + "\001" + ("".equals(changeString(last))?"-":changeString(last))+"\001北京市税务局\n";
		         System.out.println(result_tmp);
		         file.write(result_tmp);
		         file.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = true; 
		}
		finally{
			flag = true; 
		}
	}
	
	private String changeString(String str) {
		
		if(str !=null){
			str = str.trim().replaceAll(" ", "").replaceAll(" ","").replaceAll(",","").replaceAll("元","");
		}
		str = str.replaceAll("￥", "");
		return str;
	}
	
	private String changeHeji(List<Double> list) {
		Double tmp = 0.00d;
		for(Double d :list){
			tmp = tmp + d;
		}
		
		return tmp+"";
	}
	
}
