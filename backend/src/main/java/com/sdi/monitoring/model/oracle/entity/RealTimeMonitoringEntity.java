package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sdi.monitoring.model.oracle.dto.OracleStatusDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaInfoDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaQueryDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaStasticsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@AllArgsConstructor
@Document("realtimedata")
public class RealTimeMonitoringEntity {
	private String time;
	private OracleStatusDTO oracleStatus;
	private List<SchemaInfoDTO> schemas;
	private List<SchemaStasticsDTO> allSchemaStastics;
	private List<SchemaQueryDTO> allSchemaQueryInfo;
	
	public RealTimeMonitoringEntity() {}
}
