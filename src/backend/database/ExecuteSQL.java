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
	protected void closeMainResource() throws SQLException {
		if(this != null) 
			this.con.close();
		if(this.pst != null) 
			this.pst.close();
	}
	
}
