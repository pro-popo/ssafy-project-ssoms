package com.sdi.monitoring.model.oracle.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.sdi.monitoring.model.oracle.repository.OracleRepoImpl;
import com.sdi.monitoring.util.JsonParser;
import com.sdi.monitoring.util.Scheduler;

@Service
public class OracleSchedulingServiceImpl implements OracleSchedulingService{
	
	@Autowired
    Scheduler scheduler;
	
	@Autowired
	private OracleRepoImpl oracleRepoImpl;
	
	private SimpMessagingTemplate messagingTemplate; 

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
	
	@Override
	public void start() {
		scheduler.startScheduler();
	}
	
	@Override
	public void stop() {
		scheduler.stopScheduler();
	}
	
	@Override
	public void sampleMethod() {
		List<String> schemaList = JsonParser.getSchemaInfo();
		StopWatch stopWatch = new StopWatch();
	    stopWatch.start();
	    Map<String, Object> map = new HashMap<>();
	    
		System.out.println("========== Oracle 전체 상태 ==========");
		map.put("OracleStastics", oracleRepoImpl.findOracleStastics());
		
		System.out.println("========== 전체 스키마 정보 ==========");
		map.put("AllSchemaStastics", oracleRepoImpl.findAllSchemaStastics(schemaList));
		
		System.out.println("========== cpu 기준 전체 스키마 top query ==========");
		map.put("AllScehmaQueryInfo", oracleRepoImpl.findAllScehmaQueryInfo(schemaList));
		
		
		for(String schemaName : schemaList) {
			Map<String, Object> temp = new HashMap<>();
			System.out.println("========== " + schemaName + ":: cpu 대비 스키마별 top query ==========");
			temp.put("CpuUsedBySchema", oracleRepoImpl.findCpuUsedBySchema(schemaName));
			
			System.out.println("========== " + schemaName + ":: 실행시간 대비 스키마별 top query ==========");
			temp.put("ElapsedTimeBySchema", oracleRepoImpl.findElapsedTimeBySchema(schemaName));
			System.out.println("========== " + schemaName + ":: 리소스 대비 스키마별 top query ==========");
			temp.put("BufferGetsBySchema", oracleRepoImpl.findBufferGetsBySchema(schemaName));
			map.put(schemaName, temp);
		}
		
        messagingTemplate.convertAndSend("/sendData/schedulerM", map);
        stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeSeconds());
	}

}
