package com.sdi.monitoring.model.oracle.service;

import java.util.List;

import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;
import com.sdi.monitoring.model.oracle.dto.TimeAndCpuDTO;

public interface OracleDataService {
	public List<RealTimeMonitoringDTO> findDataForRealTime();
	
	public List<TimeAndCpuDTO> findTimeAndCpuDTO(String startDate, String endDate);
	
	public RealTimeMonitoringDTO findDataByTime(String date);
}
