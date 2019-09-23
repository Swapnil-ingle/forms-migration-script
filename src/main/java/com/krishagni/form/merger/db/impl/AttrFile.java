package com.krishagni.form.merger.db.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;

import org.apache.commons.io.FileUtils;

import com.krishagni.form.merger.util.MergeFileUtils;
import com.krishagni.form.merger.util.MergeProperties;

public class AttrFile {
	private String type;
	
	private String extension;
	
	private Object data;
	
	private String nameGenStrategy;
	
	private Integer idx = 0;
	
	private String parentDir;
	
	private String prefix;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getNameGenStrategy() {
		return nameGenStrategy;
	}

	public void setNameGenStrategy(String nameGenStrategy) {
		this.nameGenStrategy = nameGenStrategy;
	}

	public Integer getIdx() {
		++this.idx;
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public String getParentDir() {
		return parentDir;
	}

	public void setParentDir(String parentDir) {
		this.parentDir = parentDir;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String createFile() {
		String fileName = MergeFileUtils.generateFileName(getNameGenStrategy(), 
				getPrefix(), getIdx(), getExtension());
		
		if (getParentDir() == null) {
			setParentDir(MergeProperties.getInstance().getFileOpDir() + File.separatorChar + "files");
		}
		
		MergeFileUtils.ensureDirExists(getParentDir());
		File file = new File(getParentDir(), fileName);
		writeToFile(file);
		
		return fileName;
	}

	private void writeToFile(File file) {
		try {
			switch (getType()) {
				case "blob":
					MergeFileUtils.writeBlobToFile(file, getData());
					break;
				case "clob":
					MergeFileUtils.writeClobToFile(file, getData());
					break;
				case "text":
					FileUtils.writeStringToFile(file, (String) data, StandardCharsets.UTF_8);
					break;
				default:
					throw new InvalidParameterException("Invalid file type");
			}
		} catch (Exception e) {
			System.err.println("Error while making file: ");
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Type [file=" + type + ", extension=" + extension + ", fileNameGenStrategy=" + nameGenStrategy + "]";
	}
}
