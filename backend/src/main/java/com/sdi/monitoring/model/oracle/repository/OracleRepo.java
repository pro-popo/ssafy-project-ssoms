package com.sdi.monitoring.model.oracle.repository;

import java.util.List;

import com.sdi.monitoring.model.oracle.dto.AmountUsedBySchemaDTO;
import com.sdi.monitoring.model.oracle.dto.OracleStatusDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaQueryDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaStasticsDTO;
import com.sdi.monitoring.model.oracle.dto.TimePerUsedBySchemaDTO;

public interface OracleRepo {
	public OracleStatusDTO findOracleStastics();

	public List<SchemaStasticsDTO> findAllSchemaStastics(List<String> schemaList);

	public List<SchemaQueryDTO> findAllSchemaQueryInfo(List<String> schemaList);

	public List<TimePerUsedBySchemaDTO> findCpuUsedBySchema(String schemaName);

	public List<TimePerUsedBySchemaDTO> findElapsedTimeBySchema(String schemaName);

	public List<AmountUsedBySchemaDTO> findBufferGetsBySchema(String schemaName);
}
