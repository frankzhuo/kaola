package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "izhikureport", delay = 15)
public class IZhiKuReportSpider extends BaseSeimiCrawler {

	/**
	 * i智库：报告 抓取
	 * http://www.ipzhiku.com/search/goodsSearch   
	 */
	private BufferedWriter file = null;
	Map<String,Object> map_goodsid = new HashMap<String,Object>();
	
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://izhikureport.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
		int pageNum = 18;
		 
		for(int p=1;p<=pageNum;p++){
			 push(new Request("http://www.ipzhiku.com/search/goodsSearch?keyword=&searchType=allSearch&pageNo="+p, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
			 Thread.sleep(600*1000);
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
		try {
			 List<Object> list1 = doc.sel("//div[@id='view-list']/ul[@class='list clf']/li/div[@class='box']/a/@href");
			 
			 for (int i = 0; i <list1.size() ; i++) { //
				 String href ="http://www.ipzhiku.com"+list1.get(i);
				 push(new Request(href, "detail").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
				 Thread.sleep(300*1000);
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void detail(Response response) {
		String url=response.getRealUrl();
		JXDocument doc = response.document();
		try {
			 List<Object> list1 = doc.sel("//div[@class='product-intro']/div[@class='product-tit']");
             String goodsid = url.split("=")[1];
             map_goodsid.put(goodsid, list1);
			 
			 Map<String,String> map = new HashMap<String,String>();
			 
			 map.put("goodsId", url.split("=")[1]);
			 push(new Request("http://www.ipzhiku.com/product/goodsBody", "ajax").setParams(map).setTrans(map).setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.POST));
			 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void ajax(Response response) {
		String url=response.getRealUrl();
		try {
			 String content = response.getContent();

			 Map m = response.getTrans();
			 String head = "<html><head>22</head><body>";
			 String head2 = "</body></html>";
			 Element e =Jsoup.parse(head+content+head2);
			 
			 String goodsid = (String) m.get("goodsId");
             List list =  (List) map_goodsid.get(goodsid);
             
			 Element div_e = (Element) list.get(0);
			 String title = div_e.getElementsByTag("h2").get(0).text();
			 String desc = div_e.getElementsByTag("p").get(0).text();
			 
			 //regex | 是特殊字符 
			 String[] descs=desc.trim().replaceAll(" ", "").split("[|]");
			 
			 String addr= descs[0].replaceAll("出品", "").trim();
			 String date = descs[1].trim();
             
			 String contentAbstract = e.getElementById("contentAbstract").attr("value").trim().replace("\n", "");
			 System.out.println(contentAbstract.trim().replace("\n", ""));
			 String catalog = e.getElementById("catalog").attr("value").trim().replace("\n", "");
			 System.out.println(catalog.trim().trim().replace("\n", ""));
			 
			 file.write(title+"\001"+addr+"\001"+date+"\001"+contentAbstract+"\001"+catalog+"\001"+goodsid+"\n");
			 file.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
