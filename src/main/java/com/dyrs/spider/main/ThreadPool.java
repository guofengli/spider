package com.dyrs.spider.main;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	 public static void main(String[] args) {
	        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(2);
	        exec.scheduleAtFixedRate(new Runnable() {//每隔一段时间就触发异常
	                      public void run() {
	                           //throw new RuntimeException();
	                           System.out.println("================");
	                      }
	                  }, 0, 1000, TimeUnit.MILLISECONDS);
	        exec.scheduleAtFixedRate(new Runnable() {//每隔一段时间打印系统时间，证明两者是互不影响的
	                      public void run() {
	                           System.out.println(System.nanoTime());
	                      }
	                  }, 0, 1000, TimeUnit.MILLISECONDS);
	    }
}
