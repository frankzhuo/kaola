package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "sinastockpubliccompanygaoguan", delay = 0,useCookie = true)
public class SinaStockPublicCompanyGaoGuanSpider extends BaseSeimiCrawler {

	/**
	 * 新浪行情，公司高管
	 * http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CorpManager/stockid/000069.phtml 
	 */
	private BufferedReader fileReader = null;
	private BufferedWriter file = null;
	
	private static Set<String> codeSet = new HashSet<String>();
	
	@Override
	public String[] startUrls() {
		try {
			fileReader = new BufferedReader(new FileReader("/data/crawler/SinaStockPublicCompany/sinastockpubliccompany.txt"));
			file = new BufferedWriter(new FileWriter("/data/crawler/SinaStockPublicCompany/sinastockpubliccompanygaoguan.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		String line;
		try {
			while((line = fileReader.readLine()) !=null && line.trim().length()>0){
				String code = line.split("\001")[1];
				if(codeSet.contains(code)){
					continue;
				}
				codeSet.add(code);
				System.out.println("codeSet size is "+codeSet.size());
				
				Map<String,String> trans = new HashMap<String,String>();
				trans.put("line", line.trim());
				push(new Request("http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CorpManager/stockid/"+code+".phtml", "callback").setTrans(trans).setSkipDuplicateFilter(true));
				
				Thread.sleep(1000*11);
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
			 String content = response.getContent();
			// System.out.println(content);
			 Map trans = response.getTrans();
			 String line  = (String) trans.get("line");
			 List<Object> list1 = doc.sel("//div[@class='tagmain']/table[@id='Table1']/tbody/tr");
			 List<Object> list2 = doc.sel("//div[@class='tagmain']/table[@id='Table2']/tbody/tr");
			 List<Object> list3 = doc.sel("//div[@class='tagmain']/table[@id='Table3']/tbody/tr");
			 
 			 if(list1.size() > 0){
 				String title = "历届高管成员";
 				for (int i = 0; i < list1.size(); i++) {
 					if(i == 0 ){
 						continue;
 					}
 					Elements tds = ((Element)list1.get(i)).children() ;
 					if(tds.size() > 1){
 						String name = cleanStr(tds.get(0).text());
 						String duty = cleanStr(tds.get(1).text());
 						String startDate = cleanStr(tds.get(2).text());
 						String endDate = cleanStr(tds.get(3).text());
 						String tmp = cleanStr(line) + "\001" + name +"\001"+duty+"\001"+startDate+"\001"+endDate+"\001"+title+"\n";
 						System.out.println(tmp);
 						file.write(tmp);
 						file.flush();
 					}else if(tds.size() == 1){
 						title =  cleanStr(tds.get(0).text());
 					}
				}
			 }//end 
 			 
 			 
 			 if(list2.size() > 0){
 	     		String title = "历届董事会成员";
 				for (int i = 0; i < list2.size(); i++) {
 					if(i == 0 ){
 						continue;
 					}
 					Elements tds = ((Element)list2.get(i)).children() ;
 					if(tds.size() > 1){
 						String name = cleanStr(tds.get(0).text());
 						String duty = cleanStr(tds.get(1).text());
 						String startDate = cleanStr(tds.get(2).text());
 						String endDate = cleanStr(tds.get(3).text());
 						String tmp = cleanStr(line) + "\001" + name +"\001"+duty+"\001"+startDate+"\001"+endDate+"\001"+title+"\n";
 						System.out.println(tmp);
 						file.write(tmp);
 						file.flush();
 					}else if(tds.size() == 1){
 						title =  cleanStr(tds.get(0).text());
 					}
				}
			 }//end 
 			 
 			 
 			 if(list3.size() > 0){
 				String title = "历届监事会成员";
 				for (int i = 0; i < list3.size(); i++) {
 					if(i == 0 ){
 						continue;
 					}
 					Elements tds = ((Element)list3.get(i)).children() ;
 					if(tds.size() > 1){
 						String name = cleanStr(tds.get(0).text());
 						String duty = cleanStr(tds.get(1).text());
 						String startDate = cleanStr(tds.get(2).text());
 						String endDate = cleanStr(tds.get(3).text());
 						String tmp = cleanStr(line) + "\001" + name +"\001"+duty+"\001"+startDate+"\001"+endDate+"\001"+title+"\n";
 						System.out.println(tmp);
 						file.write(tmp);
 						file.flush();
 					}else if(tds.size() == 1){
 						title =  cleanStr(tds.get(0).text());
 					}
				}
			 }//end 
 			 
 			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//清楚格式
	public static String cleanStr(String str){
		if(str != null){
			str  = str.trim().replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
		}
		return str;		
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
