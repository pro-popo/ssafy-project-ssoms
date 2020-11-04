package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sdi.monitoring.model.oracle.dto.AmountUsedBySchemaDTO;
import com.sdi.monitoring.model.oracle.dto.OracleStatusDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaQueryDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaStasticsDTO;
import com.sdi.monitoring.model.oracle.dto.TimePerUsedBySchemaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document("realtimedata")
@AllArgsConstructor
public class SchemaInfo {
	private List<TimePerUsedBySchemaDTO> cpuUsed;
	private List<TimePerUsedBySchemaDTO> elapsedTime;
	private List<AmountUsedBySchemaDTO> bufferGets;
	
	public SchemaInfo() {
		
	}
}
