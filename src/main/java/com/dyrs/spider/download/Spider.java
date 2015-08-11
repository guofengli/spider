package com.dyrs.spider.download;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
/*
 * 爬虫类
 * 
 */
public class Spider {
	Logger logger = Logger.getLogger(this.getClass());
	public Response downloadPage(String url, int timeout, HashMap<String, String> map, Method method, String userAgent){
		Response response = null;
		try{
			System.out.println(url + "---------");
			response = Jsoup.connect(url).userAgent(userAgent).timeout(timeout).cookies(map).method(Method.GET).ignoreContentType(true).execute();
			this.logger.info("获取URL：" + url + " 状态码：" + response.statusCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.logger.info("服务器无响应 url: " + url);
		}
		return response;
	}
	

}
