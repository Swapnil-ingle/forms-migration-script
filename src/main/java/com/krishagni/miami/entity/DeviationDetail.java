package com.krishagni.miami.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviationDetail {
	private static final String DEV_NUMBER = "DE_AT_8095";
	
	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public DeviationDetail() {}
	
	public DeviationDetail(ResultSet rs) throws SQLException {
		this.setNumber(rs.getString(DEV_NUMBER));
	}
	
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		
		values.add(getNumber());
		
		return values;
	}
	
	public final static String QUERY = 
			"select " + 
			"  dev.DE_AT_8095 " + 
			"from " + 
			"  TMP_de_e_8092 dev " + 
			"where " + 
			"  dev.de_e_t_8103 = ?";
}
