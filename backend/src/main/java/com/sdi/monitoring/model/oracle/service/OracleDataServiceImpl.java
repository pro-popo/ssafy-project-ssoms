package com.sdi.monitoring.model.oracle.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;
import com.sdi.monitoring.model.oracle.dto.TimeAndCpuDTO;
import com.sdi.monitoring.model.oracle.entity.OneHourMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.RealTimeMonitoringEntity;
import com.sdi.monitoring.model.oracle.repository.OneHourMonitoringMongoRepo;
import com.sdi.monitoring.model.oracle.repository.RealTimeMonitoringMongoRepo;
import com.sdi.monitoring.util.Mapper;

@Service
public class OracleDataServiceImpl implements OracleDataService {

	@Autowired
	private RealTimeMonitoringMongoRepo realTimeMonitoringMongoRepo;
	
	@Autowired
	private OneHourMonitoringMongoRepo oneHourMonitoringMongoRepo;
	
	@Autowired
	private Mapper mapper;

	@Override
	public List<RealTimeMonitoringDTO> findDataForRealTime() {
		/*
		 * logic - 차트 구성을 위해 과거 12시간만큼의 데이터를 보낸다.
		 */
		Page<RealTimeMonitoringEntity> realTimeMonitoringEntityList = realTimeMonitoringMongoRepo.findAll(PageRequest.of(0, 12, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "time")));
		return realTimeMontoringEntityListToDTOList(realTimeMonitoringEntityList);
	}
	
	@Override
	public List<TimeAndCpuDTO> findTimeAndCpuDTO(String startDate, String endDate) {
		/*
		 *  구간별로 데이터를 나눠서 보낸다.
		 */
		List<RealTimeMonitoringEntity> realTimeMonitoringEntityList = realTimeMonitoringMongoRepo.findByTimeBetween(startDate, endDate);
		return realTimeMontoringEntityListToTimeAndCpuDTO(realTimeMonitoringEntityList);
	}
	
	@Override
	public RealTimeMonitoringDTO findDataByTime(String date) {
		Optional<RealTimeMonitoringEntity> optional = realTimeMonitoringMongoRepo.findById(date);
		if(optional.isPresent()) {
			return realTimeMontoringEntityToDTO(optional.get());
		}
		
		return null;
	}
	
	private List<RealTimeMonitoringDTO> realTimeMontoringEntityListToDTOList(Page<RealTimeMonitoringEntity> realTimeMonitoringEntityList){
		List<RealTimeMonitoringDTO> realTimeMonitoringDTOList = new ArrayList<>();
		
		for(RealTimeMonitoringEntity realTimeMonitoringEntity : realTimeMonitoringEntityList) {
			RealTimeMonitoringDTO realTimeMonitoringDTO = mapper.convertToDTO(realTimeMonitoringEntity, RealTimeMonitoringDTO.class);
			realTimeMonitoringDTOList.add(realTimeMonitoringDTO);
		}
		
		Collections.sort(realTimeMonitoringDTOList);
		return realTimeMonitoringDTOList;
	}
	
	private List<TimeAndCpuDTO> realTimeMontoringEntityListToTimeAndCpuDTO(List<RealTimeMonitoringEntity> realTimeMonitoringEntityList){
		List<TimeAndCpuDTO> timeAndCpuDTOList = new ArrayList<>();
		int idx = 0;
		for(RealTimeMonitoringEntity realTimeMonitoringEntity : realTimeMonitoringEntityList) {
			TimeAndCpuDTO timeAndCpuDTO = new TimeAndCpuDTO(realTimeMonitoringEntity.getTime(), realTimeMonitoringEntity.getOracleStatus().getDatabaseCpuTimeRatio());
			timeAndCpuDTOList.add(timeAndCpuDTO);
			idx++;
		}
		System.out.println(idx);
		return timeAndCpuDTOList;
	}
	
	private RealTimeMonitoringDTO realTimeMontoringEntityToDTO(RealTimeMonitoringEntity realTimeMonitoringEntity){
		return mapper.convertToDTO(realTimeMonitoringEntity, RealTimeMonitoringDTO.class);
	}
}
