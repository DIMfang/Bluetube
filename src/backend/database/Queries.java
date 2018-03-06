package backend.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;

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
	
	// Function to verify if the username is already is use
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
	
	// Function to verify if the email is already registered
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
	
	// Function to verify is the user exist
	public boolean checkLogin(String username, String password) throws SQLException {	
		String query = this.props.getProp("checkLogin");
		this.pst = this.con.prepareStatement(query);
		this.pst.setString(1, username);
		this.pst.setString(2, password);
		this.rs = this.pst.executeQuery();
		return this.rs.next();
	}
	
	// Function to add a new user
	public boolean newUser(JSONObject userData) {
		try {	
			pst = con.prepareStatement(props.getProp("insertUser"));
			pst.setString(1, userData.getString("name"));
			pst.setString(2, userData.getString("lastname"));
			pst.setString(3, userData.getString("username"));
			pst.setString(4, userData.getString("password"));
			pst.setString(5, userData.getString("email"));
			int result = pst.executeUpdate();
			if(result == 1) {
				return true;
			}		
		} catch (SQLException e) {
			e.printStackTrace(); // Hay que ver como manejar el error
		}
		return false;
	}

}
