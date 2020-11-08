package com.sdi.monitoring.model.oracle.repository;

import java.util.List;

import com.sdi.monitoring.model.oracle.dto.OracleStatusDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaQueryDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaStasticsDTO;
import com.sdi.monitoring.model.oracle.dto.UsedBySchemaDTO;

public interface OracleRepo {
	public OracleStatusDTO findOracleStastics();

	public List<SchemaStasticsDTO> findAllSchemaStastics(List<String> schemaList);

	public List<SchemaQueryDTO> findAllSchemaQueryInfo(List<String> schemaList);

	public List<UsedBySchemaDTO> findCpuUsedBySchema(String schemaName);

	public List<UsedBySchemaDTO> findElapsedTimeBySchema(String schemaName);

	public List<UsedBySchemaDTO> findBufferGetsBySchema(String schemaName);
	
	public boolean findSchema(String schemaName);
}
