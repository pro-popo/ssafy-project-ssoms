package com.sdi.monitoring.model.oracle.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sdi.monitoring.model.oracle.dto.AmountUsedBySchemaDTO;
import com.sdi.monitoring.model.oracle.dto.OracleStatusDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaQueryDTO;
import com.sdi.monitoring.model.oracle.dto.SchemaStasticsDTO;
import com.sdi.monitoring.model.oracle.dto.TimePerUsedBySchemaDTO;
import com.sdi.monitoring.util.DBUtil;

@Repository
public class OracleRepoImpl implements OracleRepo{
	@Override
	public OracleStatusDTO findOracleStastics() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OracleStatusDTO oracleStatusDTO = null;
		try {
			con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			Map<String, Double> map = new HashMap<>();
			sql.append("select metric_name, round(VALUE,2) as value\n");
			sql.append("from SYS.V_$SYSMETRIC\n");
			sql.append("where METRIC_NAME IN ('Database CPU Time Ratio',\n");
			sql.append("                            'Database Wait Time Ratio',\n");
			sql.append("                            'Active Serial Sessions',\n");
			sql.append("                            'DB Block Gets Per User Call',\n");
			sql.append("                            'Logical Reads Per User Call',\n");
			sql.append("                            'Redo Generated Per Sec',\n");
			sql.append("                            'Executions Per Sec',\n");
			sql.append("                            'Total Parse Count Per Sec',\n");
			sql.append("                            'Open Cursors Per Sec',\n");
			sql.append("                            'User Commits Per Sec',\n");
			sql.append("                            'Physical Reads Per Sec',\n");
			sql.append("                            'Physical Writes Per Sec') AND\n");
			sql.append("        INTSIZE_CSEC = (select max(INTSIZE_CSEC) from SYS.V_$SYSMETRIC)\n");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				map.put(rs.getString("METRIC_NAME"), rs.getDouble("value"));
			}
			oracleStatusDTO = new OracleStatusDTO();
			oracleStatusDTO.setDatabaseCpuTimeRatio(map.get("Database CPU Time Ratio"));
			oracleStatusDTO.setDatabaseWaitTimeRatio(map.get("Database Wait Time Ratio"));
			oracleStatusDTO.setActiveSerialSessions(map.get("Active Serial Sessions"));
			oracleStatusDTO.setDbBlockGetsPerUserCall(map.get("DB Block Gets Per User Call"));
			oracleStatusDTO.setLogicalReadsPerUserCall(map.get("Logical Reads Per User Call"));
			oracleStatusDTO.setRedoGeneratedPerSec(map.get("Redo Generated Per Sec"));
			oracleStatusDTO.setExecutionsPerSec(map.get("Executions Per Sec"));
			oracleStatusDTO.setTotalParseCountPerSec(map.get("Total Parse Count Per Sec"));
			oracleStatusDTO.setOpenCursorsPerSec(map.get("Open Cursors Per Sec"));
			oracleStatusDTO.setUserCommitsPerSec(map.get("User Commits Per Sec"));
			oracleStatusDTO.setPhysicalReadsPerSec(map.get("Physical Reads Per Sec"));
			oracleStatusDTO.setPhysicalWritesPerSec(map.get("Physical Writes Per Sec"));
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(con);
		}
		return oracleStatusDTO;
	}
	
	@Override
	public List<SchemaStasticsDTO> findAllSchemaStastics(List<String> schemaList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SchemaStasticsDTO> list = new ArrayList<SchemaStasticsDTO>();
		try {
			con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			int schemaListSize = schemaList.size();
			String sqlPlus = "";
			for(int i = 0 ; i < schemaListSize ; i++) {
				sqlPlus += "?,";
			}
			sqlPlus = schemaListSize != 0 ? sqlPlus.substring(0, schemaListSize * 2 - 1) : sqlPlus;
			sql.append("select parsing_schema_name\n");
			sql.append(", count(*) sql_cnt\n");
			sql.append(", sum(executions) executions\n");
			sql.append(", round(avg(buffer_gets/executions)) buffer_gets\n");
			sql.append(", round(avg(disk_reads/executions)) disk_reads\n");
			sql.append(", round(avg(rows_processed/executions)) rows_processed\n");
			sql.append(", round(avg(cpu_time/executions/1000000),2) cpu_time_avg\n");
			sql.append(", round(avg(elapsed_time/executions/1000000),2) elapsed_time_avg\n");
			sql.append(", round(max(cpu_time/executions/1000000),2) cpu_time_max\n");
			sql.append(", round(max(elapsed_time/executions/1000000),2) elapsed_time_max\n");
			sql.append(", round(sum(cpu_time/tot_cpu*100),2) cpu_time_tot\n");
			sql.append(", round(sum(elapsed_time/tot_elapsed*100),2) elapsed_time_tot\n");
			sql.append("from v$sql,\n");
			sql.append("	(select sum(elapsed_time) tot_elapsed,\n");
			sql.append("	sum(cpu_time) tot_cpu\n");
			sql.append("	from v$sql)\n");
//			sql.append("	where parsing_schema_name in ('C##TESTDB', 'C##TESTDB2'))\n");			
			sql.append("where last_active_time >= sysdate - 7\n");
			sql.append("	and executions > 0\n");
			sql.append("	and parsing_schema_name in (" + sqlPlus + ")\n");
			sql.append("group by parsing_schema_name\n");
			pstmt = con.prepareStatement(sql.toString());
			
			int idx = 1;
			for(String schemaName : schemaList) {
				pstmt.setString(idx++, schemaName);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SchemaStasticsDTO schemaStasticsDTO = new SchemaStasticsDTO();
				schemaStasticsDTO.setParsingSchemaName(rs.getString("parsing_schema_name"));
				schemaStasticsDTO.setSqlCnt(rs.getLong("sql_cnt"));
				schemaStasticsDTO.setExecutions(rs.getLong("executions"));
				schemaStasticsDTO.setBufferGetsAvg(rs.getDouble("buffer_gets"));
				schemaStasticsDTO.setDiskReadsAvg(rs.getDouble("disk_reads"));
				schemaStasticsDTO.setRowsProcessedAvg(rs.getDouble("rows_processed"));
				schemaStasticsDTO.setCpuTimeAvg(rs.getDouble("cpu_time_avg"));
				schemaStasticsDTO.setElapsedTimeAvg(rs.getDouble("elapsed_time_avg"));
				schemaStasticsDTO.setCpuTimeMax(rs.getDouble("cpu_time_max"));
				schemaStasticsDTO.setElapsedTimeMax(rs.getDouble("elapsed_time_max"));
				schemaStasticsDTO.setCpuTimeTot(rs.getDouble("cpu_time_tot"));
				schemaStasticsDTO.setElasedTimeTot(rs.getDouble("elapsed_time_tot"));
				list.add(schemaStasticsDTO);
			}

		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(con);
		}
		return list;
	}
	
	@Override
	public List<SchemaQueryDTO> findAllScehmaQueryInfo(List<String> schemaList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SchemaQueryDTO> list = new ArrayList<SchemaQueryDTO>();
		try {
			con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			int schemaListSize = schemaList.size();
			String sqlPlus = "";
			for(int i = 0 ; i < schemaListSize ; i++) {
				sqlPlus += "?,";
			}
			sqlPlus = schemaListSize != 0 ? sqlPlus.substring(0, schemaListSize * 2 - 1) : sqlPlus;
			sql.append("select parsing_schema_name\r\n" + 
					"     , (SELECT rtrim(xmlagg(xmlelement(e, sql_text ,' ').extract('//text()') order by piece).GetClobVal(),' ')\r\n" + 
					"          FROM  v$sqltext b WHERE b.sql_id = s.sql_id GROUP BY sql_id ) as sql_full_text\r\n" + 
					"     , executions executions\r\n" + 
					"     , round(buffer_gets/executions) buffer_gets\r\n" + 
					"     , round(disk_reads/executions) disk_reads\r\n" + 
					"     , round(rows_processed/executions) rows_processed\r\n" + 
					"     , round(cpu_time/executions/1000000,2) cpu_time_avg\r\n" + 
					"     , round(elapsed_time/executions/1000000,2) elapsed_time_avg\r\n" + 
					"from   v$sql s\r\n" + 
					"where  last_active_time >= sysdate - 7\r\n" + 
					"and    parsing_schema_name in (" + sqlPlus + ")\r\n" + 
					"and    executions > 0\r\n" + 
					"and NOT (TRIM(SQL_TEXT) LIKE '%:Q%'\r\n" + 
					"			        OR TRIM(SQL_TEXT) LIKE 'declare%'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE 'DECLARE%'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE 'truncate%'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE 'TRUNCATE%'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE '%dba\\_%' escape '\\'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE '%DBA\\_%' escape '\\'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE 'SQLGateMain%'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE 'ALTER SESSION%'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE '%v$%'\r\n" + 
					"                    OR TRIM(SQL_TEXT) LIKE '%x$%')\r\n" + 
					"order by CPU_TIME_AVG desc\n");
			pstmt = con.prepareStatement(sql.toString());
			int idx = 1;
			for(String schemaName : schemaList) {
				pstmt.setString(idx++, schemaName);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				SchemaQueryDTO schemaQueryDTO = new SchemaQueryDTO();
				schemaQueryDTO.setParsingSchemaName(rs.getString("parsing_schema_name"));
				schemaQueryDTO.setSql(rs.getString("sql_full_text"));
				schemaQueryDTO.setExecutions(rs.getLong("executions"));
				schemaQueryDTO.setBufferGets(rs.getDouble("buffer_gets"));
				schemaQueryDTO.setDiskReads(rs.getDouble("disk_reads"));
				schemaQueryDTO.setRowsProcessed(rs.getDouble("rows_processed"));
				schemaQueryDTO.setCpuTimeAvg(rs.getDouble("cpu_time_avg"));
				schemaQueryDTO.setElapsedTimeAvg(rs.getDouble("elapsed_time_avg"));
				list.add(schemaQueryDTO);
			}

		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(con);
		}
		return list;
	}
	
	@Override
	public List<TimePerUsedBySchemaDTO> findCpuUsedBySchema(String schemaName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TimePerUsedBySchemaDTO> list = new ArrayList<TimePerUsedBySchemaDTO>();
		try {
			con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT main.parsing_schema_name parsing_schema_name, module, to_char(LAST_ACTIVE_TIME,'yyyy-mm-dd hh24:mi:ss') last_active_time, main.executions executions, main.cpu_time/1000 cpu_time_per_sec, main.cpu_time_ratio\n");
			sql.append("		, (SELECT rtrim(xmlagg(xmlelement(e, sql_text ,' ').extract('//text()') order by piece).GetClobVal(),' ')\n");
			sql.append("          FROM  v$sqltext b WHERE b.sql_id = main.sql_id GROUP BY sql_id ) as sql_full_text\n");
			sql.append("FROM ( SELECT sql_id,\n");
			sql.append("              parsing_schema_name,\n");
			sql.append("              executions,\n");
			sql.append("              cpu_time,\n");
			sql.append("			  LAST_ACTIVE_TIME,\n");
			sql.append("			  module,\n");
			sql.append("              round(cpu_time/sum(cpu_time) OVER()*100,1) cpu_time_ratio\n");
			sql.append("       FROM   v$sqlarea a\n");
			sql.append("       WHERE  executions > 1 and\n");
			sql.append("	   parsing_schema_name in (?)\n");
			sql.append("	   and NOT (TRIM(SQL_TEXT) LIKE '%:Q%'\n");
			sql.append("			        OR TRIM(SQL_TEXT) LIKE 'declare%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'DECLARE%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'truncate%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'TRUNCATE%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%dba\\_%' escape '\\'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%DBA\\_%' escape '\\'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'SQLGateMain%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'ALTER SESSION%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%v$%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%x$%')\n");
			sql.append("       ORDER BY cpu_time/sum(cpu_time) OVER()*100 desc ) main\n");
			sql.append("WHERE rownum <= 20\n");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, schemaName);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TimePerUsedBySchemaDTO timePerusedBySchemaDTO = new TimePerUsedBySchemaDTO();
				timePerusedBySchemaDTO.setParsingSchemaName(rs.getString("parsing_schema_name"));
				timePerusedBySchemaDTO.setModule(rs.getString("module"));
				timePerusedBySchemaDTO.setLastActiveTime(rs.getString("last_active_time"));
				timePerusedBySchemaDTO.setExecutions(rs.getLong("executions"));
				timePerusedBySchemaDTO.setTimePerSec(rs.getDouble("cpu_time_per_sec"));
				timePerusedBySchemaDTO.setTimeRatio(rs.getDouble("cpu_time_ratio"));
				timePerusedBySchemaDTO.setSql(rs.getString("sql_full_text"));
				list.add(timePerusedBySchemaDTO);
			}

		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(con);
		}			
		return list;
	}
	
	@Override
	public List<TimePerUsedBySchemaDTO> findElapsedTimeBySchema(String schemaName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TimePerUsedBySchemaDTO> list = new ArrayList<TimePerUsedBySchemaDTO>();
		try {
			con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT main.parsing_schema_name parsing_schema_name, module, to_char(LAST_ACTIVE_TIME,'yyyy-mm-dd hh24:mi:ss') last_active_time, main.executions executions, main.elapsed_time/1000 elapsed_time_per_sec, main.elapsed_time_ratio\n");
			sql.append("		, (SELECT rtrim(xmlagg(xmlelement(e, sql_text ,' ').extract('//text()') order by piece).GetClobVal(),' ')\n");
			sql.append("          FROM  v$sqltext b WHERE b.sql_id = main.sql_id GROUP BY sql_id ) as sql_full_text\n");
			sql.append("FROM ( SELECT sql_id,\n");
			sql.append("              parsing_schema_name,\n");
			sql.append("              executions,\n");
			sql.append("              elapsed_time,\n");
			sql.append("			  LAST_ACTIVE_TIME,\n");
			sql.append("			  module,\n");
			sql.append("              round(elapsed_time/sum(elapsed_time) OVER () *100 ,1) elapsed_time_ratio\n");
			sql.append("       FROM   v$sqlarea a\n");
			sql.append("       WHERE  executions > 1 and\n");
			sql.append("	   parsing_schema_name in (?)\n");
			sql.append("	   and NOT (TRIM(SQL_TEXT) LIKE '%:Q%'\n");
			sql.append("			        OR TRIM(SQL_TEXT) LIKE 'declare%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'DECLARE%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'truncate%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'TRUNCATE%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%dba\\_%' escape '\\'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%DBA\\_%' escape '\\'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'SQLGateMain%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'ALTER SESSION%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%v$%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%x$%')\n");
			sql.append("       ORDER BY cpu_time/sum(cpu_time) OVER()*100 desc ) main\n");
			sql.append("WHERE rownum <= 20\n");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, schemaName);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TimePerUsedBySchemaDTO timePerusedBySchemaDTO = new TimePerUsedBySchemaDTO();
				timePerusedBySchemaDTO.setParsingSchemaName(rs.getString("parsing_schema_name"));
				timePerusedBySchemaDTO.setModule(rs.getString("module"));
				timePerusedBySchemaDTO.setLastActiveTime(rs.getString("last_active_time"));
				timePerusedBySchemaDTO.setExecutions(rs.getLong("executions"));
				timePerusedBySchemaDTO.setTimePerSec(rs.getDouble("elapsed_time_per_sec"));
				timePerusedBySchemaDTO.setTimeRatio(rs.getDouble("elapsed_time_ratio"));
				timePerusedBySchemaDTO.setSql(rs.getString("sql_full_text"));
				list.add(timePerusedBySchemaDTO);
			}
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(con);
		}		
		return list;
	}
	
	@Override
	public List<AmountUsedBySchemaDTO> findBufferGetsBySchema(String schemaName) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AmountUsedBySchemaDTO> list = new ArrayList<AmountUsedBySchemaDTO>();
		try {
			con = DBUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT main.parsing_schema_name parsing_schema_name, module, to_char(LAST_ACTIVE_TIME,'yyyy-mm-dd hh24:mi:ss') last_active_time, main.executions executions, main.buffer_gets, main.buffer_gets_ratio\n");
			sql.append("		, (SELECT rtrim(xmlagg(xmlelement(e, sql_text ,' ').extract('//text()') order by piece).GetClobVal(),' ')\n");
			sql.append("          FROM  v$sqltext b WHERE b.sql_id = main.sql_id GROUP BY sql_id ) as sql_full_text\n");
			sql.append("FROM ( SELECT sql_id,\n");
			sql.append("              parsing_schema_name,\n");
			sql.append("              executions,\n");
			sql.append("              buffer_gets,\n");
			sql.append("			  LAST_ACTIVE_TIME,\n");
			sql.append("			  module,\n");
			sql.append("              round(buffer_gets/sum(buffer_gets) OVER()*100,1) buffer_gets_ratio\n");
			sql.append("       FROM   v$sqlarea a\n");
			sql.append("       WHERE  executions > 1 and\n");
			sql.append("	   parsing_schema_name in (?)\n");
			sql.append("	   and NOT (TRIM(SQL_TEXT) LIKE '%:Q%'\n");
			sql.append("			        OR TRIM(SQL_TEXT) LIKE 'declare%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'DECLARE%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'truncate%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'TRUNCATE%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%dba\\_%' escape '\\'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%DBA\\_%' escape '\\'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'SQLGateMain%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE 'ALTER SESSION%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%v$%'\n");
			sql.append("                    OR TRIM(SQL_TEXT) LIKE '%x$%')\n");
			sql.append("       ORDER BY cpu_time/sum(cpu_time) OVER()*100 desc ) main\n");
			sql.append("WHERE rownum <= 20\n");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, schemaName);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				AmountUsedBySchemaDTO amountUsedBySchemaDTO = new AmountUsedBySchemaDTO();
				amountUsedBySchemaDTO.setParsingSchemaName(rs.getString("parsing_schema_name"));
				amountUsedBySchemaDTO.setModule(rs.getString("module"));
				amountUsedBySchemaDTO.setLastActiveTime(rs.getString("last_active_time"));
				amountUsedBySchemaDTO.setExecutions(rs.getLong("executions"));
				amountUsedBySchemaDTO.setAmount(rs.getDouble("buffer_gets"));
				amountUsedBySchemaDTO.setAmountRatio(rs.getDouble("buffer_gets_ratio"));
				amountUsedBySchemaDTO.setSql(rs.getString("sql_full_text"));
				list.add(amountUsedBySchemaDTO);
			}

		} catch(Exception e){
			e.printStackTrace();
		}finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(con);
		}
		return list;
	}
}
