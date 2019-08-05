package com.krishagni.miami.driver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.krishagni.miami.entity.Consents;
import com.krishagni.miami.entity.DeviationDetail;
import com.krishagni.miami.entity.ProcedureDetail;
import com.krishagni.miami.entity.QuestionDetail;

public class BOConsentFormDeveloper {
	private static final String URL = "jdbc:mysql://localhost:3306/catissue_suite";
	
	private static final String USER = "root";
	
	private static final String PWD = "root";
	
	private static final String OUTPUT_DIR = "/Users/swapnil/links/miami-consent-form-merger/output-dir/";
	
	private static final String ZIP_OUTPUT_DIR = "/Users/swapnil/links/miami-consent-form-merger/bo-form.zip";
	
	private final static String DATE_FORMAT = "dd-MM-yyyy";
	
	private final static String[] HEADERS = {
			"ConsentFormV4 ID","Collection Protocol","PPID","Activity Status","Version","Site","POC","Consent Status","Date of Consent","Consent Language","Re-Consent","Reason","Consent Withdrawn","Comments","Consent Form",
			"Question 1_v2: Specifically by signing this informed consent form I am agreeing to: 1. The collection and storage of samples from tissues and body fluids removed during biopsy surgical resection and treatment of my cancer (performed as part of my routine clinical care) 2. The collection of additional blood specimen/s 3. The collection of relevant past present and future clinical information from my University of Miami affiliated institution medical records laboratory tests radiology reports operative notes pathology reports treatment records and discharge summaries for medical research purposes and then store this data in the University of Miami TBCF database 4. The distribution of samples from the University of Miami TBCF to other academic institutions for the purpose of research 5. The analysis of genes and molecules (genetic research) including DNA RNA proteins and other molecules from my stored blood urine tissue body fluid samples and cell lines 6. The establishment of continuous cell lines from my stored blood urine tissue and body fluid samples 7. The distribution of samples or continuous cell lines from my stored blood urine tissue and body fluid samples to other institutions or companies for purposes of medical research 8. If participating in a clinical trial the collection and storage of additional research samples of tissues and body fluids (including blood) removed during biopsy surgical resection and treatment of my disease.","Response1_v2",
			"Question 1_v4_1: Specifically  by signing this informed consent form  I am agreeing to: 1. The collection and storage of samples from tissues and body fluids removed during biopsy  surgical resection and treatment of my cancer (performed as part of my routine clinical care) 2. The collection of additional blood specimen/s 3. The collection of relevant past  present and future clinical information from my University of Miami affiliated institution medical records  laboratory tests  radiology reports  operative notes  pathology reports  treatment records  and discharge summaries for medical research purposes and then store this data in the University of Miami BSSR database 4. The distribution of samples from the University of Miami BSSR to other academic institutions for the purpose of research 5. The analysis of genes and molecules (genetic research) including DNA  RNA  proteins and other molecules from my stored blood  urine  tissue  body fluid samples and cell lines 6. The establishment of continuous cell lines from my stored blood  urine  tissue and body fluid samples 7. The distribution of samples or continuous cell lines from my stored blood  urine  tissue and body fluid samples to other institutions or companies for purposes of medical research 8. If participating in a clinical trial  the collection and storage of additional research samples of tissues and body fluids (including blood) removed during biopsy  surgical resection and treatment of my disease.","Response1_v4_1",
			"Question 1_v4_2: Specifically  by signing this informed consent form  I am agreeing to: 1. The collection and storage of samples from tissues and body fluids removed during biopsy  surgical resection and treatment of my child's cancer (performed as part of their routine clinical care) 2. The collection of additional blood specimen/s 3. The collection of relevant past  present and future clinical information from their University of Miami affiliated institution medical records  laboratory tests  radiology reports  operative notes  pathology reports  treatment records  and discharge summaries for medical research purposes and then store this data in the University of Miami BSSR database 4. The distribution of samples from the University of Miami BSSR to other academic institutions for the purpose of research 5. The analysis of genes and molecules (genetic research) including DNA  RNA  proteins and other molecules from my child's stored blood  urine  tissue  body fluid samples and cell lines 6. The establishment of continuous cell lines from their stored blood  urine  tissue and body fluid samples 7. The distribution of samples or continuous cell lines from their stored blood  urine  tissue and body fluid samples to other institutions or companies for purposes of medical research 8. If participating in a clinical trial  the collection and storage of additional research samples of tissues and body fluids (including blood) removed during biopsy  surgical resection and treatment of my disease as discussed with the trial physician.","Response1_v4_2",
			"Question 1: Subject wants to be in this research study?","Response1",
			"Question 1a: I give permission for my/my child s samples and information to be utilised for any future study. ","Response1a",
			"Question 1b: I give permission for my/my child s samples and information to be utilised ONLY for future research to (Specify research in the provided textbox) ","Response1b",
			"Question 2: I permit the University of Miami to use my/my child s sample(s) for genetic research. ","Response2",
			"Question 3: I permit the University of Miami to give my/my child s de-identified or coded sample to researchers at other institutions and acknowledge that the testing performed on my/my child s sample may include genetic testing.","Response3",
			"Procedure Details#1#Procedure Type","Procedure Details#1#Date of Procedure","Procedure Details#1#Tissue Site","Procedure Details#1#Tissue Collected","Procedure Details#1#Reason Tissue Not Collected","Procedure Details#1#Tissue Status","Procedure Details#1#Comments",
			"Procedure Details#2#Procedure Type","Procedure Details#2#Date of Procedure","Procedure Details#2#Tissue Site","Procedure Details#2#Tissue Collected","Procedure Details#2#Reason Tissue Not Collected","Procedure Details#2#Tissue Status","Procedure Details#2#Comments",
			"Procedure Details#3#Procedure Type","Procedure Details#3#Date of Procedure","Procedure Details#3#Tissue Site","Procedure Details#3#Tissue Collected","Procedure Details#3#Reason Tissue Not Collected","Procedure Details#3#Tissue Status","Procedure Details#3#Comments",
			"Deviations#1#Deviation Number","Deviations#2#Deviation Number","Deviations#3#Deviation Number"};
	
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(date);
	}

	public static void main(String[] args) {
		Connection conn = null;
		CSVPrinter printer = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PWD);
			System.out.println("Connected to the Database!");
			
			Statement sql = conn.createStatement();
			PreparedStatement consentFormPrepStmt = conn.prepareStatement(Consents.CONSENT_FORM_QUERY);
			PreparedStatement procPrepStmt = conn.prepareStatement(ProcedureDetail.QUERY);
			PreparedStatement QuesPrepStmt = conn.prepareStatement(QuestionDetail.QUERY);
			PreparedStatement DeviationPrepStmt = conn.prepareStatement(DeviationDetail.QUERY);
			
			System.out.println("Executing the consent query...");
			sql.setFetchSize(25);
			ResultSet rs = sql.executeQuery(Consents.QUERY);
			
			System.out.println("Creating the output CSV file...");
			printer = createCsvFile();
			printer.printRecord(HEADERS);
			
			List<String> row = new ArrayList<>();
			int rowIdx = 0;
			
			System.out.println("Iterating through the results...");
			Consents consent = new Consents();
			while (rs.next()) {
				rowIdx++;
				
				consent.init(rs);
				
				// System.out.println("Processing Procedure Details...");
				List<ProcedureDetail> pds = processProcedureDetails(procPrepStmt, consent.getIdentifier());
				// System.out.println("Processing Questionnaire Details...");
				List<String> questions = processQuestionaires(QuesPrepStmt, consent.getIdentifier());
				// System.out.println("Processing Deviation Details...");
				List<DeviationDetail> deviations = processDeviations(DeviationPrepStmt, consent.getIdentifier());
				
				// System.out.println("Add to row buffer...");
				row.add(null); // Blank recordId
				row.addAll(consent.getValues());
				row.addAll(questions);
				pds.forEach(pd -> {
					row.addAll(pd.getValues());
				});
				deviations.forEach(d -> {
					row.addAll(d.getValues());
				});
				
				
				printer.printRecord(row);
				// System.out.println("Creating attached file...");
				// consent.makeFile(OUTPUT_DIR, consentFormPrepStmt);
				
				if (rowIdx % 25 == 0) {
					System.out.println("Flushing upto row " + rowIdx + " to the output file...");
					printer.flush();
				}
				
				row.clear();
			}
			
			printer.close();
			
			// ZIP the output DIR
			System.out.println("Zipping the CSV and files...");
			FileOutputStream fileOpS = new FileOutputStream(ZIP_OUTPUT_DIR);
			ZipOutputStream zipOpS = new ZipOutputStream(fileOpS);
			File dirToZip = new File(OUTPUT_DIR);
			
			for (File file: dirToZip.listFiles()) {
				zipFiles(file, file.getName(), zipOpS);
			}

			zipOpS.close();
			fileOpS.close();
			
			// Delete the generated files and CSV
			System.out.println("Deleting stale files dir: " + dirToZip.getPath());
			// deleteFiles(dirToZip);
			
			System.out.println("Done!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeQuitely(conn);
		}
	}
	
	private static void deleteFiles(File dirToZip) {
		for (File file: dirToZip.listFiles()) {
			if (file.isDirectory()) {
				deleteFiles(file);
			}
			file.delete();
		}
	}

	private static void zipFiles(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
	    if (fileToZip.isHidden()) {
	        return;
	    }
	    if (fileToZip.isDirectory()) {
	        if (fileName.endsWith(String.valueOf(File.separatorChar))) {
	            zipOut.putNextEntry(new ZipEntry(fileName));
	            zipOut.closeEntry();
	        } else {
	            zipOut.putNextEntry(new ZipEntry(fileName + File.separatorChar));
	            zipOut.closeEntry();
	        }
	        File[] children = fileToZip.listFiles();
	        for (File childFile : children) {
	        	zipFiles(childFile, fileName + File.separatorChar + childFile.getName(), zipOut);
	        }
	        return;
	    }
	    FileInputStream fis = new FileInputStream(fileToZip);
	    ZipEntry zipEntry = new ZipEntry(fileName);
	    zipOut.putNextEntry(zipEntry);
	    byte[] bytes = new byte[1024];
	    int length;
	    while ((length = fis.read(bytes)) >= 0) {
	        zipOut.write(bytes, 0, length);
	    }
	    fis.close();
	}
	
	private static CSVPrinter createCsvFile() throws IOException {
		FileWriter out = new FileWriter(OUTPUT_DIR + File.separatorChar + "form-bo.csv");
	    return new CSVPrinter(out, CSVFormat.DEFAULT);
    }
	
	private static List<DeviationDetail> processDeviations(PreparedStatement deviationPrepStatement, long identifier) 
			throws SQLException {
		deviationPrepStatement.setLong(1, identifier);
		ResultSet rs = deviationPrepStatement.executeQuery();
		int i = 0;
		
		List<DeviationDetail> devDetails = new ArrayList<>();
		
		while (rs.next()) {
			DeviationDetail dd = new DeviationDetail(rs);
			devDetails.add(dd);
			i++;
		}
		
		for (int j = i; j<=2; j++) {
			DeviationDetail dd = new DeviationDetail();
			devDetails.add(dd);
		}
		
		return devDetails;
	}

	private static List<String> processQuestionaires(PreparedStatement quesPrepStatement, long identifier) 
			throws SQLException {
		quesPrepStatement.setLong(1, identifier);
		ResultSet rs = quesPrepStatement.executeQuery();
		QuestionDetail qd = new QuestionDetail();
		
		while (rs.next()) {
			qd.extractValuesFrom(rs);
			qd.addResponse();
		}
		
		return qd.getValues();
	}

	private static List<ProcedureDetail> processProcedureDetails(PreparedStatement procPrepStatement, long identifier) 
			throws SQLException {
		procPrepStatement.setLong(1, identifier);
		ResultSet rs = procPrepStatement.executeQuery();
		int i = 0;
		List<ProcedureDetail> procedureDetails = new ArrayList<>();
		
		while (rs.next()) {
			ProcedureDetail pd = new ProcedureDetail(rs);
			procedureDetails.add(pd);
			i++;
		}
		
		for (int j = i; j<=2; j++) {
			ProcedureDetail pd = new ProcedureDetail();
			procedureDetails.add(pd);
		}
		
		return procedureDetails;
	}

	private static <T extends AutoCloseable> void closeQuitely(T obj) {
		if (obj == null) {
			return;
		}
		
		try {
			obj.close();
		} catch (Exception e) {
			// Ignore the closing exception
		}
	}
	
	public final static String TIMESTAMP_FORMAT = "ddMMyyyy_HHmmss";
}