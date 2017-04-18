package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;
import sun.security.util.Length;

@Crawler(name = "zhengjianhui", delay = 15)
public class ZhengJianHuiSpider extends BaseSeimiCrawler {

	/**
	 * 证监会 行政处罚决定
	 *  
	 */
	private BufferedWriter file = null;

	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://zhengjianhui.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.csrc.gov.cn/pub/zjhpublic/3300/3313/index_7401.htm" };
	}

	@Override
	public void start(Response response) {
		String content = response.getContent();
	
		int pageNum = 44; // 测试页数写成固定页数

		callback(response);// 第一页

		for (int i = 1; i < pageNum; i++) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			push(new Request("http://www.csrc.gov.cn/pub/zjhpublic/3300/3313/index_7401_" + i + ".htm", "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
		}
		System.out.println("start run queue size " + pageNum);
	}
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		try {
			List<Object> list = doc.sel("//div[@id='documentContainer']/div[@class='row']//div//a/@href");

			for (Object tmp : list) {
			 System.out.println(tmp);
			 String hrefDetail = "http://www.csrc.gov.cn/pub/zjhpublic" + tmp.toString().replace("../..",""); 
			 System.out.println(hrefDetail);
				push(new Request(hrefDetail, "getData").setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.GET));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 获取详细页面
	 */
	public void getData(Response response) {
		JXDocument doc = response.document();
//		System.out.println(response.getContent());
		
		try {
			String[] strs = new String[] {"", "", "", "", "", "", "", ""};

			List<Object> headInfo = doc.sel("//div[@class='headInfo']/table//tr/td[@colspan='2']/table//td/allText()");
			List<Object> lTitle = doc.sel("//div[@class='headInfo']/table//tr/td[@colspan='2']/span/text()");
			List<Object> content = doc.sel("//div[@class='mainContainer']/div[@id='ContentRegion']//p/allText()");

			for (int i = 0; i < headInfo.size(); i++) {
				strs[i]= replaceStr(headInfo.get(i).toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ","").replaceAll(" ",""));
			}
			strs[6] = replaceStr(lTitle.toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ",""));
			
			String cont = "";
			for (int i = 0; i < content.size(); i++) {
				String temp = content.get(i).toString().trim().replaceAll("\t","").replaceAll("\n","").replaceAll("\r","").replaceAll(" ","");
				if(temp.length()>0){
					if(cont.length()>0){
						cont += "\002" + temp;
					}else{
						cont += temp;
					}
				}
			}
			
			if(cont.length()==0){
				cont = "-";
			}
			strs[7] = cont;
			
			String tt = ArrToStr(strs);
			System.out.println(tt);
			if (tt.split("\001").length == 8) {
				file.write(tt);
			}
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 /**
	  * 把字符数组转化成字符串，按“\001”分隔符分组
	  * @param arr
	  * @return
	  */
	private String ArrToStr(String[] arr) {
		String str = "";
		for (int j = 0; j < arr.length; j++) {
			str = str + arr[j];
			if (j == arr.length - 1) {
				str = str + "\n";
			} else {
				str = str + "\001";
			}
		}
		return str;
	}
	private String replaceStr(String str){
		
		str = str.replaceAll("索引号:","");
		str = str.replaceAll("分类:","");
		str = str.replaceAll("发布机构:","");
		str = str.replaceAll("发文日期:","");
		str = str.replaceAll("文　　号:","");
		str = str.replaceAll("主题词:","");
		
		return str;
		
	}
}
