package com.sdi.monitoring.model.oracle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		System.out.println("========== Oracle 전체 상태 ==========");
		oracleRepoImpl.findOracleStastics();
		System.out.println("========== 전체 스키마 정보 ==========");
		oracleRepoImpl.findAllSchemaStastics(schemaList);
		System.out.println("========== cpu 기준 전체 스키마 top query ==========");
		oracleRepoImpl.findAllScehmaQueryInfo(schemaList);
		for(String schemaName : schemaList) {
			System.out.println("========== " + schemaName + ":: cpu 대비 스키마별 top query ==========");
			oracleRepoImpl.findCpuUsedBySchema(schemaName);
			System.out.println("========== " + schemaName + ":: 실행시간 대비 스키마별 top query ==========");
			oracleRepoImpl.findElapsedTimeBySchema(schemaName);
			System.out.println("========== " + schemaName + ":: 리소스 대비 스키마별 top query ==========");
			oracleRepoImpl.findBufferGetsBySchema(schemaName);
		}
		stopWatch.stop();
		System.out.println(stopWatch.getTotalTimeSeconds());
	}
}
