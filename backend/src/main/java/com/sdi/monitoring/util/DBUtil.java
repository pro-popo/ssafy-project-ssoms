package com.sdi.monitoring.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Component;

import com.sdi.monitoring.domain.OracleHostInfo;
import com.sdi.monitoring.exception.InternalServerException;
import com.sdi.monitoring.model.oracle.dto.OracleDBSettingsDTO;

@Component
public class DBUtil {
	
	public static Connection getConnection() {
		try {
			OracleHostInfo oracleHostInfo = JsonParser.getHostInfo();
			String urlInfo = oracleHostInfo.getUrl();
			String editionInfo = oracleHostInfo.getEdition();
			String id = oracleHostInfo.getId();
			String pw = oracleHostInfo.getPw();
			String url = "jdbc:oracle:thin:@" + urlInfo + ":" + editionInfo;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			return DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
//			throw new InternalServerException("Cannot connect oracle database");
		}
		return null;
	}
	public static Connection checkConnection(OracleDBSettingsDTO oracleDB) {
		try {
			String id = oracleDB.getOracleID();
			String url = "jdbc:oracle:thin:@" + oracleDB.getOracleURL() + ":" + oracleDB.getOracleSID();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(url, id, oracleDB.getOraclePassword());
		} catch (Exception e) {
			e.printStackTrace();
//			throw new InternalServerException("Cannot connect oracle database");
		}
		return null;
	}
	public static void close(AutoCloseable c) {
		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				throw new InternalServerException("Cannot close oracle database");
			}
		}
	}

}
