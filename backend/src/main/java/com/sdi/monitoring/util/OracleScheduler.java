package com.sdi.monitoring.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.sdi.monitoring.model.oracle.service.OracleSchedulingService;

@Component
public class OracleScheduler {
	private ThreadPoolTaskScheduler scheduler;
	 
	@Autowired
	private OracleSchedulingService oss;
	
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
    		scheduler.schedule(getRunnable(), getTrigger());
    		return true;
    	}
    	return false;
    }
 
    private Runnable getRunnable(){
        return () -> {
        	oss.oracleGetDataScheduler();
        };
    }
 
    private Trigger getTrigger() {
        // 작업 주기 설정 
        return new PeriodicTrigger(1, TimeUnit.MINUTES);
    }
}
