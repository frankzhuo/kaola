package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "dffblbgg", delay = 15)
public class dffblbggSpider extends BaseSeimiCrawler {

	/**
	 * 采购流标废标公告
	 */
	private BufferedWriter file = null;

	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://dffblfgg.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.ccgp.gov.cn/cggg/dfgg/fblbgg/index.htm" };
	}

	@Override
	public void start(Response response) {
		//String content = response.getContent();
		//System.out.println(content);

		callback(response);// 第一页

		for (int i = 1; i < 25; i++) {
			try {
				Thread.sleep(10*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			push(new Request("http://www.ccgp.gov.cn/cggg/dfgg/fblbgg/index_" + i + ".htm", "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
		}
	}
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		try {
			List<Object> list = doc.sel("//div[@class='main_list']/ul[@class='ulst']/li/a/@href");
			for (Object tmp : list) {
				String request = "http://www.ccgp.gov.cn/cggg/dfgg/fblbgg" + tmp.toString().substring(1);
				try {
					Thread.sleep(1*1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				push(new Request(request, "getData").setSkipDuplicateFilter(true));
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
		String url = response.getRealUrl();
		try {
			List<Object> list= doc.sel("//div[@class^='vT_detail_content']/p/allText()");

			boolean flat = false;
			String season = "";//废标、流标的原因
			String fblbDate ="";//废标/流标日期：
			
			String projectNum="";//项目编号：
			String projectName="";//项目名称：
			String projectContact="";//项目联系人：
			String contact="";//联系方式：
					
			String purchaseName="";//采购人名称：
			String purchaseAddress="";//采购人地址：
			String purchaseContact="";//采购人联系方式：
			
			String purchaseAgentName="";//采购代理机构全称：
			String purchaseAgentAddress="";//采购代理机构地址：
			String purchaseAgentContact="";//采购代理机构联系方式：
			
			
			for (int i = 0; i < list.size(); i++) {
				String tmp = (String)list.get(i);
				tmp=tmp.trim().replaceAll(" ","").replaceAll("\t", "");
				if("".equals(tmp)){
					continue;
				}
				
				if(tmp.contains("项目编号：") || tmp.contains("项目编码：") || tmp.contains("编号：")){
					projectNum = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("项目名称：")){
					projectName =tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("项目联系人：")){
					projectContact =tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("联系方式：")){
					contact = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("采购人名称：") || tmp.contains("采购单位名称：")){
					purchaseName = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("采购人地址：") || tmp.contains("采购单位地址：")){
					purchaseAddress =tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("采购人联系方式：") || tmp.contains("采购单位联系方式：")){
					purchaseContact = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("代理机构全称：") || tmp.contains("代理机构：") ){
					purchaseAgentName = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("代理机构地址：")){
					purchaseAgentAddress = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("代理机构联系方式：")){
					purchaseAgentContact = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				
				if(tmp.contains("废标原因：")||tmp.contains("废标理由：")){
					season = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				if(tmp.contains("废标日期：")){
					fblbDate = tmp.substring(tmp.indexOf("：")+1);
					continue;
				}
				
				
				if(tmp.matches(".*废标、流标的原因.*")){
					flat =true;
					season = "";
					continue;
				}
				if(flat){
					if(!tmp.matches(".*、废标/流标日期.*")){
						season = season + tmp;
						continue;
					}
					if(tmp.matches(".*、废标/流标日期.*")){
						flat =false;
						fblbDate = tmp.substring(tmp.lastIndexOf("：")+1);
						break;
					}
				}
			}
			
			String str =projectNum+"\001"+projectName+"\001"+projectContact+"\001"+contact+"\001"
					+purchaseName+"\001"+purchaseAddress+"\001"+purchaseContact+"\001"
					+purchaseAgentName+"\001"+purchaseAgentAddress+"\001"+purchaseAgentContact+"\001"
					+fblbDate+"\001"+season+"\001df\001"+url+"\n";
			System.out.println(str);
			file.write(str);
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
}
