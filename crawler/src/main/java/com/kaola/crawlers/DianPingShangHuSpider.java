package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

@Crawler(name = "dianpingshanghu3",delay = 15)
public class DianPingShangHuSpider extends BaseSeimiCrawler {

	/**
	 * 证监会 行政处罚决定
	 *  
	 */
	private BufferedWriter file = null;
	private BufferedWriter file_no = null;

    @Override
    public String proxy() {
        String[] proxies = new String[]{
        		"http://119.29.54.15:80",
        		"http://119.29.191.76:80",
        		"http://101.201.143.54:80"};
        return proxies[RandomUtils.nextInt()%proxies.length];
    }
	
	@Override
	public String[] startUrls() {
		try {
			//file = new BufferedWriter(new FileWriter("/data/crawler/dianping/shanghu10w_1000w.txt", true));
			//file_no = new BufferedWriter(new FileWriter("/data/crawler/dianping/shanghu10w_1000w_no.txt", true));
			
			file = new BufferedWriter(new FileWriter("/data/crawler/dianping/shanghu200w.txt", true));
			file_no = new BufferedWriter(new FileWriter("/data/crawler/dianping/shanghu200w_no.txt", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://www.dianping.com/" };
	}
	


	@Override
	public void start(Response response) {
//		int start   = 30000000;
//		int pageNum = 40000000; // 测试页数写成固定页数

//		int start   = 511945;
//		int pageNum = 1000000; // 测试页数写成固定页数
		
		int start   = 2373590;
		int pageNum = 3000000; // 测试页数写成固定页数
		
		//push(new Request("http://www.dianping.com/shop/2611654", "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
		
		for (int i = start; i <= pageNum; i++) {
			try {
//				if(i%20==0){
					Thread.sleep(3*1000);
//				}
				push(new Request("http://www.dianping.com/shop/"+i, "callback").setSkipDuplicateFilter(false).setHttpMethod(HttpMethod.GET));
			} catch (Exception e) {
				try {
					file_no.write(i+"");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
		}

		System.out.println("start run queue size " + pageNum);
	}
    
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
        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
    }
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		String url = response.getUrl();
		//System.out.println(response.getContent());
		try {
			
			//商户id
			String shanghuiID= url.substring(url.lastIndexOf("/")+1);
//			System.out.println("url:"+url+"------>"+shanghuiID);
			
			//城市
			List<Object> city = doc.sel("//a[@class='city J-city']/text()");
			String cityString="";
			if(city.size()>0){
				cityString = (String)city.get(0);
			}
//			System.out.println("city:"+cityString);
			
			//导航地址
			List<Object> daohang = doc.sel("//div[@class='breadcrumb']/allText()");
			String daohangString="";
			if(daohang.size()>0){
				daohangString = (String)daohang.get(0);
			}
//			System.out.println("daohang:"+daohangString);
			
			//商户名称
			List<Object> shopname = doc.sel("//div[@id='basic-info']/h1[@class='shop-name']/text()");
			String shopnameString="";
			if(shopname.size()>0){
				shopnameString = (String)shopname.get(0);
			}
//			System.out.println("shopname:"+shopnameString);

			//商户评价
			List<Object> pingjia = doc.sel("//div[@id='basic-info']/div[@class='brief-info']/span[first()]/@title");
			List<Object> briefinfo = doc.sel("//div[@id='basic-info']/div[@class='brief-info']/span[@class='item']/text()");
			String pingjiaString = (String) (pingjia.size()>0?pingjia.get(0):"");
			for (int i = 0; i < briefinfo.size(); i++) {
				pingjiaString = pingjiaString +"\002"+briefinfo.get(i);
			}
//			System.out.println("briefinfo:"+pingjiaString);
			
			//地址信息			
			List<Object> address = doc.sel("//div[@id='basic-info']/div[@class*='address']/span[@class='item']/text()");
			String addressString =(String) (address.size()>0?address.get(0):"");
//			System.out.println("address:"+addressString);
			
			
			//电话，联系方式
			List<Object> telphone = doc.sel("//div[@id='basic-info']/p[@class*='tel']/span[last()]/text()");
			String telphoneString =(String) (telphone.size()>0?telphone.get(0):"");
//			System.out.println("telphone:"+telphoneString);
 
			//其它补充说明
			List<Object> other = doc.sel("//div[@id='basic-info']/div[@class*='J-other']/p[@class^='info info-indent']/allText()");
			String otherString = "-";
			for (int i = 0; i < other.size(); i++) {
				if(i == 0 ){
					otherString = other.get(i) +"";
					continue;
				}
				otherString = otherString +"\002"+other.get(i);
			}
//			System.out.println("other:"+otherString);
			
			
			String Str = shanghuiID + "\001" +cityString +"\001"+daohangString+"\001"+shopnameString+"\001"+pingjiaString+"\001"+addressString+"\001"+telphoneString+"\001"+otherString+"\n";
			System.out.println(Str);
			
			if("".equals(cityString) && "".equals(daohangString)){
				file_no.write(Str);
				file_no.flush();
			}else{
				file.write(Str);
				file.flush();
			}
			
			
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
