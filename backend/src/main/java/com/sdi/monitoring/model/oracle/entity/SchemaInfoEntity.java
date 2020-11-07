package com.sdi.monitoring.model.oracle.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Document("realtimedata")
public class SchemaInfoEntity {
	private List<UsedBySchemaEntity> cpuUsed;
	private List<UsedBySchemaEntity> elapsedTime;
	private List<UsedBySchemaEntity> bufferGets;
}
