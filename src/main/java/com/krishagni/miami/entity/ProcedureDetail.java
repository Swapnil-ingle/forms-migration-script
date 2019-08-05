package com.krishagni.miami.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.krishagni.miami.driver.BOConsentFormDeveloper;

public class ProcedureDetail {
	private static final String TYPE = "DE_AT_7311";
	
	private static final String DATE = "DE_AT_5371";
	
	private static final String TISSUE_SITE = "DE_AT_5368";
	
	private static final String TISSUE_COLLECTED = "DE_AT_5369";
	
	private static final String REASON_TISSUE_NOT_COLLECTED = "DE_AT_5370";
	
	private static final String TISSUE_STATUS = "DE_AT_9302";
	
	private static final String COMMENTS = "DE_AT_5367";
	
	private String type;
	
	private Date date;
	
	private String tissueSite;
	
	private String tissueCollected;
	
	private String reasonTissueNotCollected;
	
	private String tissueStatus;
	
	private String comments;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTissueSite() {
		return tissueSite;
	}

	public void setTissueSite(String tissueSite) {
		this.tissueSite = tissueSite;
	}

	public String getTissueCollected() {
		return tissueCollected;
	}

	public void setTissueCollected(String tissueCollected) {
		this.tissueCollected = tissueCollected;
	}

	public String getReasonTissueNotCollected() {
		return reasonTissueNotCollected;
	}

	public void setReasonTissueNotCollected(String reasonTissueNotCollected) {
		this.reasonTissueNotCollected = reasonTissueNotCollected;
	}

	public String getTissueStatus() {
		return tissueStatus;
	}

	public void setTissueStatus(String tissueStatus) {
		this.tissueStatus = tissueStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public ProcedureDetail() {}
	
	public ProcedureDetail(ResultSet rs) throws SQLException {
		this.setType(rs.getString(TYPE));
		this.setDate(rs.getDate(DATE));
		this.setTissueSite(rs.getString(TISSUE_SITE));
		this.setTissueCollected(rs.getString(TISSUE_COLLECTED));
		this.setReasonTissueNotCollected(rs.getString(REASON_TISSUE_NOT_COLLECTED));
		this.setTissueStatus(rs.getString(TISSUE_STATUS));
		this.setComments(rs.getString(COMMENTS));
	}
	
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		
		values.add(getType());
		values.add(BOConsentFormDeveloper.formatDate(getDate()));
		values.add(getTissueSite());
		values.add(getTissueCollected());
		values.add(getReasonTissueNotCollected());
		values.add(getTissueStatus());
		values.add(getComments());
		
		return values;
	}
	
	public final static String QUERY = 
			"select " + 
			"  proc.DE_AT_5367, proc.DE_AT_5368, proc.DE_AT_5369, proc.DE_AT_5370, " + 
			"  proc.DE_AT_5371, proc.DE_AT_7311, proc.DE_AT_9302 " + 
			"from " + 
			"  TMP_de_e_5365 proc " + 
			"where " + 
			"  proc.de_e_t_5386 = ?";
}
