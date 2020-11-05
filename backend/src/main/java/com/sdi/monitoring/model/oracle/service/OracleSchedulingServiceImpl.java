package com.sdi.monitoring.model.oracle.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaInfoDTO;
import com.sdi.monitoring.model.oracle.entity.RealTimeMonitoringEntity;
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
		scheduler.stopScheduler();
	}
	
	@Override
	public void sampleMethod() {
		List<String> schemaList = JsonParser.getSchemaInfo();
		StopWatch stopWatch = new StopWatch();
	    stopWatch.start();
	    RealTimeMonitoringDTO realTimeMonitoringDTO = new RealTimeMonitoringDTO();
	    Date time = new Date();
	    
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    realTimeMonitoringDTO.setTime(format.format(time));
	    
		System.out.println("========== Oracle 전체 상태 ==========");
		realTimeMonitoringDTO.setOracleStatus(oracleRepoImpl.findOracleStastics());
		
		System.out.println("========== 전체 스키마 정보 ==========");
		realTimeMonitoringDTO.setAllSchemaStastics(oracleRepoImpl.findAllSchemaStastics(schemaList));
		
		System.out.println("========== cpu 기준 전체 스키마 top query ==========");
		realTimeMonitoringDTO.setAllSchemaQueryInfo(oracleRepoImpl.findAllSchemaQueryInfo(schemaList));
		
		List<SchemaInfoDTO> schemaInfoDTOList = new ArrayList<SchemaInfoDTO>();
		for(String schemaName : schemaList) {
			SchemaInfoDTO schemaInfoDTO = new SchemaInfoDTO();
			System.out.println("========== " + schemaName + ":: cpu 대비 스키마별 top query ==========");
			schemaInfoDTO.setCpuUsed(oracleRepoImpl.findCpuUsedBySchema(schemaName));
			System.out.println("========== " + schemaName + ":: 실행시간 대비 스키마별 top query ==========");
			schemaInfoDTO.setElapsedTime(oracleRepoImpl.findElapsedTimeBySchema(schemaName));
			System.out.println("========== " + schemaName + ":: 리소스 대비 스키마별 top query ==========");
			schemaInfoDTO.setBufferGets(oracleRepoImpl.findBufferGetsBySchema(schemaName));
			schemaInfoDTOList.add(schemaInfoDTO);
		}
		realTimeMonitoringDTO.setSchemas(schemaInfoDTOList);
        messagingTemplate.convertAndSend("/sendData/schedulerM", realTimeMonitoringDTO);
        rtmRepo.save(realTimeMonitoringEntityBuilder(realTimeMonitoringDTO));
        stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeSeconds());
	}
	
	private RealTimeMonitoringEntity realTimeMonitoringEntityBuilder(RealTimeMonitoringDTO realTimeMonitoringDTO) {
		return RealTimeMonitoringEntity.builder()
				.time(realTimeMonitoringDTO.getTime())
				.oracleStatus(realTimeMonitoringDTO.getOracleStatus())
				.schemas(realTimeMonitoringDTO.getSchemas())
				.allSchemaStastics(realTimeMonitoringDTO.getAllSchemaStastics())
				.allSchemaQueryInfo(realTimeMonitoringDTO.getAllSchemaQueryInfo())
				.build();
	}
	
	
}
