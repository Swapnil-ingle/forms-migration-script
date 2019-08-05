package com.krishagni.miami.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.krishagni.miami.driver.BOConsentFormDeveloper;

public class Consents {
	private final static String COLLECTION_PROTOCOL = "collection_protocol_id";
	
	private final static String PPID = "protocol_participant_id";
	
	private final static String VERSION = "DE_AT_5352";
	
	private final static String SITE = "DE_AT_5354";
	
	private final static String POC = "DE_AT_5357";
	
	private final static String CONSENT_STATUS = "DE_AT_5360";
	
	private final static String DATE_OF_CONSENT = "DE_AT_5358";
	
	private final static String CONSENT_LANGUAGE = "DE_AT_5361";
	
	private final static String RE_CONSENT = "DE_AT_5355";
	
	private final static String REASON = "DE_AT_5356";
	
	private final static String CONSENT_WITHDRAWN = "DE_AT_5359";
	
	private final static String COMMENTS = "DE_AT_5351";
	
	private final static String CONSENT_FORM = "DE_AT_5362";
	
	private final static String FILE_NAME = "DE_AT_5362_file_name";
	
	private final static String IDENTIFIER = "Identifier";
	
	private final static Map<String,String> cpIdToNameMap = new HashMap<>();
	
	private String collectionProtocol;
	
	private String ppid;
	
	private String activityStatus;
	
	private String version;
	
	private String site;
	
	private String poc;
	
	private String consentStatus;
	
	private Date dateOfConsent;
	
	private String consentLanguage;
	
	private String reconsent;
	
	private String reason;
	
	private byte consentWithdrawn;
	
	private String comments;
	
	private String fileName;
	
	private Blob consentForm;
	
	private long identifier;
	
	private Set<String> filesAdded = new HashSet<>();
	
	public String getCollectionProtocol() {
		return collectionProtocol;
	}

	public void setCollectionProtocol(String collectionProtocol) {
		this.collectionProtocol = collectionProtocol;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(String activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getPoc() {
		return poc;
	}

	public void setPoc(String poc) {
		this.poc = poc;
	}

	public String getConsentStatus() {
		return consentStatus;
	}

	public void setConsentStatus(String consentStatus) {
		this.consentStatus = consentStatus;
	}

	public Date getDateOfConsent() {
		return dateOfConsent;
	}

	public void setDateOfConsent(Date dateOfConsent) {
		this.dateOfConsent = dateOfConsent;
	}

	public String getConsentLanguage() {
		return consentLanguage;
	}

	public void setConsentLanguage(String consentLanguage) {
		this.consentLanguage = consentLanguage;
	}

	public String getReconsent() {
		return reconsent;
	}

	public void setReconsent(String reconsent) {
		this.reconsent = reconsent;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public byte getConsentWithdrawn() {
		return consentWithdrawn;
	}

	public void setConsentWithdrawn(byte consentWithdrawn) {
		this.consentWithdrawn = consentWithdrawn;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Blob getConsentForm() {
		return consentForm;
	}

	public void setConsentForm(Blob consentForm) {
		this.consentForm = consentForm;
	}
	
	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}
	
	public Set<String> getFilesAdded() {
		return filesAdded;
	}

	public void setFilesAdded(Set<String> filesAdded) {
		this.filesAdded = filesAdded;
	}
	
	public Consents() {
		// Initialize CpId-Name Map
		cpIdToNameMap.put("6","LUNG");
		cpIdToNameMap.put("4","HEAD/NECK/ENT");
		cpIdToNameMap.put("77","20070398");
		cpIdToNameMap.put("2","BREAST");
		cpIdToNameMap.put("27","URINARY");
		cpIdToNameMap.put("15","KOMOTAR");
		cpIdToNameMap.put("26","REPRODUCTIVE");
		cpIdToNameMap.put("7","GI");
		cpIdToNameMap.put("8","BRAIN");
		cpIdToNameMap.put("12","ENDOCRINE");
		cpIdToNameMap.put("22","SLINGERLAND BIOPSY");
		cpIdToNameMap.put("19","TBCF Collection");
		cpIdToNameMap.put("14","ABREU");
		cpIdToNameMap.put("11","SOFT TISSUE");
		cpIdToNameMap.put("28","LIVER");
		cpIdToNameMap.put("23","SUSSMAN");
		cpIdToNameMap.put("62","BSSR Tissue Collection");
		cpIdToNameMap.put("63","01-Hematopoetic and Lymphoid System");
		cpIdToNameMap.put("10","SKIN");
		cpIdToNameMap.put("32","Heme-Onc");
		cpIdToNameMap.put("36","Telischi");
		cpIdToNameMap.put("37","PROSTATE");
		cpIdToNameMap.put("46","Watts");
		cpIdToNameMap.put("58","Komotar Brain 2");
		cpIdToNameMap.put("66","04-GI System, Gallbladder, and Pancreas");
		cpIdToNameMap.put("44","S George");
		cpIdToNameMap.put("45","p16+ H/N");
		cpIdToNameMap.put("59","C. Dinh Accoustic Neuroma");
		cpIdToNameMap.put("56","LTCC");
		cpIdToNameMap.put("50","GYN/GYO");
		cpIdToNameMap.put("55","Slingerland Trial");
		cpIdToNameMap.put("54","Slingerland Fat Study");
		cpIdToNameMap.put("65","03-Kidney and Collecting System, Lower Urinary Tra");
		cpIdToNameMap.put("70","08-Endocrine System and ENT");
		cpIdToNameMap.put("71","09-Bones, Joints, Soft Tissue, and Skin");
		cpIdToNameMap.put("67","05-Breast");
		cpIdToNameMap.put("72","10-Central Nervous System, Peripherl Nerves and Mu");
		cpIdToNameMap.put("64","02-Lung and Mediastinum, Cardiovascular System");
		cpIdToNameMap.put("68","06-Female Genital System");
		cpIdToNameMap.put("73","BSSR Tissue Collections - Sophia George");
		cpIdToNameMap.put("74","BSSR Tissue Collections - Ashok Saluja");
		cpIdToNameMap.put("69","07-Male Genital System");
	}

	public void init(ResultSet rs) throws SQLException {
		this.setIdentifier(rs.getLong(IDENTIFIER));
		this.setCollectionProtocol(toCp(rs.getString(COLLECTION_PROTOCOL)));
		this.setPpid(rs.getString(PPID));
		this.setActivityStatus("");
		this.setVersion(rs.getString(VERSION));
		this.setSite(rs.getString(SITE));
		this.setPoc(rs.getString(POC));
		this.setConsentStatus(rs.getString(CONSENT_STATUS));
		this.setDateOfConsent(rs.getDate(DATE_OF_CONSENT));
		this.setConsentLanguage(rs.getString(CONSENT_LANGUAGE));
		this.setReconsent(rs.getString(RE_CONSENT));
		this.setReason(rs.getString(REASON));
		this.setConsentWithdrawn(rs.getByte(CONSENT_WITHDRAWN));
		this.setComments(rs.getString(COMMENTS));
		/* Commented because we are migrating files in second run 
		this.setFileName(toFileName(rs.getString(FILE_NAME)));
		*/ 
		// this.setConsentForm(rs.getBlob(CONSENT_FORM));
	}
	
	private String toCp(String cpId) {
		if (cpIdToNameMap.containsKey(cpId)) {
			return cpIdToNameMap.get(cpId);
		}
		
		return cpId;
	}

	private String toFileName(String fileName) {
		if (fileName == null) {
			return null;
		}
		
		String[] fileParts = fileName.split("\\.");
		
		if (filesAdded.contains(fileName)) {
			String newFileName = fileParts[0] + "_" +  getCurrentTimeStamp() + "." + fileParts[1];
			filesAdded.add(newFileName);
			return newFileName;
		}
		
		filesAdded.add(fileName);
		return fileName;
	}

	private String getCurrentTimeStamp() {
		return new SimpleDateFormat(BOConsentFormDeveloper.TIMESTAMP_FORMAT).format(new Date());
	}

	public void makeFile(String dir, PreparedStatement consentFormPrepStmt) throws SQLException, IOException {
		consentFormPrepStmt.setLong(1, getIdentifier());
		consentFormPrepStmt.setString(2, getCollectionProtocol());
		consentFormPrepStmt.setString(3, getPpid());
		
		ResultSet rs = consentFormPrepStmt.executeQuery();
		if (rs.next() == false) {
			return;
		}
		
		this.setConsentForm(rs.getBlob(CONSENT_FORM));
		
		if (getFileName() == null || getFileName().isEmpty()) {
			System.out.println("No attached file exists");
			return;
		}
		
		dir = dir + File.separatorChar + "files";
		File parentDir = new File(dir);
		
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		
		File output = new File(parentDir, getFileName());
		
		InputStream in = consentForm.getBinaryStream();
		OutputStream out = new FileOutputStream(output);
		IOUtils.copy(in, out);
		
		in.close();
		out.close();
		rs.close();
	}
	
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		
		values.add(getCollectionProtocol());
		values.add(getPpid());
		values.add(getActivityStatus());
		values.add(getVersion());
		values.add(getSite());
		values.add(getPoc());
		values.add(getConsentStatus());
		values.add(BOConsentFormDeveloper.formatDate(getDateOfConsent()));
		values.add(getConsentLanguage());
		values.add(getReconsent());
		values.add(getReason());
		values.add(String.valueOf(getConsentWithdrawn()));
		values.add(getComments());
		values.add(getFileName());
		
		return values;
	}
	
	public static final String CONSENT_FORM_QUERY = 
			"select " + 
			"  e.DE_AT_5362 " + 
			"from " + 
			"  TMP_de_e_5349 e " + 
			"where " + 
			"  e.Identifier = ? and e.collection_protocol_id = ? and e.protocol_participant_id = ?";
	
	public static final String QUERY = 
			"select " + 
			"  e.IDENTIFIER, e.collection_protocol_id, e.protocol_participant_id, " + 
			"  e.ACTIVITY_STATUS, e.DE_AT_5351, e.DE_AT_5352, e.DE_AT_5353, e.DE_AT_5354, e.DE_AT_5355, " +
			"  e.DE_AT_5356, e.DE_AT_5357, e.DE_AT_5358, e.DE_AT_5359, e.DE_AT_5360, e.DE_AT_5361, e.DE_AT_5362_file_name, " +
			"  e.DE_AT_5362_content_type, e.DYEXTN_AS_1983_2015 " +
			"from " + 
			"  TMP_de_e_5349 e";
}
