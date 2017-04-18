package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.Element;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "qianshuishanghai")
public class QianShuiShangHaiSpider extends BaseSeimiCrawler {
	/**
     * UA库可以自行查找整理，这里仅为样例
     * @return
     */
    @Override
    public String getUserAgent(){
        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
        };
        return "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";
    }
	/**
	 *  
	 */
	private BufferedWriter file = null;
    private static boolean flag= true;
	
	@Override
	public String[] startUrls() {
		try {
			file = new BufferedWriter(new FileWriter("d://qianshushanghai.txt", false));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.baidu.com" };
	}

	@Override
	public void start(Response response) {
		try {
			for(int i =1;i<247 ;i++){
				flag = false; //未完成
				String href = "http://www.tax.sh.gov.cn/tycx/TYCXqjsknsrmdCtrl-getQjsknsrmd.pfv";
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("type", "QY");
				map.put("nsrmc", "");
				map.put("swdjh", "");
				map.put("fzrxm", "");
				map.put("tsnr", "Thu Mar 31 00:00:00 GMT+08:00 2016");
				map.put("curPage", ""+i);
//                String cookies = "JSESSIONID=XvtPXsGqTjyp9ChYBzF5nWS9ldhTGFfSdgFQvTsw6kdvJVFt2t7N!-1162343885; _gscu_740578017=731562361g3nax77; _gscs_740578017=t7444082412tei013|pv:12; _gscbrs_740578017=1; _gscu_1517939987=73156236btuq2x77; _gscs_1517939987=t74440824f54cra13|pv:12; _gscbrs_1517939987=1";
				try{
					push(new Request(href, "callback").setSkipDuplicateFilter(true).setParams(map).setHttpMethod(HttpMethod.POST));
				}
				catch (Exception ex){
					System.out.println("----------exception--");
					flag=true;
				} 
				
				int count=0;
				while(!flag){//未完成则继续等
					System.out.println("====="+i+"======"+new Date());
					Thread.sleep(10*1000);
					if(count >5){
						break;
					}
					count++;
				}
				System.out.println("------yunxing zhi ---------"+i);
			}
			System.out.println("------运行完毕！！！ ---------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
    
	
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
//  		System.out.println(response.getContent());
		try {
			 List<Object> list2 = doc.sel("//table[@class='csstable']/tbody/tr");
			 
			 String result = "";
			 //compName,taxNum,legalRep,idType,idNum,adress,taxType,ownSum,taxUnit
			 for (int i = 0; i < list2.size(); i++) {
				 Element el = (Element) list2.get(i);
		         List<Element> child_div = el.children();
		         
		         if(i ==0 || i==1 || i == list2.size() - 1){
		        	 continue;
		         }
		         
		         String compName = child_div.get(0).text().trim();
		         String taxNum=  child_div.get(1).text().trim();
		         String legalRep=  child_div.get(2).text().trim();
		         String idType=  child_div.get(3).text().trim();
		         String idNum=  child_div.get(4).text().trim();
		         String adress=  "-";
		         String taxType=  child_div.get(5).text().trim();
		         String ownSum=  changeString(child_div.get(6).text().trim());
		         String taxUnit=  child_div.get(9).text().trim();
		         
		         		         
		         String result_tmp=compName + "\001" + taxNum + "\001" + legalRep+"\001"+idType+"\001"+idNum+"\001"+adress+"\001"+taxType+"\001"+ownSum+"\001"+taxUnit+"\n";
		         System.out.println(result_tmp);
		         file.write(result_tmp);
		         file.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			flag = true; 
		}
	}
	
	private String changeString(String str) {
		
		if(str !=null){
			str = str.trim().replaceAll(" ", "").replaceAll(" ","").replaceAll(",","").replaceAll("元","").replaceAll("\"","");
		}
		str = str.replaceAll("￥", "");
		return str;
	}
	
}
