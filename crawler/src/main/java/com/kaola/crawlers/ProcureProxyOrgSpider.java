package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//@Crawler(name = "pporg", delay = 10)
public class ProcureProxyOrgSpider extends BaseSeimiCrawler {
	/**
	 * 采购代理机构（中国政府采购网）
	 * 采购代理机构业绩
	 */
	private BufferedWriter file = null;
	private BufferedWriter fileScore = null;

	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://procureproxyorg.txt", false));
			fileScore = new BufferedWriter(new FileWriter("d://procureproxyorg_score.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] {"http://jczy.ccgp.gov.cn/gs1/gs1agentreg/pubListIndex.regx" };
	}

	@Override
	public void start(Response response) {
		String content = response.getContent();
		System.out.println(content);
		
		int pageNum = 221;
		for (int i = 1; i <= pageNum; i++) {
	    	HashMap<String,String> map=new HashMap<String,String>();
	    	map.put("page",i+"");
	    	map.put("rows","30");
	    	map.put("sort", "regValidDate");
	    	map.put("order", "desc");
	    	try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			push(new Request("http://jczy.ccgp.gov.cn/gs1/gs1agentreg/getPubList.regx?provinceCode=", "callback").setSkipDuplicateFilter(true).setParams(map).setHttpMethod(HttpMethod.POST));
		}
	}
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		String content = response.getContent();
		System.out.println(content);

		 JSONObject jsStr = JSONObject.fromObject(content);
		 JSONArray rows = jsStr.getJSONArray("rows");
		 int total = Integer.parseInt(jsStr.getString("total"));
		 System.out.println("callback total:"+total);
		 for (int i = 0; i < rows.size(); i++) {
			 JSONObject tmp = rows.getJSONObject(i);
			 
			 String uuid = tmp.getString("uuid");//uuid
			 String orgId = tmp.getString("orgId");//机构id
			 String agentNm = tmp.getString("agentNm");//机构名称
			 String corpTel = tmp.getString("corpTel");//联系电话
			 String contactNm = tmp.getString("contactNm");//联系人
			 String legalNm = tmp.getString("legalNm");//法人姓名
			 String regAddr = tmp.getString("regAddr");//注册地址
			 String auditPlaceAddr = tmp.getString("auditPlaceAddr");//评审场所地址
			 String auditPlaceArea = tmp.getString("auditPlaceArea");//评审场所面积
			 String regValidDateStr = tmp.getString("regValidDateStr");//登记日期
			 String auditPlace = tmp.getString("auditPlace");//登记地点
			 String year3Outlaw = tmp.getString("year3Outlaw");//近三年有无重大违法记录
			 String publicRemark = tmp.getString("publicRemark");//备注
			 String corpDesc = tmp.getString("corpDesc");//机构简介
			 
			 StringBuffer strb= new StringBuffer();
			 strb.append(replaceString(uuid)+"\001");
			 strb.append(replaceString(orgId)+"\001");
			 strb.append(replaceString(agentNm)+"\001");
			 strb.append(replaceString(corpTel)+"\001");
			 strb.append(replaceString(contactNm)+"\001");
			 strb.append(replaceString(legalNm)+"\001");
			 strb.append(replaceString(regAddr)+"\001");
			 strb.append(replaceString(auditPlaceAddr)+"\001");
			 strb.append(replaceString(auditPlaceArea)+"\001");
			 strb.append(replaceString(regValidDateStr)+"\001");
			 strb.append(replaceString(auditPlace)+"\001");
			 strb.append(replaceString(year3Outlaw)+"\001");
			 strb.append(replaceString(publicRemark)+"\001");
			 strb.append(replaceString(corpDesc)+"\n");
			 
			 try {
				file.write(strb.toString());
				file.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			HashMap<String,String> map=new HashMap<String,String>();
	    	map.put("page","1");
	    	map.put("rows",""+Integer.MAX_VALUE);
			push(new Request("http://jczy.ccgp.gov.cn/gs1/gs1agentinfo/agentper/listAgentPerfQuery.regx?orgid="+orgId, "callbackScore").setSkipDuplicateFilter(false).setParams(map).setHttpMethod(HttpMethod.POST));
		}
	}

	/*
	 * 获取详细页面
	 */
	public void callbackScore(Response response) {
		try {
			String content = response.getContent();
			System.out.println(content);

			 JSONObject jsStr = JSONObject.fromObject(content);
			 JSONArray rows = jsStr.getJSONArray("rows");
			 int total = Integer.parseInt(jsStr.getString("total"));
			 System.out.println("callbackScore total:"+total);
			 for (int i = 0; i < rows.size(); i++) {
				 JSONObject tmp = rows.getJSONObject(i);
				 
				 String uuid = tmp.getString("uuid");//uuid
				 String orgId = tmp.getString("orgId");//机构id
				 String projNm = tmp.getString("projNm");//项目名称 
				 String entrustUnitNm = tmp.getString("entrustUnitNm");//委托单位
				 String bidcallNo = tmp.getString("bidcallNo");//采购文件编号
				 String bidconfirmUnitNm = tmp.getString("bidconfirmUnitNm");//中标/成交单位
				 String noticedatestr = tmp.getString("noticedatestr");//中标通知发出时间
				 String bidconfirmAmount = tmp.getString("bidconfirmAmount");//中标/成交金额（万元）
				 
			
				 StringBuffer strb= new StringBuffer();
				 strb.append(replaceString(uuid)+"\001");
				 strb.append(replaceString(orgId)+"\001");
				 strb.append(replaceString(projNm)+"\001");
				 strb.append(replaceString(entrustUnitNm)+"\001");
				 strb.append(replaceString(bidcallNo)+"\001");
				 strb.append(replaceString(bidconfirmUnitNm)+"\001");
				 strb.append(replaceString(noticedatestr)+"\001");
				 strb.append(replaceString(bidconfirmAmount)+"\n");
				 
				 try {
					fileScore.write(strb.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			  }
			 fileScore.flush();
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
