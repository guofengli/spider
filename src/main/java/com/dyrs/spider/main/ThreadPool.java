package com.dyrs.spider.main;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	 public static void main(String[] args) {
	        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(2);
	        exec.scheduleAtFixedRate(new Runnable() {//ÿ��һ��ʱ��ʹ����쳣
	                      public void run() {
	                           //throw new RuntimeException();
	                           System.out.println("================");
	                      }
	                  }, 0, 1000, TimeUnit.MILLISECONDS);
	        exec.scheduleAtFixedRate(new Runnable() {//ÿ��һ��ʱ���ӡϵͳʱ�䣬֤�������ǻ���Ӱ���
	                      public void run() {
	                           System.out.println(System.nanoTime());
	                      }
	                  }, 0, 1000, TimeUnit.MILLISECONDS);
	    }
}
