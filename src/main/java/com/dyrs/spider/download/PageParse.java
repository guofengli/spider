package com.dyrs.spider.download;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParse {
//	static HashSet<String> regexSet = new HashSet<String>();
	static Logger logger = Logger.getLogger(PageParse.class);
	static List<String> list;
	static{
		try {
			String filePathTMP = PageParse.class.getResource("/").toString() + "conf/regex.txt";
			String filePath = filePathTMP.replace("file:/", "");
			System.out.println(filePath); 
			list = FileUtils.readLines(new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("正则表达式文件不存在!!");
//			e.printStackTrace();
		}
	}
	
	public HashSet<String> getAllURL(Document document){
		HashSet<String> linkSet = new HashSet<String>();
		Elements links = document.select("a[href]");
		for(Element element: links){
			String link = element.attr("abs:href");
			for(int i = 0, len = list.size(); i < len; i++){
				String regexURL = list.get(i);
				if(link != null && link.matches(regexURL)){
					linkSet.add(link);	
					break;
				}
			}
		}
		return linkSet;
	}
	
	
}
