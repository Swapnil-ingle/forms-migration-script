package com.krishagni.form.merger.db.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.krishagni.form.merger.db.TableSource;
import com.krishagni.form.merger.util.MergeProperties;

public class TableSourceImpl implements TableSource {
	private static final Integer BATCH_SIZE = 25;

	private Connection conn;
	
	private Table table;
	
	private Integer rowIdx = 0;
	
	private Integer totalRows;
	
	private PreparedStatement prepSql;

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

	public PreparedStatement getPrepSql() {
		return prepSql;
	}

	public void setPrepSql(PreparedStatement prepSql) {
		this.prepSql = prepSql;
	}

	public TableSourceImpl() throws SQLException {
		MergeProperties props = MergeProperties.getInstance();
		this.setConn(DriverManager.getConnection(
				props.getDbUrl(),
				props.getDbUser(),
				props.getDbPwd())
				);
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
	
	private Integer getPageLimit() {
		return getRowIdx() + BATCH_SIZE;
	}
	
	private PreparedStatement makePrepSql(Integer rowIdx, Integer pageLimit) throws Exception {
		if (getPrepSql() == null) {
			setPrepSql(getConn().prepareStatement(table.getQry()));
		}
		
		String dbType = MergeProperties.getInstance().getDbType();
		PreparedStatement prepSql = getPrepSql();
		
		if (dbType.equals("mysql")) {
			prepSql.setInt(1, getPageLimit());
			prepSql.setInt(2, getRowIdx());
		} else if (dbType.equals("oracle")) {
			prepSql.setInt(1, getRowIdx() + 1);
			prepSql.setInt(2, getPageLimit());
		}
		
		return prepSql;
	}
	
	@Override
	public List<String> getHeaders() {
		return table.getHeaders();
	}

	@Override
	public List<Table> getRows() throws Exception {
		PreparedStatement prepStmt = makePrepSql(getRowIdx(), getPageLimit());
		ResultSet rs = prepStmt.executeQuery();
		List<Table> tableRows = new ArrayList<>();
		
		while (rs.next()) {
			for (Attribute attr : getTable().getAttrs()) {
				Object value = rs.getObject(attr.getColumn());
				attr.setValue(value);
			}
			
			tableRows.add(Table.copy(getTable()));
			setRowIdx(getRowIdx() + 1);
			getTable().clearAttrValues();
		}
		
		rs.close();
		
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
