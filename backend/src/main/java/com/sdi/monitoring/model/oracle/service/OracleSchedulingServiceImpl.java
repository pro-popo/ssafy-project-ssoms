package com.sdi.monitoring.model.oracle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.google.gson.Gson;
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
	    String msg = "{";
	    Gson gson = new Gson();
		System.out.println("========== Oracle 전체 상태 ==========");
		msg += "\"OracleStastics\":" + gson.toJson(oracleRepoImpl.findOracleStastics())+ ",";
		
		System.out.println("========== 전체 스키마 정보 ==========");
		msg += "\"AllSchemaStastics\":" + gson.toJson(oracleRepoImpl.findAllSchemaStastics(schemaList))+ ",";
		
		System.out.println("========== cpu 기준 전체 스키마 top query ==========");
		msg += "\"AllScehmaQueryInfo\":" + gson.toJson(oracleRepoImpl.findAllScehmaQueryInfo(schemaList))+ ",";
		
		
		for(String schemaName : schemaList) {
			msg += "\""+schemaName+"\":{";
			System.out.println("========== " + schemaName + ":: cpu 대비 스키마별 top query ==========");
			msg += "\"CpuUsedBySchema\":" + gson.toJson(oracleRepoImpl.findCpuUsedBySchema(schemaName)) + ",";
			
			System.out.println("========== " + schemaName + ":: 실행시간 대비 스키마별 top query ==========");
			msg += "\"ElapsedTimeBySchema\":" + gson.toJson(oracleRepoImpl.findElapsedTimeBySchema(schemaName)) + ",";
			System.out.println("========== " + schemaName + ":: 리소스 대비 스키마별 top query ==========");
			msg += "\"BufferGetsBySchema\":" + gson.toJson(oracleRepoImpl.findBufferGetsBySchema(schemaName)) + "";
			msg+= "},";
		}
		msg +="}";
		stopWatch.stop();
		messagingTemplate.setMessageConverter(new StringMessageConverter());
        messagingTemplate.convertAndSend("/sendData/schedulerM", msg);
		System.out.println(stopWatch.getTotalTimeSeconds());
	}

}
