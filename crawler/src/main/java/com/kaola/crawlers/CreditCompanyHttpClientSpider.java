package com.kaola.crawlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CreditCompanyHttpClientSpider {

	/**
	 * http://credit.ndrc.gov.cn/XYXX/admin_client/form_designer/ttt/index.html 
	 * http://credit.ndrc.gov.cn/XYXX/doPost;JSESSIONID=wKghggBQWHRAR3ubZ-pEyEZLte6-a0GzvkgA
	 * 中华人民共和国国家发展和改革委员会 信用企业
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
	    HttpClient client = new HttpClient(); 
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(40000);
	    client.getHttpConnectionManager().getParams().setSoTimeout(40000);
	    String url = "http://credit.ndrc.gov.cn/XYXX/doPost;JSESSIONID=wKghggBQWHRAR3ubZ-pEyEZLte6-a0GzvkgA";
	    
	    PostMethod method1 = new PostMethod(url); 
	    BufferedWriter bufferwrite = new BufferedWriter(new FileWriter(new File("d://data/creditcompany.txt"),false));
	    
	    
	    for(int i=10000;i<=52356;i=i+100){
	    	System.out.println("min:"+i+"---max:"+(i+100));
	    	changeFile(client, method1, bufferwrite, i, (i+100));
	    	Thread.sleep(240*1000);
	    }
   }
	
	public static void changeFile(HttpClient client,PostMethod method1,BufferedWriter bufferwrite,int min,int max){
		 try{
		    	String ttt =new String(Base64.encode(("<Request  action=\"admin_client/form_designer/ttt/action/getinfo.xml\" request=\"JSON\" response=\"JSON\" ><Data>{\"MINNUM\":"+min+",\"MAXNUM\":"+max+",\"pageSize\":100,\"MLLX\":\"\",\"SX\":\"\",\"ZN\":\"\",\"XYXXJL\":\"\",\"XYZTMC\":\"\",\"XYDM\":\"\",\"XYZTID\":\"\",\"SJ\":\"\"}</Data></Request>").getBytes()));
		    	System.out.println(ttt);
		    	method1.setRequestEntity(new StringRequestEntity(ttt));
		    	
		    	method1.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		    	method1.setRequestHeader("Cookie", "JSESSIONID=wKghggBQWHRAR3ubZ-pEyEZLte6-a0GzvkgA");
		    	method1.setRequestHeader("Connection", "keep-alive");
		    	method1.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		    	 
		    	client.executeMethod(method1);  
		    	byte[] bt =method1.getResponseBody();
		   
		    	String re = new String(bt);
		     	if(re.contains("Not found"))
		    	{
		    		System.out.println("-----------没有数据---------------");
		    		System.exit(-1);
		    	}else{
			    	JSONObject json = JSONObject.fromObject(re);
			    	JSONObject result = json.getJSONObject("Result");
			    	JSONObject data = result.getJSONObject("Data");
			    	JSONArray rows = data.getJSONArray("ROWS");
			    	for (int i = 0; i < rows.size(); i++) {
			    		String ID = rows.getJSONObject(i).getString("ID"); //id
			    		String XYZTMC = rows.getJSONObject(i).getString("XYZTMC");//信用主体名称
			    		String XYZTID = rows.getJSONObject(i).getString("XYZTID");//组织机构代码
			    		String ZN = rows.getJSONObject(i).getString("ZN");//职能
			    		String FWWH = rows.getJSONObject(i).getString("FWWH");//发文文号
			    		String SFPZ = rows.getJSONObject(i).getString("SFPZ");//是否批准
			    		String SJ = rows.getJSONObject(i).getString("SJ");//办理司局
			    		String TYPE = rows.getJSONObject(i).getString("TYPE");
			    		String DATETIME = rows.getJSONObject(i).getString("DATETIME");//创建时间
			    		String rr = ID+"\001"+XYZTMC+"\001"+XYZTID+"\001"+ZN+"\001"+FWWH+"\001"+SFPZ+"\001"+SJ+"\001"+TYPE+"\001"+DATETIME+"\n";
			    		System.out.println(rr);
			    		bufferwrite.write(rr);
					}
			    	bufferwrite.flush();
		    	}
		    }catch(Exception o){
		    	o.printStackTrace();
		    }
	}
}
