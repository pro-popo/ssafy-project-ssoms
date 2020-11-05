package com.sdi.monitoring.model.oracle.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.sdi.monitoring.model.oracle.entity.RealTimeMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.SchemaInfo;
import com.sdi.monitoring.model.oracle.repository.OracleRepoImpl;
import com.sdi.monitoring.model.oracle.repository.RealTimeMonitoringMongoRepo;
import com.sdi.monitoring.util.JsonParser;
import com.sdi.monitoring.util.Scheduler;

@Service
public class OracleSchedulingServiceImpl implements OracleSchedulingService{
	
	@Autowired
    Scheduler scheduler;
	
	@Autowired
	private OracleRepoImpl oracleRepoImpl;
	
	@Autowired
	private RealTimeMonitoringMongoRepo rtmRepo;
	
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
		if(scheduler != null)
			scheduler.stopScheduler();
	}
	
	@Override
	public void sampleMethod() {
		List<String> schemaList = JsonParser.getSchemaInfo();
		StopWatch stopWatch = new StopWatch();
	    stopWatch.start();
	    RealTimeMonitoringEntity rtmEntity = new RealTimeMonitoringEntity();
	    Date time = new Date();
	    
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    rtmEntity.setTime(format.format(time));
	    
		System.out.println("========== Oracle 전체 상태 ==========");
		rtmEntity.setOracleStatus(oracleRepoImpl.findOracleStastics());
		
		System.out.println("========== 전체 스키마 정보 ==========");
		rtmEntity.setAllSchemaStastics(oracleRepoImpl.findAllSchemaStastics(schemaList));
		
		System.out.println("========== cpu 기준 전체 스키마 top query ==========");
		rtmEntity.setAllSchemaQueryInfo(oracleRepoImpl.findAllSchemaQueryInfo(schemaList));
		
		List<SchemaInfo> schemas = new ArrayList<SchemaInfo>();
		for(String schemaName : schemaList) {
			SchemaInfo temp = new SchemaInfo();
			System.out.println("========== " + schemaName + ":: cpu 대비 스키마별 top query ==========");
			temp.setCpuUsed(oracleRepoImpl.findCpuUsedBySchema(schemaName));
			System.out.println("========== " + schemaName + ":: 실행시간 대비 스키마별 top query ==========");
			temp.setElapsedTime(oracleRepoImpl.findElapsedTimeBySchema(schemaName));
			System.out.println("========== " + schemaName + ":: 리소스 대비 스키마별 top query ==========");
			temp.setBufferGets(oracleRepoImpl.findBufferGetsBySchema(schemaName));
			schemas.add(temp);
		}
		rtmEntity.setSchemas(schemas);
        messagingTemplate.convertAndSend("/sendData/schedulerM", rtmEntity);
        rtmRepo.save(rtmEntity);
        stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeSeconds());
	}
}
