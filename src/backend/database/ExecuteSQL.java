package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import backend.util.properties.Props;

public abstract class ExecuteSQL {
	
	private Connection con;
	private PreparedStatement pst;
	
	public ExecuteSQL() {
		try {
			Class.forName(Props.getDB("driver"));
			this.con = DriverManager.getConnection(Props.getDB("url"), Props.getDB("user"), Props.getDB("password"));
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected ResultSet executeQuery(String query, Object... values) throws SQLException {
		this.pst = this.con.prepareStatement(Props.getQuery(query));
		for(int i = 0; i < values.length; i++) {
			this.pst.setObject(i + 1, values[i]);
		}
		return this.pst.executeQuery();
	}
	protected int executeUpdate(String query, Object... values) throws SQLException {
		this.pst = this.con.prepareStatement(Props.getQuery(query));
		for(int i = 0; i < values.length; i++) {
			this.pst.setObject(i+1, values[i]);
		}
		return this.pst.executeUpdate();
	}
//	protected JSONObject getOne(ResultSet rs) throws SQLException {
//		JSONObject one = new JSONObject();
//		if(rs.next()) {
//			ResultSetMetaData rsmd = rs.getMetaData();
//			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
//				one.put(rsmd.getColumnLabel(i), rs.getObject(i));
//			}
//		} else {
//			return null;
//		}
//		return one;
//	}
//	protected JSONArray getMany(ResultSet rs) throws SQLException {		
//		JSONArray rows = new JSONArray();
//		while(rs.next()) {
//			JSONObject row = new JSONObject();
//			ResultSetMetaData rsmd = rs.getMetaData();
//			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
//				row.put(rsmd.getColumnLabel(i), rs.getObject(i));
//			}
//			rows.put(row);
//		}
//		return rows;
//	}
	// Close connection and preparedStatement
	protected void closeMainResource() throws SQLException {
		if(this != null) 
			this.con.close();
		if(this.pst != null) 
			this.pst.close();
	}
	
	// Getters
	public Connection getConnection() {
		return this.con;
	}
	public PreparedStatement getPreparedStatement() {
		return this.pst;
	}
	
}
