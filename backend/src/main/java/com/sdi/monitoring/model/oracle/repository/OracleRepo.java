package com.sdi.monitoring.model.oracle.repository;

import java.util.List;

public interface OracleRepo {
	public void findOracleStastics();

	public void findAllSchemaStastics(List<String> schemaList);

	public void findAllScehmaQueryInfo(List<String> schemaList);

	public void findCpuUsedBySchema(String schemaName);

	public void findElapsedTimeBySchema(String schemaName);

	public void findBufferGetsBySchema(String schemaName);
}
