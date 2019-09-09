package com.krishagni.form.merger.db;

import java.sql.SQLException;
import java.util.List;

import com.krishagni.form.merger.db.impl.Table;

public interface TableSource {
	public List<Table> getRows() throws SQLException;

	boolean hasRows() throws SQLException;

	List<String> getHeaders();
}
