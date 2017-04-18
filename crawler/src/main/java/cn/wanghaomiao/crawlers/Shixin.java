package cn.wanghaomiao.crawlers;

import java.util.List;

import cn.wanghaomiao.seimi.annotation.Crawler;
import cn.wanghaomiao.seimi.def.BaseSeimiCrawler;
import cn.wanghaomiao.seimi.struct.Request;
import cn.wanghaomiao.seimi.struct.Response;
import cn.wanghaomiao.xpath.model.JXDocument;

//@Crawler(name = "shixin")

public class Shixin extends BaseSeimiCrawler {
    @Override
    public String[] startUrls() {
        return new String[]{"http://www.sina.com.cn/"};
    }
    @Override
    public void start(Response response) {
        JXDocument doc = response.document();
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        try {
            //List<Object> urls = doc.sel("/@<a");
            List<Object> urls = doc.sel("//a/@href");
            logger.info("{}", urls.size());
            for (Object s:urls){
            	System.out.println(s.toString());
                push(new Request(s.toString(),"getTitle"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getTitle(Response response){
        JXDocument doc = response.document();
        try {
        	System.out.println("*****");
            logger.info("url:{} {}", response.getUrl(), doc.sel("//h1[@class='postTitle']/a/text()|//a[@id='cb_post_title_url']/text()"));
            //do something
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}