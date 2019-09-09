package com.krishagni.form.merger.db.impl;

public class TableAttribute {
	private String name;

	private String caption;

	private String column;

	private Object value;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getValueAsStr() {
		return String.valueOf(getValue());
	}
	
	public static TableAttribute copy(TableAttribute old) {
		TableAttribute newInstance = new TableAttribute();
		
		newInstance.setCaption(old.getCaption());
		newInstance.setColumn(old.getColumn());
		newInstance.setName(old.getName());
		newInstance.setValue(old.getValue());
		
		return newInstance;
	}

	@Override
	public String toString() {
		return "DbTableAttribute [name=" + name + ", caption=" + caption + ", column=" + column + ", value=" + value
				+ "]";
	}
}
