package com.krishagni.form.merger.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MergeProperties extends Properties {
	private final static String FILE_NAME = "file.name";
	
	private final static String FILE_OUTPUT_DIR = "file.outputDir";
	
	private final static String JDBC_URL = "jdbc.url";
	
	private final static String JDBC_USER = "jdbc.user";
	
	private final static String JDBC_PWD = "jdbc.password";
	
	private final static String MAPPING_JSON = "mapping.json";
	
	private String jdbcUrl;
	
	private String jdbcUser;
	
	private String jdbcPwd;
	
	private String fileOpDir;
	
	private String fileName;
	
	private String mappingJson;
	
	private static MergeProperties mergeProperties;
	
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUser() {
		return jdbcUser;
	}

	public void setJdbcUser(String jdbcUser) {
		this.jdbcUser = jdbcUser;
	}

	public String getJdbcPwd() {
		return jdbcPwd;
	}

	public void setJdbcPwd(String jdbcPwd) {
		this.jdbcPwd = jdbcPwd;
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
	
	private static MergeProperties getMergeProperties() {
		return mergeProperties;
	}

	private static void setMergeProperties(MergeProperties mergeProperties) {
		MergeProperties.mergeProperties = mergeProperties;
	}
	
	private MergeProperties() {}

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
		this.setJdbcUrl(prop.getProperty(JDBC_URL));
		this.setJdbcUser(prop.getProperty(JDBC_USER));
		this.setJdbcPwd(prop.getProperty(JDBC_PWD));
		this.setMappingJson(prop.getProperty(MAPPING_JSON));
		
		return this;
	}
}
