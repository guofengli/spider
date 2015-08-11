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
			logger.info("��ʼ�����ݿ��ж�ȡURL");
			SpiderURLDB spiderURLDB = new MyPipeline();
			List<SpiderURLUser> urlList = spiderURLDB.selectSpiderURLs(URLQueue.start, URLQueue.num);
	        int len = urlList.size();
	        for(int index = 0; index < len; index++){
	        	String url = urlList.get(index).getStr("url");
	        	if(url == null || url.equals("")){
	        		continue;
	        	} else{
	        		URLQueue.list.add(url);
	        	}
	        }
	        URLQueue.start += len < URLQueue.num ? len : URLQueue.num;
	        this.logger.info("��ȡURL���!");
		}
	}
}
