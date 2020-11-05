package com.sdi.monitoring.model.oracle.dto;

import java.util.List;

import lombok.Data;

@Data
public class SchemaInfoDTO {
	private List<UsedBySchemaDTO> cpuUsed;
	private List<UsedBySchemaDTO> elapsedTime;
	private List<UsedBySchemaDTO> bufferGets;
}
