package com.kaola.crawlers;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class ConstructionCompanyHttpClient  {

	public static void main(String[] args) {
		HttpClient hc = new HttpClient();
		GetMethod get  = new GetMethod("http://210.12.219.18/jianguanfabuweb/handler/GetCompanyData.ashx?method=GetEngineersData&name=&card=&stampnum=&company=&major=-1&PageIndex=2&PageSize=");
	    hc.getHttpConnectionManager().getParams().setConnectionTimeout(4000);
	    hc.getHttpConnectionManager().getParams().setSoTimeout(4000);
	    
	    try {
			hc.executeMethod(get);
		    String  body = get.getResponseBodyAsString();
		    System.out.println(body);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	}
    
}
