package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "pcBulletin", delay = 15)
public class PeopleCourtBulletinSpider extends BaseSeimiCrawler {

	/**
	 * 此类是爬取人民法院公告网 中公告详细数据
	 * 公告类型  当事人 公告人 刊登版面 刊登日期 上传日期 附件地址 公告内容
	 */
	private BufferedWriter file = null;
	private BufferedWriter file_no = null;
	private String date_="2016-07-19";
	private static int count=7;//测试网补充数据
	//private static int count=7;//爬取前一周的数据
	private long sleep_time= 15*1000;//毫秒
	private boolean isStartExe=false; //启动时是否立即执行，true是执行，false不执行

	@Override
	public String[] startUrls() {
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
   	    Calendar cal = Calendar.getInstance();
   	    cal.add(Calendar.DAY_OF_MONTH, -(count));
   	    date_ = myFmt1.format(cal.getTime());
   	  
		try {
			file = new BufferedWriter(new FileWriter("/data/crawler/peoplecountbulletin/peoplecountbulletin_"+date_+".txt", false));
			file_no=new BufferedWriter(new FileWriter("/data/crawler/peoplecountbulletin/peoplecountbulletin_no.txt",true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 初始化页面
		return new String[] { "http://rmfygg.court.gov.cn/psca/lgnot/bulletin/"+date_+"_0_0.html" };
	}

	@Override
	public void start(Response response) {
		String content = response.getContent();
		String reg = "<a href=\"/psca/lgnot/bulletin/"+date_+"_0_(\\d+)\\.html\">末页</a>";
		System.out.println(reg);
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(content);
		
		int pageNum = 0;// 信用评价记录的页数
		while (mat.find()) {
			pageNum = Integer.parseInt(mat.group(1));
		}
		System.out.println("pageNum is :" + pageNum);
		
		//pageNum = 30; // 测试页数写成固定页数

		if(isStartExe){ //根据判断是否在启动时执行此段业务
			callback(response);// 第一页
			for (int i = 1; i <= pageNum; i++) {
				try {
					Thread.sleep(sleep_time);
					if( i == pageNum){
						System.out.println("===============this is last page=====pageNum is ====="+pageNum+"============");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				push(new Request("http://rmfygg.court.gov.cn/psca/lgnot/bulletin/"+date_+"_0_" + i + ".html", "callback").setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.GET));
			}
		}
		
		System.out.println("isStartExe'value is  " + isStartExe);
		System.out.println("start run queue size " + pageNum);
	}
    
	/*
	 * 获取列表页面每行记录的URL地址
	 */
	public void callback(Response response) {
		JXDocument doc = response.document();
		try {
			List<Object> list = doc.sel("//div[@class='content']/div[@class='contentDiv']//tr/child::node()");

			for (Object tmp : list) {
				Element el = (Element) tmp;
				String tagName = el.tagName();
				if (!"tr".equals(tagName)) {
					continue;
				}
				List<Element> children = el.children();
				if ("th".equals(children.get(0).tagName())) {
					continue;
				}

				for (int i = 0; i < children.size(); i++) {
					String href = children.get(i).children().get(0).attr("href");
					String neinei = href.substring(href.lastIndexOf("/") + 1);
					href = "/bulletin/court/"+date_.replaceAll("-","/")+"/block"+ neinei;
					
					System.out.println(href);
					String request = "http://rmfygg.court.gov.cn" + href;
					try {
						Thread.sleep(sleep_time);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					push(new Request(request, "getData").setSkipDuplicateFilter(true));
					break;
				}
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
		try {
			String[] strs = new String[] { "", "", "", "", "", "", "", "-" };

			List<Object> list1 = doc.sel("//div[@class^='wrap1000']//div[@class='d21']/p/text()");
			List<Object> list_content = doc.sel("//div[@class^='wrap1000']//div[@class^='d22']/p/text()");
			List<Object> list3 = doc.sel("//div[@class^='wrap1000']//div[@class^='d23']/p/text()");

			List<Object> list3_2 = new ArrayList<Object>();
			for (int i = 0; i < list3.size(); i++) {
				String tmp = (String) list3.get(i);
				if (tmp.trim().startsWith("刊登版面：")) {
					tmp = tmp.replaceAll("刊登版面：", "");
				}
				if (tmp.trim().startsWith("刊登日期：")) {
					tmp = tmp.replaceAll("刊登日期：", "");
				}
				if (tmp.trim().startsWith("上传日期：")) {
					tmp = tmp.replaceAll("上传日期：", "");
				}
				list3_2.add(tmp);
			}

			List<Object> list4 = doc.sel("//div[@class^='wrap1000']//div[@class^='d24']/a/@href");

			for (int i = 0; i < list1.size(); i++) {
				strs[i] = (String) list1.get(i);
			}

			for (int i = 0; i < list3_2.size(); i++) {
				strs[2 + i] = (String) list3_2.get(i);
			}

			for (int i = 0; i < list4.size(); i++) {
				strs[6] ="http://rmfygg.court.gov.cn"+ (String) list4.get(i);
			}
			
			for (int i = 0; i < list_content.size(); i++) {
				strs[7] = (String) list_content.get(i);
			}
			
			if("".equals(strs[0]) && "-".equals(strs[7]) ){
				System.out.println("data is return null");
				return;
			}

			String tt = ArrToStr(strs);
			System.out.println(strs[0]+":"+strs[1]);
			 
			if (tt.split("\001").length == 8) {
				file.write(tt);
			}else {
				file_no.write(tt);
			}
			file.flush();
			file_no.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	 /**
	  * 每天9.00定时爬 10.2.60.1
	  * 每天4.00定时爬 10.5.28.14
	  */
	 @Scheduled(cron = "0 0 9 * * ?")
	 //@Scheduled(cron = "0 0 4 * * ?")
	 public void callByCron(){
	      isStartExe = true; //当任务启动的时候，置为可执行
	      try {
	    	  try{
	    		  file.close();
	    		  file_no.close();
	    	  }
	    	  catch(Exception o){
	    		  o.printStackTrace();
	    	  }
	     	  SimpleDateFormat myFmt1=new SimpleDateFormat("yyyy-MM-dd");
	     	  Calendar cal = Calendar.getInstance();
	     	  cal.add(Calendar.DAY_OF_MONTH, -(count));
	     	  date_ = myFmt1.format(cal.getTime());
	     	  
	     	  System.out.println("---PeopleCourtBulletinSpider---Scheduled----执行---->>> date_ is "+ date_);
	     	  
	     	  push(new Request(startUrls()[0], "start").setSkipDuplicateFilter(true).setHttpMethod(HttpMethod.GET));
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
			str = str + arr[j].replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "");
			if (j == arr.length - 1) {
				str = str + "\n";
			} else {
				str = str + "\001";
			}
		}
		return str;
	}
}
