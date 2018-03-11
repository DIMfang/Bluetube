package backend.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;

import backend.util.properties.Props;
import backend.util.security.Encrypt;

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
	public boolean checkUsername(String username) throws SQLException {
		this.pst = this.con.prepareStatement(this.props.getQuery("findUsername"));
		this.pst.setString(1, username);
		this.rs = this.pst.executeQuery();
		return this.rs.next();
		
	}
	
	// Function to verify if the email is already registered
	public boolean checkEmail(String email) throws SQLException{
		this.pst = this.con.prepareStatement(this.props.getQuery("findEmail"));
		this.pst.setString(1, email);
		this.rs = this.pst.executeQuery();
		return this.rs.next();
	}
	
	// Function to verify is the user exist
	public boolean checkLogin(String username, String password) throws SQLException {	
		String encryptedPass = Encrypt.HashPassword(password);
		this.pst = this.con.prepareStatement(this.props.getQuery("checkLogin"));
		this.pst.setString(1, username);
		this.pst.setString(2, encryptedPass);
		this.rs = this.pst.executeQuery();
		return this.rs.next();
	}
	
	// Function to add a new user
	public boolean newUser(JSONObject userData) throws SQLException {
		// Como se, si se encripto bien?
		String encryptedPass = Encrypt.HashPassword(userData.getString("password"));
		pst = con.prepareStatement(props.getQuery("insertUser"));
		pst.setString(1, userData.getString("name"));
		pst.setString(2, userData.getString("lastname"));
		pst.setString(3, userData.getString("username"));
		pst.setString(4, encryptedPass);
		pst.setString(5, userData.getString("email"));
		int result = pst.executeUpdate();
		return (result == 1) ? true : false;
	}

}
