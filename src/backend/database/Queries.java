package backend.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import backend.properties.Props;

public class Queries {
	
	private Connection con; 
	private PreparedStatement pst; 
	private ResultSet rs; 
	@SuppressWarnings("unused")
	private ResultSetMetaData rsmd;
	private Props props; 
	
	public Queries() {
		this.con = DBConnection.getConnection();
		this.props = Props.getInstance();
	}
	
	
	public boolean checkUsername(String username) {
		try {
			String query = this.props.getProp("findUsername");
			this.pst = this.con.prepareStatement(query);
			this.pst.setString(1, username);
			this.rs = this.pst.executeQuery();
			return this.rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkEmail(String email) {
		try {
			String query = this.props.getProp("findEmail");
			this.pst = this.con.prepareStatement(query);
			this.pst.setString(1, email);
			this.rs = this.pst.executeQuery();
			return this.rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean loginCheck(String username, String password) {
		try {
			String query = this.props.getProp("loginCheck");
			this.pst = this.con.prepareStatement(query);
			this.pst.setString(1, username);
			this.pst.setString(2, password);
			this.rs = this.pst.executeQuery();
			return this.rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
