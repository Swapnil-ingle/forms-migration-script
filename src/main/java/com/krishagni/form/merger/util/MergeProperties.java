package com.krishagni.form.merger.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MergeProperties extends Properties {
	private final static String FILE_NAME = "file.name";
	
	private final static String FILE_OUTPUT_DIR = "file.outputDir";
	
	private final static String DB_URL = "db.url";
	
	private final static String DB_USER = "db.user";
	
	private final static String DB_PWD = "db.password";
	
	private final static String DB_TYPE = "db.type";

	private final static String MAPPING_JSON = "mapping.json";
	
	private String dbUrl;

	private String dbUser;
	
	private String dbPwd;
	
	private String dbType;
	
	private String fileOpDir;
	
	private String fileName;
	
	private String mappingJson;
	
	private static MergeProperties mergeProperties;
	
	private MergeProperties() {}

	private static MergeProperties getMergeProperties() {
		return mergeProperties;
	}

	private static void setMergeProperties(MergeProperties mergeProperties) {
		MergeProperties.mergeProperties = mergeProperties;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPwd() {
		return dbPwd;
	}

	public void setDbPwd(String dbPwd) {
		this.dbPwd = dbPwd;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getFileOpDir() {
		return fileOpDir;
	}

	public void setFileOpDir(String fileOpDir) {
		this.fileOpDir = fileOpDir;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getMappingJson() {
		return mappingJson;
	}

	public void setMappingJson(String mappingJson) {
		this.mappingJson = mappingJson;
	}
	
	public static MergeProperties getInstance() {
		if (getMergeProperties() == null) {
			setMergeProperties(new MergeProperties());
		}
		
		return getMergeProperties();
	}

	public MergeProperties loadPropsFile(String path) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(new FileInputStream(path));
		return toMergeProp(prop);
	}
	
	private MergeProperties toMergeProp(Properties prop) {
		this.setFileName(prop.getProperty(FILE_NAME));
		this.setFileOpDir(prop.getProperty(FILE_OUTPUT_DIR));
		this.setDbUrl(prop.getProperty(DB_URL));
		this.setDbUser(prop.getProperty(DB_USER));
		this.setDbPwd(prop.getProperty(DB_PWD));
		this.setDbType(prop.getProperty(DB_TYPE));
		this.setMappingJson(prop.getProperty(MAPPING_JSON));
		
		return this;
	}
}
