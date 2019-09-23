package com.krishagni.form.merger.db.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.krishagni.form.merger.util.MergeProperties;

public class Table {
	private String name;
	
	private List<Attribute> attrs = new ArrayList<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Attribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<Attribute> attrs) {
		this.attrs = attrs;
	}
	
	public static Table copy(Table old) {
		List<Attribute> newAttrs = old.getAttrs().stream()
			.map(Attribute::copy)
			.collect(Collectors.toList());
		
		Table newInstance = new Table();
		
		newInstance.setAttrs(newAttrs);
		newInstance.setName(old.getName());
		
		return newInstance;
	}

	public String getRowCountQry() {
		return String.format("select count(*) from %s", getName());
	}
	
	public String getQry() throws Exception {
		String db = MergeProperties.getInstance().getDbType();

		if (db.equals("mysql")) {
			return getMySQLQry();
		} else if (db.equals("oracle")) {
			return getOracleQry();
		}

		System.err.println("Invalid DB type");
		throw new InvalidParameterException("Invalid DB type");
	}

	private String getMySQLQry() {
		return String.format(
				"select %s " +
				"from %s " +
				"limit ? offset ?", getColumns(), getName());
	}

	private String getOracleQry() {
		return String.format(
				"select c.* " +
				"from (select %s, ROWNUM as rnum from %s) c " +
				"where c.rnum between ? and ?", getColumns(), getName());
	}

	public void clearAttrValues() {
		attrs.forEach(attr -> {
			attr.clearValue();
		});
	}
	
	public List<String> getHeaders() {
		return getAttrs().stream()
				.map(Attribute::getName)
				.collect(Collectors.toList());
	}
	
	public String[] getCsv() {
		return getAttrs().stream()
				.map(Attribute::getValueAsStr)
				.collect(Collectors.toList())
				.toArray(new String[0]);
	}
	
	@Override
	public String toString() {
		return "DbTableRowImpl [name=" + name + ", attrs=" + attrs + "]";
	}
	
	public String getColumns() {
		return getAttrs().stream()
				.map(Attribute::getColumn)
				.collect(Collectors.joining(","));
	}
}
