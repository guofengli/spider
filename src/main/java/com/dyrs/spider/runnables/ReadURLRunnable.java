package com.dyrs.spider.runnables;

import java.util.List;

import org.apache.log4j.Logger;

import com.dyrs.spider.dbs.MyPipeline;
import com.dyrs.spider.dbs.SpiderURLDB;
import com.dyrs.spider.models.SpiderURLUser;
import com.dyrs.spider.queue.URLQueue;


public class ReadURLRunnable implements Runnable{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	public void run() {
		// TODO Auto-generated method stub
		int quequeLen = URLQueue.list.size();
		if(quequeLen < 100){
			logger.info("开始从数据库中读取URL");
			SpiderURLDB spiderURLDB = new MyPipeline();
			/*
			 * @author:guofeng
			 * @date:2015-08-11
			 * 这里因为添加了 where mark= 0  所有应该是从0开始连续num条记录
			 * */
//			List<SpiderURLUser> urlList = spiderURLDB.selectSpiderURLs(URLQueue.start, URLQueue.num);
			List<SpiderURLUser> urlList = spiderURLDB.selectSpiderURLs(0, URLQueue.num);
	        int len = urlList.size();
	        for(int index = 0; index < len; index++){
	        	String url = urlList.get(index).getStr("url");
	        	if(url == null || url.equals("")){
	        		continue;
	        	} else{
	        		URLQueue.list.add(url);
	        	}
	        }
//	        URLQueue.start += len < URLQueue.num ? len : URLQueue.num;
	        this.logger.info("读取URL完毕!");
		}
	}
}
