package com.sdi.monitoring.model.oracle.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;
import com.sdi.monitoring.model.oracle.dto.TimeAndCpuDTO;
import com.sdi.monitoring.model.oracle.entity.OneDayMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.OneHourMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.RealTimeMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.SixHoursMonitoringEntity;
import com.sdi.monitoring.model.oracle.repository.OneDayMonitoringMongoRepo;
import com.sdi.monitoring.model.oracle.repository.OneHourMonitoringMongoRepo;
import com.sdi.monitoring.model.oracle.repository.RealTimeMonitoringMongoRepo;
import com.sdi.monitoring.model.oracle.repository.SixHoursMonitoringMongoRepo;
import com.sdi.monitoring.util.Mapper;

@Service
public class OracleDataServiceImpl implements OracleDataService {

	@Autowired
	private RealTimeMonitoringMongoRepo realTimeMonitoringMongoRepo;
	
	@Autowired
	private OneHourMonitoringMongoRepo oneHourMonitoringMongoRepo;
	
	@Autowired
	private SixHoursMonitoringMongoRepo SixHoursMonitoringMongoRepo;
	
	@Autowired
	private OneDayMonitoringMongoRepo oneDayMonitoringMongoRepo;

	@Autowired
	private Mapper mapper;

	@Override
	public List<RealTimeMonitoringDTO> findDataForRealTime() {
		/*
		 * logic - 차트 구성을 위해 과거 12시간만큼의 데이터를 보낸다.
		 */
		Page<RealTimeMonitoringEntity> realTimeMonitoringEntityList = realTimeMonitoringMongoRepo
				.findAll(PageRequest.of(0, 12, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "time")));
		return realTimeMontoringEntityListToDTOList(realTimeMonitoringEntityList);
	}

	@Override
	public List<TimeAndCpuDTO> findTimeAndCpuDTO(String startDate, String endDate) {
		/*
		 * 구간별로 데이터를 나눠서 보낸다. 
		 * typeNum : 
		 * 	0 : 1일 
		 * 	1 : 2주 
		 * 	2 : 한달 
		 * 	3 : 6달
		 */
		return calculatePeriod(startDate, endDate);
	}

	@Override
	public RealTimeMonitoringDTO findDataByTime(String date) {
		Optional<RealTimeMonitoringEntity> optional = realTimeMonitoringMongoRepo.findByTime(date);
		if (optional.isPresent()) {
			return realTimeMontoringEntityToDTO(optional.get());
		}

		return null;
	}

	private List<RealTimeMonitoringDTO> realTimeMontoringEntityListToDTOList(Page<RealTimeMonitoringEntity> realTimeMonitoringEntityList) {
		List<RealTimeMonitoringDTO> realTimeMonitoringDTOList = new ArrayList<>();

		for (RealTimeMonitoringEntity realTimeMonitoringEntity : realTimeMonitoringEntityList) {
			RealTimeMonitoringDTO realTimeMonitoringDTO = mapper.convertToDTO(realTimeMonitoringEntity,
					RealTimeMonitoringDTO.class);
			realTimeMonitoringDTOList.add(realTimeMonitoringDTO);
		}

		Collections.sort(realTimeMonitoringDTOList);
		return realTimeMonitoringDTOList;
	}

	private List<TimeAndCpuDTO> realTimeMontoringEntityListToTimeAndCpuDTO(List<RealTimeMonitoringEntity> realTimeMonitoringEntityList) {
		List<TimeAndCpuDTO> timeAndCpuDTOList = new ArrayList<>();
		for (RealTimeMonitoringEntity realTimeMonitoringEntity : realTimeMonitoringEntityList) {
			TimeAndCpuDTO timeAndCpuDTO = new TimeAndCpuDTO(realTimeMonitoringEntity.getTime(),
					realTimeMonitoringEntity.getOracleStatus().getDatabaseCpuTimeRatio());
			timeAndCpuDTOList.add(timeAndCpuDTO);
		}
		return timeAndCpuDTOList;
	}
	
	private List<TimeAndCpuDTO> oneHourMontoringEntityListToTimeAndCpuDTO(List<OneHourMonitoringEntity> oneHourMonitoringEntityList) {
		List<TimeAndCpuDTO> timeAndCpuDTOList = new ArrayList<>();
		for (OneHourMonitoringEntity oneHourMonitoringEntity : oneHourMonitoringEntityList) {
			TimeAndCpuDTO timeAndCpuDTO = new TimeAndCpuDTO(oneHourMonitoringEntity.getTime(),
					oneHourMonitoringEntity.getOracleStatus().getDatabaseCpuTimeRatio());
			timeAndCpuDTOList.add(timeAndCpuDTO);
		}
		return timeAndCpuDTOList;
	}
	
	private List<TimeAndCpuDTO> sixHoursMontoringEntityListToTimeAndCpuDTO(List<SixHoursMonitoringEntity> sixHoursMonitoringEntityList) {
		List<TimeAndCpuDTO> timeAndCpuDTOList = new ArrayList<>();
		for (SixHoursMonitoringEntity sixHoursMonitoringEntity : sixHoursMonitoringEntityList) {
			TimeAndCpuDTO timeAndCpuDTO = new TimeAndCpuDTO(sixHoursMonitoringEntity.getTime(),
					sixHoursMonitoringEntity.getOracleStatus().getDatabaseCpuTimeRatio());
			timeAndCpuDTOList.add(timeAndCpuDTO);
		}
		return timeAndCpuDTOList;
	}
	
	private List<TimeAndCpuDTO> oneDayMontoringEntityListToTimeAndCpuDTO(List<OneDayMonitoringEntity> oneDayMonitoringEntityList) {
		List<TimeAndCpuDTO> timeAndCpuDTOList = new ArrayList<>();
		for (OneDayMonitoringEntity oneDayMonitoringEntity : oneDayMonitoringEntityList) {
			TimeAndCpuDTO timeAndCpuDTO = new TimeAndCpuDTO(oneDayMonitoringEntity.getTime(),
					oneDayMonitoringEntity.getOracleStatus().getDatabaseCpuTimeRatio());
			timeAndCpuDTOList.add(timeAndCpuDTO);
		}
		return timeAndCpuDTOList;
	}

	private RealTimeMonitoringDTO realTimeMontoringEntityToDTO(RealTimeMonitoringEntity realTimeMonitoringEntity) {
		return mapper.convertToDTO(realTimeMonitoringEntity, RealTimeMonitoringDTO.class);
	}

	private List<TimeAndCpuDTO> calculatePeriod(String startDate, String endDate) {
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date start = transFormat.parse(startDate);
			Date end = transFormat.parse(endDate);
			Calendar startCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			startCal.setTime(start);
			endCal.setTime(end);
			if(startCal.compareTo(endCal) == 0) {
				endCal.add(Calendar.DATE, 1);
				return realTimeMontoringEntityListToTimeAndCpuDTO(realTimeMonitoringMongoRepo.findByTimeBetween(startDate, transFormat.format(endCal.getTime()))); // 1일
			}
			startCal.add(Calendar.DATE, 14);
			if (startCal.compareTo(endCal) == 1) {
				endCal.add(Calendar.DATE, 1);
				return oneHourMontoringEntityListToTimeAndCpuDTO(oneHourMonitoringMongoRepo.findByTimeBetween(startDate, transFormat.format(endCal.getTime())));
			}
			startCal.add(Calendar.DATE, 17);
			if (startCal.compareTo(endCal) == 1) {
				endCal.add(Calendar.DATE, 1);
				return sixHoursMontoringEntityListToTimeAndCpuDTO(SixHoursMonitoringMongoRepo.findByTimeBetween(startDate, transFormat.format(endCal.getTime())));
			}
			startCal.add(Calendar.MONTH, 5);
			if (startCal.compareTo(endCal) == 1) {
				endCal.add(Calendar.DATE, 1);
				return oneDayMontoringEntityListToTimeAndCpuDTO(oneDayMonitoringMongoRepo.findByTimeBetween(startDate, transFormat.format(endCal.getTime())));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
