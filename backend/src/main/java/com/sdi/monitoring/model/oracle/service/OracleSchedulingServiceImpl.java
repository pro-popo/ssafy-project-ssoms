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

import com.sdi.monitoring.model.oracle.dto.OracleStatusDTO;
import com.sdi.monitoring.model.oracle.dto.RealTimeMonitoringDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaInfoDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaQueryDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaStasticsDTO;
import com.sdi.monitoring.model.oracle.dto.UsedBySchemaDTO;
import com.sdi.monitoring.model.oracle.entity.OneDayMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.OneHourMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.OracleStatusEntity;
import com.sdi.monitoring.model.oracle.entity.OutlierDataEntity;
import com.sdi.monitoring.model.oracle.entity.RealTimeMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.SchemaInfoEntity;
import com.sdi.monitoring.model.oracle.entity.SchemaQueryEntity;
import com.sdi.monitoring.model.oracle.entity.SchemaStasticsEntity;
import com.sdi.monitoring.model.oracle.entity.SixHoursMonitoringEntity;
import com.sdi.monitoring.model.oracle.entity.UsedBySchemaEntity;
import com.sdi.monitoring.model.oracle.repository.OneDayMonitoringMongoRepo;
import com.sdi.monitoring.model.oracle.repository.OneHourMonitoringMongoRepo;
import com.sdi.monitoring.model.oracle.repository.OracleRepoImpl;
import com.sdi.monitoring.model.oracle.repository.OutlierDataMongoRepo;
import com.sdi.monitoring.model.oracle.repository.RealTimeMonitoringMongoRepo;
import com.sdi.monitoring.model.oracle.repository.SixHoursMonitoringMongoRepo;
import com.sdi.monitoring.util.JsonParser;
import com.sdi.monitoring.util.OracleScheduler;

@Service
public class OracleSchedulingServiceImpl implements OracleSchedulingService {
	private static int cnt = 0;
	private static int periodCnt = 0;
	@Autowired
	OracleScheduler oracleScheduler;

	@Autowired
	private OracleRepoImpl oracleRepoImpl;

	@Autowired
	private RealTimeMonitoringMongoRepo realTimeMonitoringMongoRepo;

	@Autowired
	private OneHourMonitoringMongoRepo oneHourMonitoringMongoRepo;

	@Autowired
	private SixHoursMonitoringMongoRepo sixHoursMonitoringMongoRepo;

	@Autowired
	private OneDayMonitoringMongoRepo oneDayMonitoringMongoRepo;

	@Autowired
	private OutlierDataMongoRepo outlierDataMongoRepo;

	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public boolean hasScheduler() {
		return oracleScheduler.hasScheduler();
	}

	@Override
	public boolean start() {
		cnt = 0;
		periodCnt = 0;
		return oracleScheduler.startScheduler();
	}

	@Override
	public boolean stop() {
		cnt = 0;
		periodCnt = 0;
		return oracleScheduler.stopScheduler();
	}

	@Override
	public void oracleGetDataScheduler() {
		System.out.println(new Date());
		cnt++;
		periodCnt++;
		Date time = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		System.out.println("========== Oracle 전체 상태 ==========");
		OracleStatusDTO oracleStatusDTO = oracleRepoImpl.findOracleStastics();
		Map<String, Object> map = new HashMap<>();
		boolean outlier = false;
		if (oracleStatusDTO.getDatabaseCpuTimeRatio() <= oracleStatusDTO.getDatabaseWaitTimeRatio()
				|| periodCnt % 5 == 0) {
			List<String> schemaList = JsonParser.getSchemaInfo();

			RealTimeMonitoringDTO realTimeMonitoringDTO = new RealTimeMonitoringDTO();

			realTimeMonitoringDTO.setTime(format.format(time));
			map.put("time", realTimeMonitoringDTO.getTime());

			realTimeMonitoringDTO.setOracleStatus(oracleStatusDTO);
			map.put("oracleStatus", realTimeMonitoringDTO.getOracleStatus());

			realTimeMonitoringDTO.setAllSchemaStastics(oracleRepoImpl.findAllSchemaStastics(schemaList));
			map.put("allSchemaStastics", realTimeMonitoringDTO.getAllSchemaStastics());

			realTimeMonitoringDTO.setAllSchemaQueryInfo(oracleRepoImpl.findAllSchemaQueryInfo(schemaList));
			map.put("allSchemaQueryInfo", realTimeMonitoringDTO.getAllSchemaQueryInfo());

			Map<String, Object> schemas = new HashMap<>();
			List<SchemaInfoDTO> schemaInfoDTOList = new ArrayList<SchemaInfoDTO>();
			for (String schemaName : schemaList) {
				SchemaInfoDTO schemaInfoDTO = new SchemaInfoDTO();
				schemaInfoDTO.setCpuUsed(oracleRepoImpl.findCpuUsedBySchema(schemaName));
				schemaInfoDTO.setElapsedTime(oracleRepoImpl.findElapsedTimeBySchema(schemaName));
				schemaInfoDTO.setBufferGets(oracleRepoImpl.findBufferGetsBySchema(schemaName));
				schemaInfoDTOList.add(schemaInfoDTO);
				schemas.put(schemaName, schemaInfoDTO);
			}

			realTimeMonitoringDTO.setSchemas(schemaInfoDTOList);
			map.put("schemas", schemas);

			RealTimeMonitoringEntity realTimeMonitoringEntity = realTimeMonitoringEntityBuilder(realTimeMonitoringDTO);

			realTimeMonitoringMongoRepo.insert(realTimeMonitoringEntity);
			if (cnt % (12) == 0 && periodCnt % 5 == 0) {
				oneHourMonitoringMongoRepo.insert(oneHourMonitoringEntityBuilder(realTimeMonitoringEntity));
				// 1시간 저장 logic
			}
			if (cnt % (12 * 6) == 0 && periodCnt % 5 == 0) {
				sixHoursMonitoringMongoRepo.insert(sixHoursMonitoringEntityBuilder(realTimeMonitoringEntity));
				// 6시간 저장 logic
			}
			if (cnt % (12 * 6 * 4) == 0 && periodCnt % 5 == 0) {
				cnt = 0;
				oneDayMonitoringMongoRepo.insert(oneDayMonitoringEntityBuilder(realTimeMonitoringEntity));
				// 1일 저장 logic
			}
			if(oracleStatusDTO.getDatabaseCpuTimeRatio() <= oracleStatusDTO.getDatabaseWaitTimeRatio()) {
				outlierDataMongoRepo.insert(
						outlierDataEntityBuilder(format.format(time), oracleStatusDTO.getDatabaseCpuTimeRatio()));
				outlier = true;
			}
			map.put("outlier", outlier);
			messagingTemplate.convertAndSend("/sendData/schedulerM", map);
		}
	}

	private UsedBySchemaEntity usedBySchemaBuilder(UsedBySchemaDTO usedBySchemaDTO) {
		return UsedBySchemaEntity.builder().sqlId(usedBySchemaDTO.getSqlId())
				.parsingSchemaName(usedBySchemaDTO.getParsingSchemaName()).module(usedBySchemaDTO.getModule())
				.lastActiveTime(usedBySchemaDTO.getLastActiveTime()).executions(usedBySchemaDTO.getExecutions())
				.cpuTimePerSec(usedBySchemaDTO.getCpuTimePerSec()).cpuTimeRatio(usedBySchemaDTO.getCpuTimeRatio())
				.elapsedTimePerSec(usedBySchemaDTO.getElapsedTimePerSec())
				.elapsedTimeRatio(usedBySchemaDTO.getElapsedTimeRatio()).bufferGets(usedBySchemaDTO.getBufferGets())
				.bufferGetsRatio(usedBySchemaDTO.getBufferGetsRatio()).sql(usedBySchemaDTO.getSql()).build();
	}

	private OracleStatusEntity oracleStatusBuilder(OracleStatusDTO oracleStatusDTO) {
		return OracleStatusEntity.builder().databaseCpuTimeRatio(oracleStatusDTO.getDatabaseCpuTimeRatio())
				.databaseWaitTimeRatio(oracleStatusDTO.getDatabaseWaitTimeRatio())
				.activeSerialSessions(oracleStatusDTO.getActiveSerialSessions())
				.dbBlockGetsPerSec(oracleStatusDTO.getDbBlockGetsPerSec())
				.logicalReadsPerSec(oracleStatusDTO.getLogicalReadsPerSec())
				.redoGeneratedPerSec(oracleStatusDTO.getRedoGeneratedPerSec())
				.executionsPerSec(oracleStatusDTO.getExecutionsPerSec())
				.totalParseCountPerSec(oracleStatusDTO.getTotalParseCountPerSec())
				.openCursorsPerSec(oracleStatusDTO.getOpenCursorsPerSec())
				.userCommitsPerSec(oracleStatusDTO.getUserCommitsPerSec())
				.physicalReadsPerSec(oracleStatusDTO.getPhysicalReadsPerSec())
				.physicalWritesPerSec(oracleStatusDTO.getPhysicalWritesPerSec())
				.responseTimePerTxn(oracleStatusDTO.getResponseTimePerTxn()).build();
	}

	private SchemaQueryEntity schemaQueryBuilder(SchemaQueryDTO schemaQueryDTO) {
		return SchemaQueryEntity.builder().sqlId(schemaQueryDTO.getSqlId())
				.parsingSchemaName(schemaQueryDTO.getParsingSchemaName()).sql(schemaQueryDTO.getSql())
				.executions(schemaQueryDTO.getExecutions()).bufferGets(schemaQueryDTO.getBufferGets())
				.diskReads(schemaQueryDTO.getDiskReads()).rowsProcessed(schemaQueryDTO.getRowsProcessed())
				.cpuTimeAvg(schemaQueryDTO.getCpuTimeAvg()).elapsedTimeAvg(schemaQueryDTO.getElapsedTimeAvg()).build();
	}

	private SchemaStasticsEntity schemaStasticsBuilder(SchemaStasticsDTO schemaStasticsDTO) {
		return SchemaStasticsEntity.builder().parsingSchemaName(schemaStasticsDTO.getParsingSchemaName())
				.sqlCnt(schemaStasticsDTO.getSqlCnt()).executions(schemaStasticsDTO.getExecutions())
				.bufferGetsAvg(schemaStasticsDTO.getBufferGetsAvg()).diskReadsAvg(schemaStasticsDTO.getDiskReadsAvg())
				.rowsProcessedAvg(schemaStasticsDTO.getRowsProcessedAvg()).cpuTimeAvg(schemaStasticsDTO.getCpuTimeAvg())
				.elapsedTimeAvg(schemaStasticsDTO.getElapsedTimeAvg()).cpuTimeMax(schemaStasticsDTO.getCpuTimeMax())
				.elapsedTimeMax(schemaStasticsDTO.getElapsedTimeMax()).cpuTimeTot(schemaStasticsDTO.getCpuTimeTot())
				.elapsedTimeTot(schemaStasticsDTO.getElapsedTimeTot()).build();
	}

	private SchemaInfoEntity schemaInfoBuilder(SchemaInfoDTO schemaInfoDTO) {
		List<UsedBySchemaEntity> cpuUsedlist = new ArrayList<>();
		int size = schemaInfoDTO.getCpuUsed().size();
		for (int i = 0; i < size; i++) {
			cpuUsedlist.add(usedBySchemaBuilder(schemaInfoDTO.getCpuUsed().get(i)));
		}

		List<UsedBySchemaEntity> elapsedTimelist = new ArrayList<>();
		size = schemaInfoDTO.getElapsedTime().size();
		for (int i = 0; i < size; i++) {
			elapsedTimelist.add(usedBySchemaBuilder(schemaInfoDTO.getElapsedTime().get(i)));
		}

		List<UsedBySchemaEntity> bufferGetslist = new ArrayList<>();
		size = schemaInfoDTO.getBufferGets().size();
		for (int i = 0; i < size; i++) {
			bufferGetslist.add(usedBySchemaBuilder(schemaInfoDTO.getBufferGets().get(i)));
		}

		return SchemaInfoEntity.builder().cpuUsed(cpuUsedlist).elapsedTime(elapsedTimelist).bufferGets(bufferGetslist)
				.build();
	}

	private RealTimeMonitoringEntity realTimeMonitoringEntityBuilder(RealTimeMonitoringDTO realTimeMonitoringDTO) {
		List<SchemaStasticsEntity> schemaStasticslist = new ArrayList<>();
		int size = realTimeMonitoringDTO.getAllSchemaStastics().size();
		for (int i = 0; i < size; i++) {
			schemaStasticslist.add(schemaStasticsBuilder(realTimeMonitoringDTO.getAllSchemaStastics().get(i)));
		}

		List<SchemaQueryEntity> schemaQuerylist = new ArrayList<>();
		size = realTimeMonitoringDTO.getAllSchemaQueryInfo().size();
		for (int i = 0; i < size; i++) {
			schemaQuerylist.add(schemaQueryBuilder(realTimeMonitoringDTO.getAllSchemaQueryInfo().get(i)));
		}

		List<SchemaInfoEntity> schemaInfolist = new ArrayList<>();
		size = realTimeMonitoringDTO.getSchemas().size();
		for (int i = 0; i < size; i++) {
			schemaInfolist.add(schemaInfoBuilder(realTimeMonitoringDTO.getSchemas().get(i)));
		}

		return RealTimeMonitoringEntity.builder().time(realTimeMonitoringDTO.getTime())
				.oracleStatus(oracleStatusBuilder(realTimeMonitoringDTO.getOracleStatus())).schemas(schemaInfolist)
				.allSchemaStastics(schemaStasticslist).allSchemaQueryInfo(schemaQuerylist).build();
	}

	private OneHourMonitoringEntity oneHourMonitoringEntityBuilder(RealTimeMonitoringEntity realTimeMonitoringEntity) {
		return OneHourMonitoringEntity.builder().time(realTimeMonitoringEntity.getTime())
				.oracleStatus(realTimeMonitoringEntity.getOracleStatus()).schemas(realTimeMonitoringEntity.getSchemas())
				.allSchemaStastics(realTimeMonitoringEntity.getAllSchemaStastics())
				.allSchemaQueryInfo(realTimeMonitoringEntity.getAllSchemaQueryInfo()).build();
	}

	private SixHoursMonitoringEntity sixHoursMonitoringEntityBuilder(
			RealTimeMonitoringEntity realTimeMonitoringEntity) {
		return SixHoursMonitoringEntity.builder().time(realTimeMonitoringEntity.getTime())
				.oracleStatus(realTimeMonitoringEntity.getOracleStatus()).schemas(realTimeMonitoringEntity.getSchemas())
				.allSchemaStastics(realTimeMonitoringEntity.getAllSchemaStastics())
				.allSchemaQueryInfo(realTimeMonitoringEntity.getAllSchemaQueryInfo()).build();
	}

	private OneDayMonitoringEntity oneDayMonitoringEntityBuilder(RealTimeMonitoringEntity realTimeMonitoringEntity) {
		return OneDayMonitoringEntity.builder().time(realTimeMonitoringEntity.getTime())
				.oracleStatus(realTimeMonitoringEntity.getOracleStatus()).schemas(realTimeMonitoringEntity.getSchemas())
				.allSchemaStastics(realTimeMonitoringEntity.getAllSchemaStastics())
				.allSchemaQueryInfo(realTimeMonitoringEntity.getAllSchemaQueryInfo()).build();
	}

	private OutlierDataEntity outlierDataEntityBuilder(String time, double databaseCpuTimeRatio) {
		return OutlierDataEntity.builder().time(time).databaseCpuTimeRatio(databaseCpuTimeRatio).build();
	}
}
