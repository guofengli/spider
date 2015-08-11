package com.dyrs.spider.dbs;

import java.util.HashMap;
import java.util.List;

import com.dyrs.spider.models.SpiderPageUser;

public interface SpiderPageDB {
	public boolean insertSpiderPageDB(HashMap<String, Object> map);

	public List<SpiderPageUser> selectSpiderPageDBs();

	public List<SpiderPageUser> selectSpiderPageDBs(String site);

	public List<SpiderPageUser> selectSpiderPageDBs(String regex, String site);

	public List<SpiderPageUser> selectSpiderPageDBs(int start, int num);

	public SpiderPageUser selectSpiderPageDB(String url_md5);

	public boolean updateSpiderPageDB(HashMap<String, Object> map);
}
