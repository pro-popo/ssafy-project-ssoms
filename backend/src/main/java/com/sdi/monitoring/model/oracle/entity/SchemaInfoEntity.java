package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SchemaInfoEntity {
	private List<UsedBySchemaEntity> cpuUsed;
	private List<UsedBySchemaEntity> elapsedTime;
	private List<UsedBySchemaEntity> bufferGets;
}
