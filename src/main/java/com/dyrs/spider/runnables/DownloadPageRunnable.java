package com.dyrs.spider.runnables;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import com.dyrs.spider.dbs.MyPipeline;
import com.dyrs.spider.dbs.SpiderDefeatURLDB;
import com.dyrs.spider.dbs.SpiderPageDB;
import com.dyrs.spider.dbs.SpiderURLDB;
import com.dyrs.spider.download.PageParse;
import com.dyrs.spider.download.Spider;
import com.dyrs.spider.queue.URLQueue;

public class DownloadPageRunnable implements Runnable{
	Logger logger = Logger.getLogger(this.getClass());
	static List<String> saveRegexList;
	static{
		try {
			String filePathTMP = DownloadPageRunnable.class.getResource("/").toString() + "conf/savepageregex.txt";
			String filePath = filePathTMP.replace("file:/", "");
			System.out.println(filePath); 
			saveRegexList = FileUtils.readLines(new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	public void run() {
		// TODO Auto-generated method stub
		int queueLen = URLQueue.list.size();
		Spider spider = new Spider();
		SpiderPageDB spiderPageDB = new MyPipeline();
        SpiderDefeatURLDB spiderDefeatURLDB = new MyPipeline();
        SpiderURLDB spiderURLDB = new MyPipeline();
		while(queueLen > 0){
			String url = URLQueue.list.remove(0);
			this.logger.info("开始获取URL的页面：" + url);
			String url_md5 =DigestUtils.md5Hex(url);
        	Date date = new Date();
      	    String create_time = null;
      	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
      	    create_time = df.format(date);
        	HashMap<String, String> map = new HashMap<String, String>();
    		Response response = spider.downloadPage(url, 10 * 1000, map, Method.GET, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");
    		if(response != null){
    			int status = response.statusCode();
    			if(status == 200){
    				Document document = null;
					try {
						document = response.parse();
					} catch (IOException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
						this.logger.info("url:" + url + "parse 错误 !");
						queueLen = URLQueue.list.size();
						continue;
					}
    				PageParse pageParse = new PageParse();
    				/*
    				 * 提取页面上所有的URL，并入库 
    				 */
    				HashSet<String> linkSet = pageParse.getAllURL(document);
    				spiderURLDB.insertSpiderURLs(linkSet);
    				
    				/*
    				 * 判断是否符合存储页面的正则
    				 * */
    				for(int index = 0, len = saveRegexList.size(); index < len; index++){
    					String saveRegex = saveRegexList.get(index);
    					if(url.matches(saveRegex)){
		    				HashMap<String, Object> pageMap = new HashMap<String, Object>();
		    	    		pageMap.put("url", url);
		    	      	    pageMap.put("url_md5", url_md5);
		    	      	    pageMap.put("content", document.html());
		    	      	    pageMap.put("create_time", create_time);
		    	      	    spiderPageDB.insertSpiderPageDB(pageMap);
    					}
    				}
    	      	    
    			} else {
    				/*
    				 * 将状态码不是200的加入失败队列中
    				 * */
    				HashMap<String, Object> defeatURLMap = new HashMap<String, Object>();
    				defeatURLMap.put("url_md5", url_md5);
    				defeatURLMap.put("url", url);
    				defeatURLMap.put("status", status);
    				defeatURLMap.put("create_time", create_time);
    				spiderDefeatURLDB.insertSpiderDefeatURLDB(defeatURLMap);
    			}
    		} else {
    			/*
    			 * 将访问出现异常的全部存储到失败的库中，并将状态码标识为503
    			 * */
    			HashMap<String, Object> defeatURLMap = new HashMap<String, Object>();
				defeatURLMap.put("url_md5", url_md5);
				defeatURLMap.put("url", url);
				defeatURLMap.put("status", 503);
				defeatURLMap.put("create_time", create_time);
				spiderDefeatURLDB.insertSpiderDefeatURLDB(defeatURLMap);
    		}
    		queueLen = URLQueue.list.size();
    		HashMap<String, Object> spiderURLMap = new HashMap<String, Object>();
    		spiderURLMap.put("url_md5", url_md5);
    		spiderURLMap.put("mark", 1);
    		spiderPageDB.updateSpiderPageDB(spiderURLMap);
		}
	}

}
