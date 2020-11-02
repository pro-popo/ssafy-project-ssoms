package com.sdi.monitoring.domain;

import lombok.Data;

@Data
public class OracleHostInfo {
	private String url;
	private String edition;
	private String id;
	private String pw;
	
	public OracleHostInfo() {}
	
	public OracleHostInfo(String url, String edition, String id, String pw) {
		this.url = url;
		this.edition = edition;
		this.id = id;
		this.pw = pw;
	}


	
	
}
