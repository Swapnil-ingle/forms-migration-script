package com.krishagni.form.merger.db.impl;

public class Attribute {
	private String name;

	private String caption;

	private String column;

	private Object value;
	
	private AttrFile file;

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
		// If file is null set value and return
		if (getFile() == null) {
			this.value = value;
			return;
		}

		// Make File
		getFile().setData(value);
		String fileName = getFile().createFile();
		this.value = fileName;
	}
	
	public AttrFile getFile() {
		return file;
	}

	public void setFile(AttrFile file) {
		this.file = file;
	}

	public String getValueAsStr() {
		return String.valueOf(getValue());
	}
	
	public static Attribute copy(Attribute old) {
		Attribute newInstance = new Attribute();
		
		newInstance.setCaption(old.getCaption());
		newInstance.setColumn(old.getColumn());
		newInstance.setName(old.getName());
		newInstance.setValue(old.getValue());
		newInstance.setFile(old.getFile());
		
		return newInstance;
	}

	public void clearValue() {
		this.value = null;
	}

	@Override
	public String toString() {
		return "DbTableAttribute [name=" + name + ", caption=" + caption + ", column=" + column + ", value=" + value
				+ "]";
	}
}
