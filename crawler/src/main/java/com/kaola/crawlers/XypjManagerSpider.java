package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.seimi.boot.Run;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name="xypj",delay=15)
public class XypjManagerSpider extends BaseSeimiCrawler {

	private BufferedWriter  file=null;
	@Override
	public String[] startUrls() {
		try {
			file=new BufferedWriter(new FileWriter("/data/crawler//xypj.txt",false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//初始化页面
		return new String[]{"http://xypjweb.mofcom.gov.cn/pages/xypt/HypjCorpInfoLoginList.html"};
	}

	@Override
	public void start(Response response) {
		int pageNum = 1881;//信用评价记录的页数
		//ajax 提交，需要穿cookie，否则会获取不了数据
		for (int i = 0; i < pageNum; i++) {
			push(new Request("http://xypjweb.mofcom.gov.cn/pages/xypt/HypjCorpInfoLoginList_nav.pageNoLink.html?session=T&sp="+i,"callback").setCookies("td_cookie=2056532280; td_cookie=2030619736; td_cookie=2049629274; JSESSIONID=13A8B0072ABF887AEB628DA8616D4085").setSkipDuplicateFilter(true));
		}
		System.out.println("start run queue size " + pageNum);
	}
	
	 public void callback(Response response){
	        JXDocument doc = response.document();
	        try {
	        	List<Object> list = doc.sel("//div[@class='listTab']//a/@href");
	        	for(Object st:list){
	        		String request = "http://xypjweb.mofcom.gov.cn"+st;
	        		push(new Request(request, "getData").setSkipDuplicateFilter(true));
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public void getData(Response response){
	     JXDocument doc = response.document();
	     try {
	    	List<Object> headerlist = doc.sel("//article[@class='infoMain']/header[@class='title_02']/text()");
	    	String str = "-";
	    	if(headerlist.size()>0){
	    		str = (String) headerlist.get(0);
	    		str = str.replaceFirst(" ","/");
	    	}
	    	
	    	System.out.println(str);
			List<Object> listName = doc.sel("//article[@class='infoMain']//p/*/text()");
			List<Object> listValue = doc.sel("//article[@class='infoMain']//p/text()");
			
			String[] arr = new String[33];
			arr[0]=str;
			for(int i =1;i<arr.length;i++){
				arr[i]="-";
			}
			
			for (int i = 0 ; i<listName.size();i++) {
				String name = listName.get(i).toString();
				String value =(String)listValue.get(i);
			
				if(value !=null){
					value = value.replaceAll("/", "-");
					if(value.trim().length()<1){
						value = "-";
					}
				}else{
					value = "-";
				}

				if(name.startsWith("评价等级:")){
					arr[1]=value;
					continue;
				}
				if(name.startsWith("Evaluation Unit:") && i==1){
					arr[2]=value;
					continue;
				}
				if(name.startsWith("证书编号:")){
					arr[3]=value;
					continue;
				}
				if(name.startsWith("Certificate Number:")){
					arr[4]=value;
					continue;
				}
				if(name.startsWith("颁发日期:")){
					arr[5]=value;
					continue;
				}
				if(name.startsWith("Issue Date:")){
					arr[6]=value;
					continue;
				}
				if(name.startsWith("有效期至:")){
					arr[7]=value;
					continue;
				}
				if(name.startsWith("Valid Period:")){
					arr[8]=value;
					continue;
				}
				if(name.startsWith("发证单位（协会）:")){
					arr[9]=value;
					continue;
				}
				if(name.startsWith("Issue Unit:")){
					arr[10]=value;
					continue;
				}
				if(name.startsWith("评价单位（信用机构或协会）:")){
					arr[11]=value;
					continue;
				}
				if(name.startsWith("Evaluation Unit:")){
					arr[12]=value;
					continue;
				}
				if(name.startsWith("工商注册号:")){
					arr[13]=value;
					continue;
				}
				if(name.startsWith("Registration Number:")){
					arr[14]=value;
					continue;
				}
				if(name.startsWith("组织机构代码:")){
					arr[15]=value;
					continue;
				}
				if(name.startsWith("Organization Code:")){
					arr[16]=value;
					continue;
				}
				if(name.startsWith("法人代表:")){
					arr[17]=value;
					continue;
				}
				if(name.startsWith("Legal Representative:")){
					arr[18]=value;
					continue;
				}
				if(name.startsWith("注册资本:")){
					arr[19]=value;
					continue;
				}
				if(name.startsWith("Registered Capital:")){
					arr[20]=value;
					continue;
				}
				if(name.startsWith("所属行业:")){
					arr[21]=value;
					continue;
				}
				if(name.startsWith("Sector:")){
					arr[22]=value;
					continue;
				}
				if(name.startsWith("企业网址:")){
					arr[23]=value;
					continue;
				}
				if(name.startsWith("Website:")){
					arr[24]=value;
					continue;
				}
				if(name.startsWith("所属地区:")){
					arr[25]=value;
					continue;
				}
				if(name.startsWith("Region:")){
					arr[26]=value;
					continue;
				}
				if(name.startsWith("邮编:")){
					arr[27]=value;
					continue;
				}
				if(name.startsWith("Post Code:")){
					arr[28]=value;
					continue;
				}
				if(name.startsWith("经营地址:")){
					arr[29]=value;
					continue;
				}
				if(name.startsWith("Business Address:")){
					arr[30]=value;
					continue;
				}
				if(name.startsWith("主营业务:")){
					arr[31]=value;
					continue;
				}
				if(name.startsWith("Main Business:")){
					arr[32]=value;
					continue;
				}
				
			}
			String tt =ArrToStr(arr);
			if(tt.split("\001").length <33){
				System.out.println(tt.split("\001").length);
				System.out.println(tt);
			}
			
			file.write(tt);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 private String ArrToStr(String[] arr){
		 
		 String str = "";
		 for (int j = 0; j < arr.length; j++) {
			 str =str +arr[j];
				if(j == arr.length-1){
					str = str + "\n";
				}else{
					str = str + "\001";
				}
		}
		 return str;
	 }
}
