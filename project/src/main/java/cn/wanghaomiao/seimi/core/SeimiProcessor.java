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
import cn.wanghaomiao.seimi.annotation.Interceptor;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.http.HttpClientFactory;
import cn.wanghaomiao.seimi.http.HttpMethod;
import cn.wanghaomiao.seimi.struct.BodyType;
import cn.wanghaomiao.seimi.struct.CrawlerModel;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.seimi.utils.StrFormatUtil;
import cn.wanghaomiao.seimi.utils.StructValidator;
import cn.wanghaomiao.xpath.exception.NoSuchAxisException;
import cn.wanghaomiao.xpath.exception.NoSuchFunctionException;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 汪浩淼 [et.tw@163.com]
 * @since 2015/8/21.
 */
public class SeimiProcessor implements Runnable {
    private SeimiQueue queue;
    private List<SeimiInterceptor> interceptors;
    private CrawlerModel crawlerModel;
    private BaseSeimiCrawler crawler;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public SeimiProcessor(List<SeimiInterceptor> interceptors,CrawlerModel crawlerModel){
        this.queue = crawlerModel.getQueueInstance();
        this.interceptors = interceptors;
        this.crawlerModel = crawlerModel;
        this.crawler = crawlerModel.getInstance();
        HttpParams pa=new BasicHttpParams();
        ConnManagerParams.setTimeout(pa, 2000);
        HttpConnectionParams.setConnectionTimeout(pa, 2000);
        HttpConnectionParams.setSoTimeout(pa, 2000);
    }
    private Pattern metaRefresh = Pattern.compile("<(?:META|meta|Meta)\\s+(?:HTTP-EQUIV|http-equiv)\\s*=\\s*\"refresh\".*URL=(.*)\">");
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
    @Override
    public void run() {
        while (true){
            Request request = null;
            try {
            	System.out.println("run"+queue.len(crawlerModel.getCrawlerName()));
                request = queue.bPop(crawlerModel.getCrawlerName());
                if (request==null){
                    continue;
                }
                if (crawlerModel==null){
                    logger.error("No such crawler name:'{}'",request.getCrawlerName());
                    continue;
                }
                if (request.isStop()){
                    logger.info("SeimiProcessor[{}] will stop!",Thread.currentThread().getName());
                    break;
                }
                //System.out.println("check");
                //对请求开始校验
                if (!StructValidator.validateAnno(request)){
                    logger.warn("Request={} is illegal",JSON.toJSONString(request));
                    continue;
                }
                if (!StructValidator.validateAllowRules(crawler.allowRules(),request.getUrl())){
                    logger.warn("Request={} will be dropped by allowRules=[{}]",JSON.toJSONString(request),StringUtils.join(crawler.allowRules(),","));
                    continue;
                }
                if (StructValidator.validateDenyRules(crawler.denyRules(),request.getUrl())){
                    logger.warn("Request={} will be dropped by denyRules=[{}]",JSON.toJSONString(request),StringUtils.join(crawler.denyRules(),","));
                    continue;
                }
                //如果启用了系统级去重机制并且为首次处理则判断一个Request是否已经被处理过了
                if (request.getCurrentReqCount()>=request.getMaxReqCount()){
                	//System.out.println("三次了");
                	crawler.handleErrorRequest(request);
                    continue;
                }
                if (!request.isSkipDuplicateFilter()&&crawlerModel.isUseUnrepeated() && queue.isProcessed(request)&& request.getCurrentReqCount()==0){
                    logger.info("This request has bean processed,so current request={} will be dropped!", JSON.toJSONString(request));
                    continue;
                }
                //System.out.println("add  process");
                queue.addProcessed(request);
                HttpClient hc;
               
                
                if (crawlerModel.isUseCookie()){
                    hc = HttpClientFactory.getHttpClient(10000,crawler.getCookieStore());
                }else {
                    hc = HttpClientFactory.getHttpClient(true);
                }
                
                RequestConfig config = RequestConfig.custom().setProxy(crawlerModel.getProxy()).build();
                RequestBuilder requestBuilder;
                if (HttpMethod.POST.equals(request.getHttpMethod())){
                	
                    requestBuilder = RequestBuilder.post().setUri(request.getUrl());
                }else {
                    requestBuilder = RequestBuilder.get().setUri(request.getUrl());
                }
//                if (request.getParams()!=null){
//                    for (Map.Entry<String,String> entry:request.getParams().entrySet()){
//                    	
//                        requestBuilder.addParameter(entry.getKey(),entry.getValue());
//                        System.out.println(entry.getKey()+" "+entry.getValue());
//                    }
//                    
//                }
                
                if (request.getParams()!=null){
                	List<NameValuePair> list = new ArrayList<NameValuePair>();
                    for (Map.Entry<String,String> entry:request.getParams().entrySet()){
                    	 NameValuePair sid = new BasicNameValuePair(entry.getKey(),entry.getValue());
                    	 list.add(sid);
                    	 //requestBuilder.addParameter(entry.getKey(),entry.getValue());
                        //System.out.println(entry.getKey()+" "+entry.getValue());
                    }
                    
                    StringEntity entity =new UrlEncodedFormEntity(list, HTTP.UTF_8);
                    		//new UrlEncodedFormEntity(list, HTTP.UTF_8);
                    requestBuilder.setEntity(entity);
                }
                
                requestBuilder.setConfig(config).setHeader("User-Agent",crawler.getUserAgent());
                if(request.getCookies()!=null){
                	requestBuilder.setConfig(config).setHeader("Cookie",request.getCookies());
                	//System.out.println(request.getCookies());
                	//System.out.println(request.getCookies());
                }
                if(request.getHeader()!=null){
                	Map<String,String> map=request.getHeader();
                	for (String key :map.keySet()){
                      	requestBuilder.setConfig(config).setHeader(key,map.get(key));
                      	System.out.println(key);
                      	System.out.println(map.get(key));
                	}
                  
                }
                requestBuilder.setConfig(config).setHeader("Proxy-Authorization",getSyncIp());
                //System.out.println("begin  execute");
                HttpContext httpContext = new BasicHttpContext();
                
               
                HttpResponse httpResponse = hc.execute(requestBuilder.build(),httpContext);
                //System.out.println("httpResponse  execute");
                //System.out.println(requestBuilder.build().getHeaders("Cookie").toString());
                Response seimiResponse = renderResponse(httpResponse,request,httpContext);
                Matcher mm = metaRefresh.matcher(seimiResponse.getContent());
                int refreshCount = 0;
                //System.out.println("metaRefresh  execute");
                while (mm.find()&&refreshCount<3){
                    String nextUrl = mm.group(1).replaceAll("'","");
                    if (!nextUrl.startsWith("http")){
                        String prefix = getRealUrl(httpContext);
                        nextUrl = prefix + nextUrl;
                    }
                    logger.info("Seimi refresh url to={} from={}",nextUrl,requestBuilder.getUri());
                    requestBuilder.setUri(nextUrl);
                    httpResponse = hc.execute(requestBuilder.build(),httpContext);
                    seimiResponse = renderResponse(httpResponse,request,httpContext);
                    mm = metaRefresh.matcher(seimiResponse.getContent());
                    refreshCount+=1;
                    System.out.println("metaRefresh  execute"+refreshCount);
                }
                //System.out.println("judge  requestCallback");
                Method requestCallback = crawlerModel.getMemberMethods().get(request.getCallBack());
                if (requestCallback==null){
                    continue;
                }
                for (SeimiInterceptor interceptor : interceptors) {
                    Interceptor interAnno = interceptor.getClass().getAnnotation(Interceptor.class);
                    if (interAnno.everyMethod()||requestCallback.isAnnotationPresent(interceptor.getTargetAnnotationClass())||crawlerModel.getClazz().isAnnotationPresent(interceptor.getTargetAnnotationClass())){
                        interceptor.before(requestCallback, seimiResponse);
                    }
                }
                if (crawlerModel.getDelay()>0){
                	//System.out.println("begin delay");
                    TimeUnit.SECONDS.sleep(crawlerModel.getDelay());
                    
                }
                //System.out.println("judge  getTrans");
                if(request.getTrans()!=null){
                	//requestBuilder.setConfig(config).setHeader("Cookie",request.getCookies());
                	seimiResponse.setTrans(request.getTrans());
                	//System.out.println(request.getTrans().toString());
                	//System.out.println(request.getCookies());
                }
                requestCallback.invoke(crawlerModel.getInstance(),seimiResponse);
                for (SeimiInterceptor interceptor : interceptors) {
                    Interceptor interAnno = interceptor.getClass().getAnnotation(Interceptor.class);
                    if (interAnno.everyMethod()||requestCallback.isAnnotationPresent(interceptor.getTargetAnnotationClass())||crawlerModel.getClazz().isAnnotationPresent(interceptor.getTargetAnnotationClass())){
                        interceptor.after(requestCallback, seimiResponse);
                    }
                }
                //System.out.println("judge  retry");
                //500也重试
                if(seimiResponse.getHttpResponse().getStatusLine().toString().indexOf("50")>0){
                    if (request == null){
                        continue;
                    }
                    //System.out.println("reg"+request.getCurrentReqCount());
                    //System.out.println("maxreg"+request.getMaxReqCount());
                    if (request.getCurrentReqCount()<request.getMaxReqCount()){
                        request.incrReqCount();
                        queue.push(request);
                        logger.info("Request process error1111,req will go into queue again,url={},maxReqCount={},currentReqCount={}",request.getUrl(),request.getMaxReqCount(),request.getCurrentReqCount());
                    }else if (request.getCurrentReqCount()>= request.getMaxReqCount()&& request.getMaxReqCount()>0){
                        crawler.handleErrorRequest(request);
                        logger.info("重试3次了");
                    }
                }
                	
                logger.debug("Crawler[{}] ,url={} ,responseStatus={}",crawlerModel.getCrawlerName(),request.getUrl(),httpResponse.getStatusLine().getStatusCode());
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                if (request == null){
                    continue;
                }
                System.out.println("reg"+request.getCurrentReqCount());
                System.out.println("maxreg"+request.getMaxReqCount());
                if (request.getCurrentReqCount()<request.getMaxReqCount()){
                    request.incrReqCount();
                    queue.push(request);
                    logger.info("Request process error1111,req will go into queue again,url={},maxReqCount={},currentReqCount={}",request.getUrl(),request.getMaxReqCount(),request.getCurrentReqCount());
                }else if (request.getCurrentReqCount()>= request.getMaxReqCount()&& request.getMaxReqCount()>0){
                    crawler.handleErrorRequest(request);
                    logger.info("重试3次了");
                }

            }
        }
    }

    private Response renderResponse(HttpResponse httpResponse,Request request,HttpContext httpContext){
        Response seimiResponse = new Response();
        HttpEntity entity = httpResponse.getEntity();
        seimiResponse.setHttpResponse(httpResponse);
        seimiResponse.setReponseEntity(entity);
        seimiResponse.setRealUrl(getRealUrl(httpContext));
        seimiResponse.setUrl(request.getUrl());
        seimiResponse.setRequest(request);
        seimiResponse.setMeta(request.getMeta());

        if (entity != null) {
            Header referer = httpResponse.getFirstHeader("Referer");
            if (referer!=null){
                seimiResponse.setReferer(referer.getValue());
            }
            if (!entity.getContentType().getValue().contains("image")){
                seimiResponse.setBodyType(BodyType.TEXT);
                try {
                    seimiResponse.setData(EntityUtils.toByteArray(entity));
                    ContentType contentType = ContentType.get(entity);
                    Charset charset = contentType.getCharset();
                    if (charset==null){
                        seimiResponse.setContent(new String(seimiResponse.getData(),"ISO-8859-1"));
                        String docCharset = renderRealCharset(seimiResponse);
                        seimiResponse.setContent(new String(seimiResponse.getContent().getBytes("ISO-8859-1"),docCharset));
                        seimiResponse.setCharset(docCharset);
                    }else {
                        seimiResponse.setContent(new String(seimiResponse.getData(),charset));
                        seimiResponse.setCharset(charset.name());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("no content data");
                }
            }else {
                seimiResponse.setBodyType(BodyType.BINARY);
                try {
                    seimiResponse.setData(EntityUtils.toByteArray(entity));
                    seimiResponse.setContent(StringUtils.substringAfterLast(request.getUrl(),"/"));
                } catch (Exception e) {
                    logger.error("no data can be read from httpResponse");
                }
            }
        }
        return seimiResponse;
    }

    private String renderRealCharset(Response response) throws NoSuchFunctionException, XpathSyntaxErrorException, NoSuchAxisException {
        String charset;
        JXDocument doc = response.document();
        charset = StrFormatUtil.getFirstEmStr(doc.sel("//meta[@charset]/@charset"),"").trim();
        if (StringUtils.isBlank(charset)){
            charset = StrFormatUtil.getFirstEmStr(doc.sel("//meta[@http-equiv='charset']/@content"),"").trim();
        }
        if (StringUtils.isBlank(charset)){
            String ct = StringUtils.join(doc.sel("//meta[@http-equiv='Content-Type']/@content|//meta[@http-equiv='content-type']/@content"),";").trim();
            charset = StrFormatUtil.parseCharset(ct.toLowerCase());
        }
        return StringUtils.isNotBlank(charset)?charset:"UTF-8";
    }

    private String getRealUrl(HttpContext httpContext){
        Object target = httpContext.getAttribute(HttpCoreContext.HTTP_TARGET_HOST);
        Object reqUri = httpContext.getAttribute(HttpCoreContext.HTTP_REQUEST);
        if (target==null||reqUri==null){
            return null;
        }
        HttpHost t = (HttpHost) target;
        HttpUriRequest r = (HttpUriRequest)reqUri;
        
        return r.getURI().isAbsolute()?r.getURI().toString():t.toString()+r.getURI().toString();
    }
}
