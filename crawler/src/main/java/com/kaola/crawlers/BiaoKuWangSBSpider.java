package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Crawler(name = "biaokuwangsb", delay = 15)
public class BiaoKuWangSBSpider extends BaseSeimiCrawler {

	/**
	 * 标库网抓取商标，根据提供的企业名称，
	 * http://api.tmkoo.com/sqr-tm-list.php?apiKey=TEST_API_06165&apiPassword=3A86C93C0ANC&applicantCn=拉卡拉支付有限公司
	 * http://api.tmkoo.com/info.php?apiKey=TEST_API_06165&apiPassword=3A86C93C0ANC&regNo=6353781&intCls=9
	 */
	private BufferedWriter file = null;
	private BufferedReader fileReader = null;
    private static boolean flag= true;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("/data/crawler/shangbiao/shangbiao.txt", true));
			fileReader = new BufferedReader(new FileReader("/data/crawler/shangbiao/companyofshangbiao1.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.dianping.com/" };
	}

	@Override
	public void start(Response response) {
		String line;
		try {
			while((line = fileReader.readLine()) !=null && line.trim().length()>0){
				flag = false; //未完成
			    push(new Request("http://api.tmkoo.com/sqr-tm-list.php?apiKey=TEST_API_06165&apiPassword=3A86C93C0ANC&applicantCn="+line, "queryListByAppCN").setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.GET));
				
			    int count=0;
				while(!flag){//未完成则继续等
					Thread.sleep(10*1000);
					if(count >120){ //20分钟
						break;
					}
					count++;
				}
				System.out.println("flag======"+flag+"------a line is over,applicationName is ---------"+line);
			}
			
			System.out.println("------运行完毕！！！ ---------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void queryListByAppCN(Response response) {
		try {
			String re = response.getContent();
			JSONObject json = JSONObject.fromObject(re);
			
			JSONArray sbList = json.getJSONArray("results");
			
			for(int i = 0;i<sbList.size();i++){
				JSONObject ob = sbList.getJSONObject(i);
				String regNo = ob.getString("regNo");
				String intCls = ob.getString("intCls");
				//String currentStatus = ob.getString("currentStatus");
				String applicantCn = ob.getString("applicantCn");
			
				System.out.println("flag======" + flag+" applicationName is "+applicantCn);
				if(regNo.length()>0 && intCls.length()>0){
					push(new Request("http://api.tmkoo.com/info.php?apiKey=TEST_API_06165&apiPassword=3A86C93C0ANC&regNo="+regNo+"&intCls="+intCls, "callback").setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.GET));
					Thread.sleep(5*1000);
				}
			}
			flag =true;
		} catch (Exception e) {
			flag =true;
			e.printStackTrace();
		}finally{
			flag = true;
		}
		
	}
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		try {
			String re = response.getContent();
			JSONObject json = JSONObject.fromObject(re);
			
			String regNo = json.getString("regNo");
			String intCls = json.getString("intCls");  //第几类 
			
			String appDate = json.getString("appDate");
			String applicantCn = json.getString("applicantCn");
			String applicantEn = json.getString("applicantEn");
			String addressCn = json.getString("addressCn");
			String addressEn = json.getString("addressEn");
			
			String announcementIssue = json.getString("announcementIssue");//初审公告期号
			String announcementDate = json.getString("announcementDate");  //初审公告日期
			String regIssue = json.getString("regIssue");  //注册公告期号
			String regDate = json.getString("regDate");  //注册公告日期
			
			String privateDate = json.getString("privateDate");  //专用权期限
			String agent = json.getString("agent");  //代理人名称
			String category = json.getString("category");  //商标类型
			
			String tmName = json.getString("tmName");  //商标文字
			String tmImg = "http://pic.tmkoo.com/pic.php?zch="+json.getString("tmImg");  //商标图片
			
			JSONArray goods = json.getJSONArray("goods");  //商品/服务列表
			String goods_str = "";
			for(int g= 0 ;g<goods.size();g++){
				String goodsCode = goods.getJSONObject(g).getString("goodsCode");
				String goodsName = goods.getJSONObject(g).getString("goodsName");
				if(g==goods.size() - 1){
					goods_str += goodsCode+"-"+goodsName ;
					break;
				}
				goods_str += goodsCode+"-"+goodsName +"\002";
			}
			
			JSONArray flow = json.getJSONArray("flow");  //商标状态
			String flow_str = "";
			for(int f= 0 ;f<flow.size();f++){
				String flowDate = flow.getJSONObject(f).getString("flowDate");
				String flowName = flow.getJSONObject(f).getString("flowName");
				if(f==flow.size() - 1){
					flow_str += flowDate+"-"+flowName ;
					break;
				}
				flow_str += flowDate+"-"+flowName +"\002";
			}
			if(flow_str.length()==0){ //末尾补-
				flow_str = "-" ;
			}
			
			String str = regNo+"\001"+intCls+"\001"+appDate+"\001"+applicantCn+"\001"+applicantEn+"\001"+addressCn+"\001"+addressEn+"\001"
					+announcementIssue+"\001"+announcementDate+"\001"+regIssue+"\001"+regDate+"\001"+privateDate+"\001"+agent+"\001"+category+"\001"+tmName
					+"\001"+tmImg+"\001"+goods_str+"\001"+flow_str+"\n";
			
			file.write(str);
			file.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
