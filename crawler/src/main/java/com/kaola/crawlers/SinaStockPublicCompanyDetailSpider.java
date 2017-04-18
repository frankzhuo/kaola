package com.kaola.crawlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "sinastockpubliccompanydetail", delay = 0,useCookie = true)
public class SinaStockPublicCompanyDetailSpider extends BaseSeimiCrawler {

	/**
	 * 新浪行情，公司的基本信息
	 *http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CorpInfo/stockid/000069.phtml
	 */
	private BufferedReader fileReader = null;
	private BufferedWriter file = null;
	private static Set<String> codeSet = new HashSet<String>();
	
	@Override
	public String[] startUrls() {
		try {
			fileReader = new BufferedReader(new FileReader("/data/crawler/SinaStockPublicCompany/sinastockpubliccompany.txt"));
			file = new BufferedWriter(new FileWriter("/data/crawler/SinaStockPublicCompany/sinastockpubliccompanydetail.txt", false));
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
				Map<String,String> trans = new HashMap<String,String>();
				trans.put("line", line.trim());
				push(new Request("http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CorpInfo/stockid/"+code+".phtml", "callback").setTrans(trans).setSkipDuplicateFilter(true));
				
				Thread.sleep(1000*10);
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
			 List<Object> list = doc.sel("//div[@id='con02-0']/table[@id='comInfo1']/tbody/tr");
			 List<Object> list2 = new ArrayList<>();
					 
			 for (int i = 0; i < list.size(); i++) {
				if(((Element)list.get(i)).children().size()>0){
					list2.add((Element)list.get(i));
				}
			 }
			 
			 if(list2.size() > 0){
			 	 String zhname = cleanStr(((Element)list2.get(0)).child(1).text());//公司名称
			 	 String enname = cleanStr(((Element)list2.get(1)).child(1).text());//公司英文名称
			 	 
			 	 String sssc = cleanStr(((Element)list2.get(2)).child(1).text());//上市市场
			 	 String ssrq = cleanStr(((Element)list2.get(2)).child(3).text());//上市日期
			 	 
			 	 String fxjg = cleanStr(((Element)list2.get(3)).child(1).text());//发行价格
			 	 String zcxs = cleanStr(((Element)list2.get(3)).child(3).text());//主承销商
			 	
			 	 String clrq = cleanStr(((Element)list2.get(4)).child(1).text());//成立日期
			 	 String zczb = cleanStr(((Element)list2.get(4)).child(3).text());//注册资本
			 	 
			 	 String jglx = cleanStr(((Element)list2.get(5)).child(1).text());//机构类型
			 	 String zzxs = cleanStr(((Element)list2.get(5)).child(3).text());//组织形式
			 	 
			 	 String zshms = cleanStr(((Element)list2.get(6)).child(1).text());//董事会秘书
			 	 String gsdh = cleanStr(((Element)list2.get(6)).child(3).text());//公司电话
			 	 
				 String dmdh = cleanStr(((Element)list2.get(7)).child(1).text());//董秘电话
			 	 String gscz = cleanStr(((Element)list2.get(7)).child(3).text());//公司传真
				 
			 	 String dmcz = cleanStr(((Element)list2.get(8)).child(1).text());//董秘传真
			 	 String gsyx = cleanStr(((Element)list2.get(8)).child(3).text());//公司电子邮箱
			 	 
			 	 String dmyx = cleanStr(((Element)list2.get(9)).child(1).text());//董秘电子邮箱
			 	 String gsdz = cleanStr(((Element)list2.get(9)).child(3).text());//公司网址
			 	 
			 	 String yzbm = cleanStr(((Element)list2.get(10)).child(1).text());//邮政编码
			 	 String xxplwz = cleanStr(((Element)list2.get(10)).child(3).text());//信息披露网址
			 	 
			 	 String gmls =cleanStr( ((Element)list2.get(11)).child(1).text());//证券简称更名历史
			 	 
			 	 String zcdi = cleanStr(((Element)list2.get(12)).child(1).text());//注册地址
			 	 
			 	 String bgdz = cleanStr(((Element)list2.get(13)).child(1).text());//办公地址
			 	 
			 	 String gsjj =cleanStr( ((Element)list2.get(14)).child(1).text());//公司简介
			 	 
			 	 String jyfw = cleanStr(((Element)list2.get(15)).child(1).text());//经营范围
			 	
			 	 String tmp = cleanStr(line) +"\001"+zhname+"\001"+enname+"\001"+sssc+"\001"+ssrq+"\001"+fxjg+"\001"+zcxs+"\001"+clrq+"\001"+zczb+"\001"+jglx+"\001"+zzxs+"\001"+
			 			 	zshms+"\001"+gsdh+"\001"+dmdh+"\001"+gscz+"\001"+dmcz+"\001"+gsyx+"\001"+dmyx+"\001"+gsdz+"\001"+yzbm+"\001"+xxplwz+"\001"+gmls+"\001"+zcdi+"\001"+bgdz+"\001"+gsjj+"\001"+jyfw+"\n";
			 	 file.write(tmp);
			 	 System.out.println(tmp);
			 	 file.flush();
			 }
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
