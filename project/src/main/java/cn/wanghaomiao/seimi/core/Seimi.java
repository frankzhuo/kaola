package cn.wanghaomiao.seimi.core;

/*
   Copyright 2015 - now original author

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.httpd.CrawlerStatusHttpProcessor;
import cn.wanghaomiao.seimi.httpd.PushRequestHttpProcessor;
import cn.wanghaomiao.seimi.httpd.SeimiHttpHandler;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import cn.wanghaomiao.seimi.struct.Request;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author 汪浩淼 [et.tw@163.com]
 * @since 2015/10/16.
 */
public class Seimi extends SeimiContext {
    /**
     * 主启动
     * start master
     * @param crawlerNames
     */
    public void start(String... crawlerNames){
        if (crawlerNames==null||crawlerNames.length==0){
            for (Map.Entry<String,CrawlerModel> entry:crawlerModelContext.entrySet()){
                sendRequest(entry.getKey(),entry.getValue().getQueueInstance(),entry.getValue().getInstance().startUrls());
            }
        }else {
            for (String name:crawlerNames){
                CrawlerModel crawlerModel = crawlerModelContext.get(name);
                if (crawlerModel!=null){
                    sendRequest(crawlerModel.getCrawlerName(),crawlerModel.getQueueInstance(),crawlerModel.getInstance().startUrls());
                }else {
                    logger.error("error crawler name '{}',can not find it!",name);
                }
            }
        }
    }

    /**
     * 按名称启动爬虫并开启http服务接口API
     */
    public void startWithHttpd(int port,String... crawlerNames){
        start(crawlerNames);
        SeimiHttpHandler seimiHttpHandler = new SeimiHttpHandler(crawlerModelContext);
        if (crawlerNames==null||crawlerNames.length==0){
            for (Map.Entry<String,CrawlerModel> entry:crawlerModelContext.entrySet()){
                seimiHttpHandler.add("/push/"+entry.getKey(),new PushRequestHttpProcessor(entry.getValue().getQueueInstance(),entry.getKey()))
                        .add("/status/"+entry.getKey(),new CrawlerStatusHttpProcessor(entry.getValue().getQueueInstance(),entry.getKey()));
            }
        }else {
            for (String name:crawlerNames){
                CrawlerModel crawlerModel = crawlerModelContext.get(name);
                if (crawlerModel!=null){
                    seimiHttpHandler.add("/push/"+name,new PushRequestHttpProcessor(crawlerModel.getQueueInstance(),name))
                            .add("/status/"+name,new CrawlerStatusHttpProcessor(crawlerModel.getQueueInstance(),name));
                }
            }
        }
        logger.info("Http request push service also started on port:{}",port);
        startJetty(port,seimiHttpHandler);
    }

    public void startAll(){
        start();
    }
    public void startAllWithHttpd(int port){
        startWithHttpd(port);
    }
    public void startWorkers(){
        //初始化Seimi对象时即完成了workers的创建，故这里仅用作引导说明。
        logger.info("workers started!");
    }
    public static String getSyncIp(){
		// 定义申请获得的appKey和appSecret
		String appkey = "215106405";
		String secret = "42a0fb022a9d5a82f77e63bc6ba2ab09";
		 
		// 创建参数表
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("app_key", appkey);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));//使用中国时间，以免时区不同导致认证错误
		paramMap.put("timestamp", format.format(new Date()));
		 
		// 对参数名进行排序
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);
		 
		// 拼接有序的参数名-值串
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(secret);
		for(String key : keyArray){
		    stringBuilder.append(key).append(paramMap.get(key));
		}
		     
		stringBuilder.append(secret);
		String codes = stringBuilder.toString();
		         
		// MD5编码并转为大写， 这里使用的是Apache codec
		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(codes).toUpperCase();

		paramMap.put("sign", sign);

		// 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
		String authHeader = "MYH-AUTH-MD5 sign=" +sign+"&timestamp="+format.format(new Date())+"&app_key=" +appkey;

//		System.out.println(authHeader);

		//接下来使用蚂蚁动态代理进行访问
		   
		//这里以jsoup为例，将authHeader放入请求头中即可
		//原版的jsoup设置代理不方便，这里使用E-lai提供的修改版(https://github.com/E-lai/jsoup-proxy)
		//注意authHeader必须在每次请求时都重新计算，要不然会因为时间误差而认证失败
//		Jsoup.connect("http://1212.ip138.com/ic.asp");
		return authHeader;
				
	}
    private void sendRequest(String crawlerName,SeimiQueue queue,String[] startUrls){
        if (ArrayUtils.isNotEmpty(startUrls)){
            for (String url:startUrls){
                Request request = new Request();
                String[] urlPies = url.split("##");
                if (urlPies.length>=2&& StringUtils.lowerCase(urlPies[1]).equals("post")){
                    request.setHttpMethod(HttpMethod.POST);
                }
                request.setCrawlerName(crawlerName);
                //request.seth
                Map<String,String> map1= new HashMap();
                map1.put("Proxy-Authorization",getSyncIp());
                request.setHeader(map1);
                System.out.println(getSyncIp());
                request.setUrl(url);
                request.setCallBack("start");
                queue.push(request);
                logger.info("{} url={} started",crawlerName,url);
            }
        }else {
            logger.error("crawler:{} can not find start urls!",crawlerName);
        }
    }

    private void startJetty(int port,SeimiHttpHandler seimiHttpHandler){
        Server server = new Server(port);
        server.setHandler(seimiHttpHandler);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error("http service start error,{}",e.getMessage(),e);
        }
    }
}
