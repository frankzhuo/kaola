package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Crawler(name = "sinastockpubliccompany", delay = 0,useCookie = true)
public class SinaStockPublicCompanySpider extends BaseSeimiCrawler {

	/**
	 * 新浪行情，分类，沪深A 沪深B
	 * http://vip.stock.finance.sina.com.cn/mkt/  
	 */
	private BufferedWriter file = null;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("/data/crawler/SinaStockPublicCompany/sinastockpubliccompany.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
			
			String[] nodes = new String[]{"hs_a","hs_b","cyb","zxqy"};
			
			for(int i =0 ;i<nodes.length;i++){
				push(new Request("http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeStockCount?node="+nodes[i], "callback").setSkipDuplicateFilter(true));
				Thread.sleep(1000*60*20);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		String requestUrl = response.getRealUrl();
		String nodename = requestUrl.substring(requestUrl.indexOf("=")+1);
		try {
			 String content = response.getContent();
			 
			 String reg = "\"(\\d+)\"";
			 Pattern pat = Pattern.compile(reg);
			 Matcher mat = pat.matcher(content);
			 int pageNum = 0;
				while(mat.find()){
					pageNum = Integer.parseInt(mat.group(1));
					break;
			}
			
			int size = 80;
			int pages = ((pageNum-1)/ size) +1;	
				
			for (int page = 1; page < pages; page++) {
				Map<String,String> trans = new HashMap<String,String>();
				trans.put("node",nodename);
				push(new Request("http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?page="+page+"&num=80&sort=symbol&asc=1&node="+nodename+"&symbol=&_s_r_a=page", "releaseList").setTrans(trans).setSkipDuplicateFilter(true));
			    Thread.sleep(1000*20);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public void releaseList(Response response){
		 String re = response.getContent();
		 Map trans = response.getTrans();
			
		 JSONArray sbList = JSONArray.fromObject(re);
		 try {
			for(int i = 0;i<sbList.size();i++){
				JSONObject ob = sbList.getJSONObject(i);
				String symbol = ob.getString("symbol");
				String code = ob.getString("code");
				String name = ob.getString("name");
			
				String temp = symbol + "\001" + code +"\001" + name +"\001"+trans.get("node")+"\n"; 
				System.out.println(temp);
			    file.write(temp);
			}
			file.flush();
		 } catch (IOException e) {
			e.printStackTrace();
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
