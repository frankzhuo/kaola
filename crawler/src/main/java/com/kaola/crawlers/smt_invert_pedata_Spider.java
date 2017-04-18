package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "smt_invert_pedata", delay = 0,useCookie = true)
public class smt_invert_pedata_Spider extends BaseSeimiCrawler {

	/**
	 * http://invest.pedata.cn/list_1_0_0_0_0.html
	 * 私募通 融资数据（投融事件）
	 */
	private BufferedWriter file = null;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://data/smt_invert_pedata.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
		 
		int pageNum = 258;
		 
		for(int p=1;p<=pageNum;p++){
			push(new Request("http://invest.pedata.cn/list_"+p+"_0_0_0_0.html", "callback").setHttpMethod(HttpMethod.POST).setSkipDuplicateFilter(true));
			Thread.sleep(20*1000);
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		System.out.println(response.getRealUrl());
//		String content = response.getContent();
		JXDocument doc = response.document();
		try {
			 List<Object> list = doc.sel("//div[@class='new_list_table']/table[@id='invest_info_table']/tbody/tr");
			 for (int i = 0; i < list.size(); i++) {
				 	Element e = (Element) list.get(i);
				 	Elements es = e.children();
				 	Elements e1 = es.get(0).getElementsByTag("a");
				 	String e1_text = "";
				 	String e1_href = "";
				 	if(e1.size() > 0){
				 		e1_text = e1.get(0).text();
				 		e1_href = e1.get(0).attr("href");
				 	}else{
				 		e1_text = es.get(0).text();
				 	}
				 	
				 	
				 	Elements e2 = es.get(1).getElementsByTag("a");
				 	String e2_text = "";
				 	String e2_href = "";
				 	if(e2.size() > 0){
				 		e2_text = e2.get(0).text();
				 		e2_href = e2.get(0).attr("href");
				 	}else{
				 		e2_text = es.get(1).text();
				 	}
				 	
				 	String e3_text = es.get(2).text();
				 	String e4_text = es.get(3).text();
				 	String e5_text = es.get(4).text();
				 	String e6_text = es.get(5).text();
				 	String e7_text = es.get(6).text();
				 	
					Elements e8 = es.get(7).getElementsByTag("a");
				 	String e8_text = "";
				 	String e8_href = "";
				 	if(e8.size() > 0){
				 		e8_text = e8.get(0).text();
				 		e8_href = e8.get(0).attr("href");
				 	}else{
				 		e8_text = es.get(7).text();
				 	}
				 	
				 	//投资机构，企业名称，所属行业，企业标签，投资轮次，投资资金，投资时间，   机构详细url，企业详情url，投资详情url
				 	String tmp = e1_text +"\001"+e2_text +"\001"+e3_text +"\001"+e4_text +"\001"+e5_text +"\001"+e6_text +"\001"+e7_text +"\001"+
				 			e1_href +"\001"+e2_href +"\001"+e8_href +"\n";
				 	System.out.println(tmp);
				 	file.write(tmp);
			}
		 
			file.flush();
		} catch (Exception e) {
			//e.printStackTrace();
			
		}
	}
	
    /**
     * UA库可以自行查找整理，这里仅为样例
     * @return
     */
    @Override
    public String getUserAgent(){
        return "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";
    }
    
}
