package com.dyrs.spider.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.dyrs.spider.dbs.MyPipeline;
import com.dyrs.spider.dbs.SpiderDefeatURLDB;
import com.dyrs.spider.dbs.SpiderPageDB;
import com.dyrs.spider.dbs.SpiderURLDB;
import com.dyrs.spider.download.PageParse;
import com.dyrs.spider.download.Spider;
import com.dyrs.spider.models.SpiderDefeatURLUser;
import com.dyrs.spider.models.SpiderPageUser;
import com.dyrs.spider.models.SpiderURLUser;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://172.16.254.99:3306/netspider?characterEncoding=utf-8&useUnicode=true&", "spider", "spider@&*()");
        c3p0Plugin.start();
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.addMapping("spider_url","url_md5", SpiderURLUser.class);
        activeRecordPlugin.addMapping("spider_defeat_url","url_md5", SpiderDefeatURLUser.class);
        activeRecordPlugin.addMapping("spider_page","url_md5", SpiderPageUser.class);
        activeRecordPlugin.start();
        
        SpiderURLDB spiderURLDB = new MyPipeline();
        int i = 0;
        List<SpiderURLUser> urlList = spiderURLDB.selectSpiderURLs(i, 10);
        int len = urlList.size();
        SpiderPageDB spiderPageDB = new MyPipeline();
        SpiderDefeatURLDB spiderDefeatURLDB = new MyPipeline();
        while(len > 0){
	        Spider spider = new Spider();
	        for(int index = 0; index < len; index++){
	        	String url = urlList.get(index).getStr("url");
	        	if(url == null || url.equals("")){
	        		continue;
	        	}
	        	String url_md5 =DigestUtils.md5Hex(url);
	        	Date date = new Date();
	      	    String create_time = null;
	      	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	      	    create_time = df.format(date);
	        	HashMap<String, String> map = new HashMap<String, String>();
	    		Response response = spider.downloadPage(url, 4 * 1000, map, Method.GET, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
	    		if(response != null){
	    			int status = response.statusCode();
	    			if(status == 200){
	    				Document document = response.parse();
	    				PageParse pageParse = new PageParse();
	    				HashSet<String> linkSet = pageParse.getAllURL(document);
	    				spiderURLDB.insertSpiderURLs(linkSet);
	    				
	    				HashMap<String, Object> pageMap = new HashMap<String, Object>();
	    	    		pageMap.put("url", url);
	    	      	    pageMap.put("url_md5", url_md5);
	    	      	    pageMap.put("content", document.html());
	    	      	    pageMap.put("create_time", create_time);
	    	      	    spiderPageDB.insertSpiderPageDB(pageMap);
	    	      	    
	    			} else {
	    				HashMap<String, Object> defeatURLMap = new HashMap<String, Object>();
	    				defeatURLMap.put("url_md5", url_md5);
	    				defeatURLMap.put("url", url);
	    				defeatURLMap.put("status", status);
	    				defeatURLMap.put("create_time", create_time);
	    				spiderDefeatURLDB.insertSpiderDefeatURLDB(defeatURLMap);
	    			}
	    		} else {
	    			HashMap<String, Object> defeatURLMap = new HashMap<String, Object>();
    				defeatURLMap.put("url_md5", url_md5);
    				defeatURLMap.put("url", url);
    				defeatURLMap.put("status", 503);
    				defeatURLMap.put("create_time", create_time);
    				spiderDefeatURLDB.insertSpiderDefeatURLDB(defeatURLMap);
	    		}
	    		
	        }
	        i += len < 10 ? len : 10;
	        urlList = spiderURLDB.selectSpiderURLs(i, 10);
	        len = urlList.size();
        }
	}

}
