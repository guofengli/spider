package com.dyrs.spider.dbs;

import java.util.HashSet;
import java.util.List;

import com.dyrs.spider.models.SpiderURLUser;
/*
 * 改接口主要操作所有URL列表
 * 
 */
/*
 * 改接口主要操作所有URL列表
 * 
 */
public interface SpiderURLDB {
	public boolean updateSpiderURLS(String url, boolean mark);

	public List<SpiderURLUser> selectSpiderURLs(int start, int num);

	public boolean insertSpiderURL(String url);
	
	public boolean insertSpiderURLs(HashSet<String> linkSet);
}
