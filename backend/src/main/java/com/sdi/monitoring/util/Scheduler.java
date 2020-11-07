package com.sdi.monitoring.util;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import com.sdi.monitoring.model.oracle.service.OracleSchedulingService;

@Component
public class Scheduler {
	private ThreadPoolTaskScheduler scheduler;
	 
	@Autowired
	private OracleSchedulingService oss;
	
    public void stopScheduler() {
    	if(scheduler != null) {
    		scheduler.shutdown();
    	}
    }
 
    public void startScheduler() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        // 스케쥴러가 시작되는 부분 
        scheduler.schedule(getRunnable(), getTrigger());
    }
 
    private Runnable getRunnable(){
        return () -> {
        	oss.sampleMethod();
        };
    }
 
    private Trigger getTrigger() {
        // 작업 주기 설정 
//    	return (Trigger) new FixedDelay(5000);
        return new PeriodicTrigger(1, TimeUnit.MINUTES);
    }
}
