package com.dyrs.spider.dbs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.dyrs.spider.models.SpiderDefeatURLUser;
import com.dyrs.spider.models.SpiderPageUser;
import com.dyrs.spider.models.SpiderURLUser;

public class MyPipeline implements SpiderURLDB, SpiderPageDB, SpiderDefeatURLDB {
	Logger logger = Logger.getLogger(this.getClass());
	public boolean updateSpiderURLS(String url, boolean mark) {
		// TODO Auto-generated method stub

		return false;
	}

	public List<SpiderURLUser> selectSpiderURLs(int start, int num) {
		List<SpiderURLUser> urlList = SpiderURLUser.me
				.find("select * from spider_url  where mark = 0 LIMIT " + start + ", " + num);
		return urlList;
	}

	public boolean insertSpiderURL(String url) {
		// TODO Auto-generated method stub
		Date date = new Date();
		String create_time = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		create_time = df.format(date);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
  	    String url_md5 =DigestUtils.md5Hex(map.get("url").toString());
  	    map.put("url_md5", url_md5);
		map.put("create_time", create_time);
		Object selectURL = map.get("url_md5");
		if (selectURL != null) {
			SpiderURLUser user = SpiderURLUser.me.findById(selectURL);
			if (user != null) {
				new SpiderURLUser().setAttrs(map).update();
				logger.info(selectURL + "----------->update success");
			} else {
				new SpiderURLUser().setAttrs(map).save();
				logger.info(selectURL + "----------->save success");
			}
		}
		return false;
	}

	public boolean insertSpiderURLs(HashSet<String> linkSet) {
		// TODO Auto-generated method stub
		Iterator<String> iter = linkSet.iterator();
		Date date = new Date();
		String create_time = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		create_time = df.format(date);
		while (iter.hasNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("url", iter.next());
	  	    String url_md5=DigestUtils.md5Hex(map.get("url").toString());
	  	    map.put("url_md5", url_md5);
			map.put("create_time", create_time);
			Object url = map.get("url_md5");
			if (url != null) {
				SpiderURLUser user = SpiderURLUser.me.findById(url);
				if (user != null) {
//					new SpiderURLUser().setAttrs(map).update();
//					logger.info(url + "----------->update success");
				} else {
					new SpiderURLUser().setAttrs(map).save();
					logger.info(url + "----------->save success");
				}
			}
		}

		return false;
	}

	public boolean insertSpiderPageDB(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		Object url_md5 = map.get("url_md5");
		boolean result = false;
		if (url_md5 != null) {
			SpiderPageUser user = SpiderPageUser.me.findById(url_md5);
			if (user != null) {
				result = true;
				logger.info(map.get("url") + "页面内容已经存在!");
			} else {
				result = new SpiderPageUser().setAttrs(map).save();
				logger.info(map.get("url") + "页面已经入库!");
			}
		}
		return result;
	}

	public List<SpiderPageUser> selectSpiderPageDBs(String site) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public List<SpiderPageUser> selectSpiderPageDBs(String regex, String site) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SpiderPageUser> selectSpiderPageDBs(int start, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	public SpiderPageUser selectSpiderPageDB(String url_md5) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 *默认查询库里所有页面
	 */
	public List<SpiderPageUser> selectSpiderPageDBs() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean insertSpiderDefeatURLDB(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		Object url_md5 = map.get("url_md5");
		boolean result = false;
		if (url_md5 != null) {
			SpiderDefeatURLUser user = SpiderDefeatURLUser.me.findById(url_md5);
			if (user != null) {
				result = true;
				logger.info(map.get("url") + "----------失败队列已经存在-------------");
			} else {
				result = new SpiderDefeatURLUser().setAttrs(map).save();
				logger.info(map.get("url") + "----------入库完成---------");
			}
		}
		return result;
	}

	public boolean updateSpiderPageDB(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		Object url_md5 = map.get("url_md5");
		boolean result = false;
		if (url_md5 != null) {
			SpiderURLUser user = SpiderURLUser.me.findById(url_md5);
			if (user != null) {
				result = new SpiderURLUser().setAttrs(map).update();
				logger.info(map.get("url_md5") + "----------队列状态已经更新-------------");
			}
		}
		return result;
	}

}
