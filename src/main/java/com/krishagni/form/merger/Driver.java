package com.krishagni.form.merger;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishagni.form.merger.db.TableSource;
import com.krishagni.form.merger.db.impl.Table;
import com.krishagni.form.merger.db.impl.TableSourceImpl;
import com.krishagni.form.merger.util.CSVWriter;
import com.krishagni.form.merger.util.MergeProperties;

public class Driver {
	private static final String TIMESTAMP_FORMAT = "yyyy_MM_dd-HH_mm_ss";
	
	public static void main(String[] args) {
		String propFile = args[0];
		try {
			MergeProperties props = MergeProperties.getInstance().loadPropsFile(propFile);
			TableSource source = getTableSource(props.getMappingJson());
			CSVWriter writer = new CSVWriter(getFileOpDir(props), props.getFileName());
			writer.printHeaders(source.getHeaders());
			
			while (source.hasRows()) {
				for (Table row : source.getRows()) {
					writer.printToCSV(row.getCsv());
				}
				writer.flushPrinter();
			}
			
			writer.closeCsvWriter();
			System.out.println("Done!");
		} catch (Exception e) {
			System.out.println("Error occured while merging forms. ");
			e.printStackTrace();
		}
	}

	private static String getFileOpDir(MergeProperties props) {
		return props.getFileOpDir() + File.separatorChar + getCurrentTimeStamp();
	}

	private static TableSource getTableSource(String mappingJson) 
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(new File(mappingJson), TableSourceImpl.class);
	}
	
	private static String getCurrentTimeStamp() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return new SimpleDateFormat(TIMESTAMP_FORMAT).format(ts);
	}
}
