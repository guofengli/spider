package com.dyrs.spider.dbs;

import java.util.HashSet;
import java.util.List;

import com.dyrs.spider.models.SpiderURLUser;
/*
 * �Ľӿ���Ҫ��������URL�б�
 * 
 */
/*
 * �Ľӿ���Ҫ��������URL�б�
 * 
 */
public interface SpiderURLDB {
	public boolean updateSpiderURLS(String url, boolean mark);

	public List<SpiderURLUser> selectSpiderURLs(int start, int num);

	public boolean insertSpiderURL(String url);
	
	public boolean insertSpiderURLs(HashSet<String> linkSet);
}
