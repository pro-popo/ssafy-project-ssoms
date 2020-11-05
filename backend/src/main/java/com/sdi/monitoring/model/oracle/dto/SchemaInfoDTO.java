package com.sdi.monitoring.model.oracle.dto;

import java.util.List;

import lombok.Data;

@Data
public class SchemaInfoDTO {
	private List<TimePerUsedBySchemaDTO> cpuUsed;
	private List<TimePerUsedBySchemaDTO> elapsedTime;
	private List<AmountUsedBySchemaDTO> bufferGets;
}
