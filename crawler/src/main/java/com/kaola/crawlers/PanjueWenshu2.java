package com.kaola.crawlers;

import java.util.List;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.def.DefaultLocalQueue;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "panjuewenshu")
public class PanjueWenshu2  extends BaseSeimiCrawler {
	private FileWriter  filew=null;
	private FileWriter  filec=null;
	private int page=0;
	private int num=0;
    @Override
    public String[] startUrls() {
    	try{
    		filew=new FileWriter("d:\\panjuewenshu.txt");
    		
    	}catch(Exception o){
    		
    	}
    	//return null;
        return new String[]{"http://wenshu.court.gov.cn"};
    }
    @Override
    public String proxy() {
        String[] proxies = new String[]{"socket://10.5.13.22:3127","http://10.5.13.22:3127"};
        return proxies[RandomUtils.nextInt()%proxies.length];
    }
/*    @Override
    public String getUserAgent(){
        String[] uas = new String[]{"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/38.0"
        };
        return uas[RandomUtils.nextInt()%uas.length]+" "+ RandomStringUtils.randomAlphanumeric(6);
    }*/

    @Override
    public void start(Response response) {
    	//System.out.println(response.getContent());
        JXDocument doc = response.document();
        
        //System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
        	     
            for (long i=6;i<8;i++){
            	//String cookie="td_cookie=1115211982; yunsuo_session_verify=5d46cf58327e4a5f3519f1efc4658b82; ASP.NET_SessionId=ku3pduju135lobo2czxaosye; wzwsconfirm=60fd027aee370f32cf5dd7dce104f0ce; wzwstemplate=MQ==; ccpassport=cd870f13ce6df66c530bceea381ed69b; wzwschallenge=-1; _gsref_2116842793=http://wenshu.court.gov.cn/list/list/?sorttype=1&conditions=searchWord+2+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E6%B0%91%E4%BA%8B%E6%A1%88%E4%BB%B6&conditions=searchWord++CPRQ++%E8%A3%81%E5%88%A4%E6%97%A5%E6%9C%9F:2015-10-01%20TO%202016-03-07; _gscu_2116842793=57316986ze31k858; _gscs_2116842793=t57329362jy8pur73|pv:1; _gscbrs_2116842793=1";
            	HashMap<String,String> map1=new HashMap<String,String>();

               	//map1.put("Param", "案件类型:民事案件");
               	map1.put("Param", "案件类型:民事案件,裁判日期:2016-03-01 TO 2016-03-07");
               	
                //System.out.println("Param "+java.net.URLEncoder.encode("案件类型:民事案件","utf-8"));
            	map1.put("Index","4");
            	map1.put("Page","5");
                map1.put("Order","法院层级");
            	map1.put("Direction","asc");
                     
          	    HashMap<String,String> map=new HashMap<String,String>();
         	
            	//map.put("Referer","http://wenshu.court.gov.cn/list/list/?sorttype=1&conditions=searchWord+2+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E6%B0%91%E4%BA%8B%E6%A1%88%E4%BB%B6&conditions=searchWord++CPRQ++%E8%A3%81%E5%88%A4%E6%97%A5%E6%9C%9F:2016-03-01%20TO%202016-03-07");
            	//map.put("Accept","*/*");
            	//map.put("Accept-Encoding", "gzip, deflate");
            	//map.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            	//map.put("Connection", "keep-alive");
            	//map.put("Content-Length", "354");
           	
            	//map.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            	//map.put("Content-Length", "354");
            	//map.put("Cookie", "_gscu_1241932522=503454142xrwww84; _gscbrs_1241932522=1; _gscu_1049835508=51288684fug20s14; _gscbrs_1049835508=1; yunsuo_session_verify=54037a42c304bbf5f267baa9d3bbfeae; wzwsconfirm=78adf3236a8975f834e7fe187ea6328d; wzwstemplate=OQ==; ccpassport=fa82df0ced72ad090bd656f6c6a88ca7; wzwschallenge=-1; _gscu_2116842793=56969540wplf8j15; _gscs_2116842793=t57341001bivjhs10|pv:7; _gscbrs_2116842793=1");
            	map.put("Cookie", "_gscu_1241932522=503454142xrwww84; _gscbrs_1241932522=1; _gscu_1049835508=51288684fug20s14; _gscbrs_1049835508=1; yunsuo_session_verify=54037a42c304bbf5f267baa9d3bbfeae; _gsref_2116842793=http://wenshu.court.gov.cn/List/List?sorttype=1&conditions=searchWord+2+AJLX++%E6%A1%88%E4%BB%B6%E7%B1%BB%E5%9E%8B:%E6%B0%91%E4%BA%8B%E6%A1%88%E4%BB%B6; _gscu_2116842793=56969540wplf8j15; _gscs_2116842793=t57341001bivjhs10|pv:8; _gscbrs_2116842793=1; wzwsconfirm=0e79d2f0dca5d51682ca31705ed7bdfa; wzwstemplate=NQ==; ccpassport=588cab4e94c2737a3753cf9f4b80a04b; wzwschallenge=-1");

            	//map.put("Origin", "http://wenshu.court.gov.cn");
            	//map.put("Host", "wenshu.court.gov.cn");
            	//map.put("X-Requested-With", "XMLHttpRequest");
            	//map.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
  
                push(new Request("http://wenshu.court.gov.cn/List/ListContent","getData").setHttpMethod(HttpMethod.POST).setParams(map1).setHeader(map).setSkipDuplicateFilter(true));
                
                //push(new Request("http://wenshu.court.gov.cn/List/ListContent","getData").setParams(map1).setSkipDuplicateFilter(true));
                //push(new Request("http://www.sina.com.cn","getData"));
            	//System.out.println(i);
                //push(new Request("http://zhixing.court.gov.cn/search/detail?id="+i,"getData"));
            }
        } catch (Exception e) {
        	System.out.println("异常了");
            e.printStackTrace();
        }
    }
    public void getData(Response response){
    	try {
	    	System.out.println(response.getRealUrl());
	    	System.out.println(response.getHttpResponse().getFirstHeader("Cookie").getValue());
	    	System.out.println(response.getHttpResponse().getStatusLine().toString());
	    	if(response.getHttpResponse().getStatusLine().toString().indexOf("502")>0)
	    		return;
	    	String  str=response.getContent();
	    	//System.out.println(new String(response.getData()));
	    	//System.out.println(response.getHttpResponse().getFirstHeader("Cookie"));
	    	System.out.println("**"+str);
	    	int begin=str.indexOf("\"},")	;	
	    	//str="["+str.substring(begin+3);
	    	//return ;
	    	str=str.replaceAll("\\\"", "'");
	    	str=str.replaceAll("\\\'", "^");
	    	//System.out.println(str);
	    	
	    	str="[{\"Count\":\"1236992\"},{\"裁判要旨段原文\":\"本院经审查认为：上诉人青岛新世界广场实业有限责任公司撤回上诉的申请，系当事人对其诉讼权利的自由处分，不违反法律和行政法规的禁止性规定，也不损害国家和社会公共利益及他人的合法权益，符合法律规定，是当事人的真实意思表示，应予准许。依照《中华人民共和国民事诉讼法》第一百五十四条第一款第（五）项、第一百七十三条的规定，裁定如下\",\"DocContent\":\"中华人民共和国最高人民法院\\n民 事 裁 定 书\\n（2015）民一终字第270号\\n上诉人（一审被告）：青岛新世界广场实业有限责任公司。住所地：山东省青岛市城阳区正阳路151号世正爱丽安综合楼1号楼706室。\\n法定代表人：韩秀华，该公司董事长。\\n委托代理人：朱海燕，该公司职员。\\n被上诉人（一审原告）：青岛国融集团有限公司。住所地：青岛经济技术开发区黄河东路81号中新大厦。\\n法定代表人：张德亮，该公司董事长。\\n委托代理人：刘凌云，北京市金杜律师事务所上海分所律师。\\n委托代理人：蔡晨程，北京市金杜律师事务所上海分所律师。\\n一审被告：阎德明。\\n一审被告：郑奎。\\n一审被告：于发勤。\\n上诉人青岛新世界广场实业有限责任公司为与被上诉人青岛国融集团有限公司、一审被告阎德明、郑奎、于发勤民间借贷纠纷一案，不服山东省高级人民法院（2013）鲁民一初字第16号民事判决，向本院提出上诉后，同时又向本院申请撤回上诉。\\n本院经审查认为：上诉人青岛新世界广场实业有限责任公司撤回上诉的申请，系当事人对其诉讼权利的自由处分，不违反法律和行政法规的禁止性规定，也不损害国家和社会公共利益及他人的合法权益，符合法律规定，是当事人的真实意思表示，应予准许。依照《中华人民共和国民事诉讼法》第一百五十四条第一款第（五）项、第一百\"}]";
	
			str=str.replaceAll("\\\"", "'");
			//System.out.println(str);
	    	
	    	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        
            Gson gson = new Gson();
            list = gson.fromJson(str,
                    new TypeToken<List<Map<String, String>>>() {
                    }.getType());
            for(int i=0;i<list.size();i++){
//            	System.out.println("*****************");
//
//            	System.out.println(list.get(i).get("裁判要旨段原文"));
//            	System.out.println(list.get(i).get("DocContent"));
//            	System.out.println(list.get(i).get("审判程序"));
//            	System.out.println(list.get(i).get("案号"));
//            	System.out.println(list.get(i).get("法院名称"));
//            	
//            	System.out.println(list.get(i).get("审判程序"));
//            	System.out.println(list.get(i).get("文书ID"));
//            	System.out.println(list.get(i).get("案件名称"));
//            	System.out.println(list.get(i).get("裁判日期"));
//            	System.out.println(list.get(i).get("案件类型"));
            	
            	
            	
            	
//            	if(list.get(i).get("cardNum").length()>11){
//	            	filew.write(id+"\001"+id+"\001"+list.get(i).get("iname")+"\001"+list.get(i).get("caseCode")+"\001"+list.get(i).get("age")+"\001"+list.get(i).get("sexy")+"\001"
//	            			+list.get(i).get("cardNum")+"\001"+list.get(i).get("courtName")+"\001"+list.get(i).get("areaName")+"\001"+list.get(i).get("gistId")+"\001"
//	            			+list.get(i).get("regDate")+"\001"+list.get(i).get("gistUnit")+"\001"+list.get(i).get("duty")+"\001"+list.get(i).get("performance")+"\001"
//	            			+list.get(i).get("disruptTypeName")+"\001"+list.get(i).get("publishDate")+"\001"+list.get(i).get("_update_time")+"\001"+list.get(i).get("partyTypeName")+"\n\r");
//	            	filew.flush();
//            	}
            
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
            // TODO: handle exception
      }
    }
 // @Scheduled(cron = "0/5 * * * * ?")
//  public void callByCron(){
//      logger.info("我是一个根据cron表达式执行的调度器，5秒一次");
//   	try{
//		  filew=new FileWriter("d:\\shixin.txt");
//		  filec=new FileWriter("d:\\shixinc.txt");
//		  //DefaultLocalQueue qq=new DefaultLocalQueue();
//		  //this.setQueue(qq);
//	      for (long i=0;i<2000;i=i+50){
//          	System.out.println(i);
//          	 //push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//              push(new Request("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6899&query=%E5%A4%B1%E4%BF%A1%E8%A2%AB%E6%89%A7%E8%A1%8C%E4%BA%BA&pn="+i+"&rn=10&ie=utf-8&oe=utf-8&format=json&t=1455870490009&cb=jQuery1102049660134187316657_1455863650458&_=1455863650463","getData").setSkipDuplicateFilter(true));
//          }
//		
//	}catch(Exception o){
//		
//	}
//      // 可定时发送一个Request
//      // push(Request.build(startUrls()[0],"start").setSkipDuplicateFilter(true));
//  }
    
}