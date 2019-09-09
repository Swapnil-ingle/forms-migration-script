package com.krishagni.form.merger.db.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Table {
	private String name;
	
	private List<TableAttribute> attrs = new ArrayList<>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TableAttribute> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<TableAttribute> attrs) {
		this.attrs = attrs;
	}
	
	public static Table copy(Table old) {
		List<TableAttribute> newAttrs = old.getAttrs().stream()
			.map(TableAttribute::copy)
			.collect(Collectors.toList());
		
		Table newInstance = new Table();
		
		newInstance.setAttrs(newAttrs);
		newInstance.setName(old.getName());
		
		return newInstance;
	}

	public String getRowCountQry() {
		return "select count(*) from " + getName();
	}
	
	public String getQryWithLimits(int currRowIdx, int batchSize) {
		String limit = String.valueOf(currRowIdx + batchSize);
		String offset = String.valueOf(currRowIdx <= 0 ? 0 : currRowIdx - batchSize);
		
		return getQry() + " limit " + limit + " offset " + offset;
	}
	
	public String getQry() {
		return "select " + getColumns()  + " from " + getName();
	}
	
	public void clearAttrValues() {
		attrs.forEach(attr -> {
			attr.setValue(null);
		});
	}
	
	public List<String> getHeaders() {
		return getAttrs().stream()
				.map(TableAttribute::getName)
				.collect(Collectors.toList());
	}
	
	public String[] getCsv() {
		return getAttrs().stream()
				.map(TableAttribute::getValueAsStr)
				.collect(Collectors.toList())
				.toArray(new String[0]);
	}
	
	@Override
	public String toString() {
		return "DbTableRowImpl [name=" + name + ", attrs=" + attrs + "]";
	}
	
	private String getColumns() {
		return getAttrs().stream()
				.map(TableAttribute::getColumn)
				.collect(Collectors.joining(","));
	}
}
