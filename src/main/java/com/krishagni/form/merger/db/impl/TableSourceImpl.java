package com.krishagni.form.merger.db.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.krishagni.form.merger.db.TableSource;
import com.krishagni.form.merger.util.MergeProperties;

public class TableSourceImpl implements TableSource {
	private Connection conn;
	
	private Table table;
	
	private Integer rowIdx = 0;
	
	private Integer totalRows;
	
	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public Integer getRowIdx() {
		return rowIdx;
	}

	public void setRowIdx(Integer rowIdx) {
		this.rowIdx = rowIdx;
	}
	
	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public TableSourceImpl() throws SQLException {
		MergeProperties props = MergeProperties.getInstance();
		this.setConn(DriverManager.getConnection(props.getJdbcUrl(), props.getJdbcUser(), props.getJdbcPwd()));
		System.out.println("INFO: Connected to Database!");
	}

	private Integer fetchTotalRows() throws SQLException {
		Statement sql = getConn().createStatement();
		ResultSet rs = sql.executeQuery(table.getRowCountQry());
		
		rs.next();
		Integer totalRows = rs.getInt(1);
		
		rs.close();
		sql.close();

		return totalRows;
	}
	
	@Override
	public List<String> getHeaders() {
		return table.getHeaders();
	}

	@Override
	public List<Table> getRows() throws SQLException {
		Statement sql = getConn().createStatement();
		Integer batchSize = 25;
		
		ResultSet rs = sql.executeQuery(table.getQryWithLimits(getRowIdx(), batchSize));
		List<Table> tableRows = new ArrayList<>();
		
		while (rs.next()) {
			for (Attribute attr : getTable().getAttrs()) {
				Object value = rs.getObject(attr.getColumn());
				attr.setValue(value);
				setRowIdx(getRowIdx() + 1);
			}
			
			tableRows.add(Table.copy(getTable()));
			getTable().clearAttrValues();
		}
		
		rs.close();
		sql.close();
		
		return tableRows;
	}
	
	@Override
	public boolean hasRows() throws SQLException {
		if (getTotalRows() == null) {
			setTotalRows(fetchTotalRows());
		}
		
		return getRowIdx() < getTotalRows();
	}

	@Override
	public String toString() {
		return "DbTableSourceImpl [tableRow=" + table + "]";
	}
}
