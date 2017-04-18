package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Crawler(name = "jijincongye", delay = 10)
public class JiJinCongyeSpider extends BaseSeimiCrawler {

	/**
	 */
	private BufferedWriter file = null;
    private static boolean flag= true;

	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://jijincongyeorgdetail.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] {"http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		String[] orgids = new String[]{
				"01","02","03","04","05","06","07","08","09","10",
				"11","12","13","14","15","16","17","18","19","20",
				"21","22","23","24","25","26","27","99"
		};
		for (int i = 0; i < orgids.length; i++) {
			flag = false; //未完成
	    	HashMap<String,String> map=new HashMap<String,String>();
	    	map.put("filter_EQS_OTC_ID",orgids[i]);
	    	map.put("ORDERNAME","AOI#AOI_NAME");
	    	map.put("ORDER", "ASC");
	    	map.put("sqlkey", "registration");
	    	map.put("sqlval", "SELECT_LINE_PERSON");
	    	push(new Request("http://person.amac.org.cn/pages/registration/train-line-register!orderSearch.action", "callback").setSkipDuplicateFilter(true).setParams(map).setHttpMethod(HttpMethod.POST));
	    	
	    	while(!flag){//未完成则继续等
				try {
					Thread.sleep(10*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("------yunxing zhi ---------"+i);
		}
	}
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		 String content = response.getContent();
		 System.out.println(response.getUrl());
		 System.out.println(content);

		 try {
			 JSONArray rows = JSONArray.fromObject(content);
			 int num =rows.size();
			 for (int i = 0; i <num ; i++) {
				JSONObject tmp = rows.getJSONObject(i);
				String AOI_ID = tmp.getString("AOI_ID");//uuid
				 
				HashMap<String,String> map=new HashMap<String,String>();
		    	map.put("filter_EQS_PTI_ID","");
		    	map.put("filter_EQS_AOI_ID",AOI_ID);
		    	map.put("ORDERNAME","PP#PTI_ID,PP#PPP_NAME");
		    	map.put("ORDER", "ASC");
		    	map.put("sqlkey", "registration");
		    	map.put("sqlval", "SEARCH_FINISH_PUBLICITY");
				
		    	Thread.sleep(15*1000);
				push(new Request("http://person.amac.org.cn/pages/registration/train-line-register!search.action", "callbackScore").setSkipDuplicateFilter(true).setParams(map).setHttpMethod(HttpMethod.POST));
			 }
			flag =true;///
		} catch (Exception e) {
			flag =true;///
			e.printStackTrace();
		}
	}
	

	/*
	 * 获取详细页面
	 */
	public void callbackScore(Response response) {
		try {
			String content = response.getContent();
			System.out.println(content);

			 JSONArray rows = JSONArray.fromObject(content);
			 for (int i = 0; i < rows.size(); i++) {
				 JSONObject tmp = rows.getJSONObject(i);
				 
				 String RPI_ID = tmp.getString("RPI_ID");//uuid
				 String RPI_NAME = tmp.getString("RPI_NAME");//姓名
				 String SCO_NAME = tmp.getString("SCO_NAME");//性别 
				 String ECO_NAME = tmp.getString("ECO_NAME");//学历
				 String AOI_NAME = tmp.getString("AOI_NAME");//从业机构
				 String PTI_NAME = tmp.getString("PTI_NAME");//从业岗位
				 String CER_NUM = tmp.getString("CER_NUM");//证书编号
				 String PPP_GET_DATE = tmp.getString("PPP_GET_DATE");//证书取得日期
				 String PPP_END_DATE = tmp.getString("PPP_END_DATE");//证书有效截止日期
				 String COUNTCER = tmp.getString("COUNTCER");//注册变更记录
				 String COUNTCX = tmp.getString("COUNTCX")==null || "null".equals(tmp.getString("COUNTCX"))?"-":tmp.getString("COUNTCX");//诚信记录
				 
			
				 StringBuffer strb= new StringBuffer();
				 strb.append(replaceString(RPI_ID)+"\001");
				 strb.append(replaceString(RPI_NAME)+"\001");
				 strb.append(replaceString(SCO_NAME)+"\001");
				 strb.append(replaceString(ECO_NAME)+"\001");
				 strb.append(replaceString(AOI_NAME)+"\001");
				 strb.append(replaceString(PTI_NAME)+"\001");
				 strb.append(replaceString(CER_NUM)+"\001");
				 strb.append(replaceString(PPP_GET_DATE)+"\001");
				 strb.append(replaceString(PPP_END_DATE)+"\001");
				 strb.append(replaceString(COUNTCER)+"\001");
				 strb.append(replaceString(COUNTCX)+"\n");
				 
				 try {
					 file.write(strb.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
			 file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   public String replaceString(String str){
	   if(str != null){
		   str = str.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "").replaceAll(" ", "");
	   }
	   return str;
   }
}
