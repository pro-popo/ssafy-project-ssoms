package com.sdi.monitoring.model.oracle.service;

import java.util.List;

import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;

public interface OracleDataService {
	public List<RealTimeMonitoringDTO> findDataForRearTime();
}
