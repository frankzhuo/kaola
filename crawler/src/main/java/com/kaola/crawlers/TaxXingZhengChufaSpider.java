package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "taxxingzhengchufa", delay = 15)
public class TaxXingZhengChufaSpider extends BaseSeimiCrawler {

	/**
	 * 北京市地方税务局 征管行政处罚
	 * http://shiju.tax861.gov.cn/nsfw/sscx/sgs.asp#
	 * list
	 * detail   
	 */
	private BufferedWriter file_detail = null;
	private Queue<String>  queue = new ConcurrentLinkedQueue<String>();
	
	
	public TaxXingZhengChufaSpider() {
//		Runnable  r = new Runnable() {
//			public void run() {
//				runDetail();
//			}
//		};
//		new Thread(r).start();
//		System.out.println("other thread is running!");
	}
	
	@Override
	public String[] startUrls() {
		try {
			file_detail = new BufferedWriter(new FileWriter("d://taxchufadetail.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
		int pageNum = 283;//;
		 
		for(int p=1;p<=pageNum;p++){
			 push(new Request("http://shiju.tax861.gov.cn/nsfw/sscx/sgs_xg_jc.asp?page="+p, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
			 System.out.println("1111111111111111111111111111111111111");
			 Thread.sleep(20*1000);
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("22222222222222222222222222222222222222222");
	}
	
	private void runDetail() {
		try {
			while(true){
				String href = queue.poll();
				if(!StringUtil.isBlank(href)){
					push(new Request(href, "detail").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
				}else{
					System.out.println("=================================queue 没有==================================");
				}
				System.out.println("--------------------------------------queue sise ----------------------------"+ queue.size());
				Thread.sleep(10*1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
//		System.out.println(response.getContent());
		try {
			
			 Pattern p = Pattern.compile("<a href='(display.asp\\?jdbh=[\\d]+)' target='_top'>");
			 Matcher m =  p.matcher(response.getContent());
			 String str = "";
			 while(m.find()){
				str ="http://shiju.tax861.gov.cn/nsfw/sscx/"+ m.group(1);
				queue.add(str);
				System.out.println(str);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取详情的URL地址
	 */
	public void detail(Response response) {
		JXDocument doc = response.document();
//		System.out.println(response.getContent());
		String url = response.getRealUrl();
		try {
			 //公司名称
			//决定书编号，处罚类别，处罚事由，处罚依据，行政相对人名称，
			//统一社会信用代码，组织机构代码，工商登记码，税务登记号，居民身份证号，
			//法定代表人姓名，法人身份证号，处罚结果，处罚生效期，处罚截止期，
			//处罚机关，当前状态，地方编码，数据更新时间戳，备注
			
			 List<Object> list1 = doc.sel("//table/tbody/tr/td[@align='center']");
			 List<Object> list2 = doc.sel("//table[@bgcolor='#E4E4E4']/tbody/tr/td[@align='left']");
			 
			 String compName  = "";
			 if(list1.size() >0){
				 compName = ((Element)list1.get(0)).text().toString().trim();
			 }
			 String content = "";
			 for (int i = 0; i <list2.size() ; i++) { 
				String td = ((Element)list2.get(i)).text().toString().trim().replace("\n", "").replaceAll("　","").replaceAll(" ","");
				if(i == list2.size()-1){
					content = content + (StringUtil.isBlank(td)?"-":td) +"\n";
					break;
				}
				content = content + td +"\001";
			 }
			 
			 String tmp = compName + "\001" + content ; 
			
			 System.out.println(tmp);
			 file_detail.write(tmp);
			 file_detail.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
