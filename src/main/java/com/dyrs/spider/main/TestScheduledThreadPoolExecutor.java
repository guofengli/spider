package com.dyrs.spider.main;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.dyrs.spider.models.SpiderDefeatURLUser;
import com.dyrs.spider.models.SpiderPageUser;
import com.dyrs.spider.models.SpiderURLUser;
import com.dyrs.spider.runnables.DownloadPageRunnable;
import com.dyrs.spider.runnables.ReadURLRunnable;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

	public class TestScheduledThreadPoolExecutor {
	    public static void main(String[] args) {
	    	C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://172.16.254.99:3306/netspider?characterEncoding=utf-8&useUnicode=true&", "spider", "spider@&*()");
	        c3p0Plugin.start();
	        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
	        activeRecordPlugin.addMapping("spider_url","url_md5", SpiderURLUser.class);
	        activeRecordPlugin.addMapping("spider_defeat_url","url_md5", SpiderDefeatURLUser.class);
	        activeRecordPlugin.addMapping("spider_page","url_md5", SpiderPageUser.class);
	        activeRecordPlugin.start();
	    	
	    	
	        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(4);
	        exec.scheduleAtFixedRate(new DownloadPageRunnable(), 1000, 2000, TimeUnit.MILLISECONDS);
	        exec.scheduleAtFixedRate(new ReadURLRunnable(), 1000, 2000, TimeUnit.MILLISECONDS);
	        exec.scheduleAtFixedRate(new DownloadPageRunnable(), 1000, 2000, TimeUnit.MILLISECONDS);
	        exec.scheduleAtFixedRate(new DownloadPageRunnable(), 1000, 2000, TimeUnit.MILLISECONDS);
	    }
	}

