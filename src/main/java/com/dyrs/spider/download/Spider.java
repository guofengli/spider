package com.dyrs.spider.download;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
/*
 * ������
 * 
 */
public class Spider {
	Logger logger = Logger.getLogger(this.getClass());
	public Response downloadPage(String url, int timeout, HashMap<String, String> map, Method method, String userAgent){
		Response response = null;
		try{
			System.out.println(url + "---------");
			response = Jsoup.connect(url).userAgent(userAgent).timeout(timeout).cookies(map).method(Method.GET).ignoreContentType(true).execute();
			this.logger.info("��ȡURL��" + url + " ״̬�룺" + response.statusCode());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.logger.info("����������Ӧ url: " + url);
		}
		return response;
	}
	

}
