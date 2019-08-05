package com.krishagni.miami.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionDetail {
	private static final String Q_2 = "Q_2";
	
	private static final String Q_3 = "Q_3";
	
	private static final String Q_1A = "Q_1A";
	
	private static final String Q_1B = "Q_1B";
	
	private static final String Q_1_v2 = "Q_1_v2";
	
	private static final String Q_1 = "Q_1";
	
	private static final String Q_1_v4_1 = "Q_1_v4_1";
	
	private static final String Q_1_v4_2 = "Q_1_v4_2";
	
	private static final String Q_1_v2_VAL = "Specifically by signing this informed consent form I am agreeing to: 1. The collection and storage of samples from tissues and body fluids removed during biopsy surgical resection and treatment of my cancer (performed as part of my routine clinical care) 2. The collection of additional blood specimen/s 3. The collection of relevant past present and future clinical information from my University of Miami affiliated institution medical records laboratory tests radiology reports operative notes pathology reports treatment records and discharge summaries for medical research purposes and then store this data in the University of Miami TBCF database 4. The distribution of samples from the University of Miami TBCF to other academic institutions for the purpose of research 5. The analysis of genes and molecules (genetic research) including DNA RNA proteins and other molecules from my stored blood urine tissue body fluid samples and cell lines 6. The establishment of continuous cell lines from my stored blood urine tissue and body fluid samples 7. The distribution of samples or continuous cell lines from my stored blood urine tissue and body fluid samples to other institutions or companies for purposes of medical research 8. If participating in a clinical trial the collection and storage of additional research samples of tissues and body fluids (including blood) removed during biopsy surgical resection and treatment of my disease.";
	
	private static final String Q_1_VAL = "Subject wants to be in this research study?";
	
	private static final String Q_1_v4_1_VAL = "Specifically  by signing this informed consent form  I am agreeing to: 1. The collection and storage of samples from tissues and body fluids removed during biopsy  surgical resection and treatment of my cancer (performed as part of my routine clinical care) 2. The collection of additional blood specimen/s 3. The collection of relevant past  present and future clinical information from my University of Miami affiliated institution medical records  laboratory tests  radiology reports  operative notes  pathology reports  treatment records  and discharge summaries for medical research purposes and then store this data in the University of Miami BSSR database 4. The distribution of samples from the University of Miami BSSR to other academic institutions for the purpose of research 5. The analysis of genes and molecules (genetic research) including DNA  RNA  proteins and other molecules from my stored blood  urine  tissue  body fluid samples and cell lines 6. The establishment of continuous cell lines from my stored blood  urine  tissue and body fluid samples 7. The distribution of samples or continuous cell lines from my stored blood  urine  tissue and body fluid samples to other institutions or companies for purposes of medical research 8. If participating in a clinical trial  the collection and storage of additional research samples of tissues and body fluids (including blood) removed during biopsy  surgical resection and treatment of my disease.";
	
	private static final String Q_1_v4_2_VAL = "Specifically  by signing this informed consent form  I am agreeing to: 1. The collection and storage of samples from tissues and body fluids removed during biopsy  surgical resection and treatment of my childâ??s cancer (performed as part of their routine clinical care) 2. The collection of additional blood specimen/s3. The collection of relevant past  present and future clinical information from their University of Miami affiliated institution medical records  laboratory tests  radiology reports  operative notes  pathology reports  treatment records  and discharge summaries for medical research purposes and then store this data in the University of Miami BSSR database 4. The distribution of samples from the University of Miami BSSR to other academic institutions for the purpose of research 5. The analysis of genes and molecules (genetic research) including DNA  RNA proteins and other molecules from my childâ??s stored blood  urine  tissue  body fluid samples and cell lines 6. The establishment of continuous cell lines from their stored blood  urine  tissue and body fluid samples 7. The distribution of samples or continuous cell lines from their stored blood  urine  tissue and body fluid samples to other institutions or companies for purposes of medical research 8. If participating in a clinical trial  the collection and storage of additional research samples of tissues and body fluids (including blood) removed during biopsy  surgical resection and treatment of my disease as discussed with the trial physician.";
	
	private static final String QUESTION = "DE_AT_5378";
	
	private static final String RESPONSE = "DE_AT_5376";
	
	private static final String QUES_NUM = "DE_AT_5377";
	
	private static final String[] questionSeq = new String[] {
			Q_1_v2, Q_1_v4_1,
			Q_1_v4_2, Q_1, Q_1A,
			Q_1B, Q_2, Q_3
			};
	
	private Map<String,String> questionToResponse = new HashMap<>();
	
	private String question;
	
	private String response;
	
	private String questionNumber;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	public QuestionDetail() {};
	
	public void extractValuesFrom(ResultSet rs) throws SQLException {
		this.setQuestion(rs.getString(QUESTION));
		this.setQuestionNumber(rs.getString(QUES_NUM));
		this.setResponse(rs.getString(RESPONSE));
	}
	
	public void addResponse() {
		String qNum = getQuestionNumber();
		String ques = getQuestion();
		String resp = getResponse();
		
		questionToResponse.put(getUpdatedQuestionNum(qNum, ques), resp);
	}
	
	public List<String> getValues() {
		List<String> values = new ArrayList<>();
		
		for (String ques : questionSeq) {
			values.add("");
			values.add(questionToResponse.get(ques));
		}
		
		return values;
	}

	private String getUpdatedQuestionNum(String qNum, String question) {
		switch (qNum) {
			case "1a": return Q_1A;
			
			case "1b": return Q_1B;
			
			case "2": return Q_2;
			
			case "3": return Q_3;
			
			default: 
				switch(question) {
					case Q_1_VAL: return Q_1;
					
					case Q_1_v2_VAL: return Q_1_v2;
					
					case Q_1_v4_1_VAL: return Q_1_v4_1;
					
					case Q_1_v4_2_VAL: return Q_1_v4_2;
					
					default: 
						System.out.println("Question did not found: "); // For debugging purpose
						System.out.println(question);
						return null;
				}
		}
	}
	
	public final static String QUERY = 
			"select " + 
			"  q.DE_AT_5376, q.DE_AT_5377, q.DE_AT_5378 " + 
			"from " + 
			"  TMP_DE_E_5374 q " + 
			"where " + 
			"  q.de_e_t_5391 = ? ";
}
