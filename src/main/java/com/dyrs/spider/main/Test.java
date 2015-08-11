package com.dyrs.spider.main;

import com.dyrs.spider.dbs.MyPipeline;
import com.dyrs.spider.dbs.SpiderURLDB;
import com.dyrs.spider.models.SpiderDefeatURLUser;
import com.dyrs.spider.models.SpiderPageUser;
import com.dyrs.spider.models.SpiderURLUser;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://172.16.254.99:3306/netspider?characterEncoding=utf-8&useUnicode=true&", "spider", "spider@&*()");
        c3p0Plugin.start();
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.addMapping("spider_url","url_md5", SpiderURLUser.class);
        activeRecordPlugin.addMapping("spider_defeat_url","url_md5", SpiderDefeatURLUser.class);
        activeRecordPlugin.addMapping("spider_page","url_md5", SpiderPageUser.class);
        activeRecordPlugin.start();
		
		SpiderURLDB spiderURLDB = new MyPipeline();
		spiderURLDB.insertSpiderURL("http://bbs.wacai.com/portal.php");
	}

}
