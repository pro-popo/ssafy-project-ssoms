package com.sdi.monitoring.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.sdi.monitoring.model.mongo.service.MongoSchedulingService;

@Component
public class MongoScheduler {
	private ThreadPoolTaskScheduler scheduler;
	 
	@Autowired
	private MongoSchedulingService mss;
	
	public boolean hasScheduler() {
		if(scheduler != null) {
			return true;
		}
		return false;
	}
	
    public boolean stopScheduler() {
    	if(scheduler == null) {
    		return false;
    	}
    	scheduler.shutdown();
    	scheduler = null;
    	return true;
    }
 
    public boolean startScheduler() {
    	if(scheduler == null) {
    		scheduler = new ThreadPoolTaskScheduler();
    		scheduler.initialize();
    		// 스케쥴러가 시작되는 부분 
    		scheduler.schedule(getRunnable(), getTrigger());
    		return true;
    	}
    	return false;
    }
 
    private Runnable getRunnable(){
        return () -> {
        	mss.mongoRemoveDataScheduler();
        };
    }
 
    private Trigger getTrigger() {
        // 작업 주기 설정 
    	return new CronTrigger("0 0 1 * * ?");
    }
}
