package backend.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;

import backend.util.security.Encrypt;

public class UserQueries extends ExecuteSQL {

	private ResultSet rs; 
	private ResultSetMetaData rsmd;
	
	public UserQueries() {		
		super();
	}
	
	private JSONObject getData() throws SQLException {
		JSONObject userData = new JSONObject();
		if(this.rs.next()) {
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
		} else {
			return null;
		}
		return userData;
	}
	
	// Function to verify if the username is already is use
	public boolean checkUsername(String username) throws SQLException {
		this.rs = executeQuery("findUsername", username);
		return this.rs.next();
	}
	
	// Function to verify if the email is already registered
	public boolean checkEmail(String email) throws SQLException {
		this.rs = executeQuery("findEmail", email);
		return this.rs.next();
	}
	
	// Function to verify is the user exist, and return he's data
	public JSONObject getUserData(JSONObject user) throws SQLException {	
		String encryptedPass = Encrypt.HashPassword(user.getString("password"));
		this.rs = executeQuery("checkLogin", user.get("username"), encryptedPass);		
		return this.getData();
	}
	
	// Function to add a new user
	public boolean newUser(JSONObject userData) throws SQLException {
		String encryptedPass = Encrypt.HashPassword(userData.getString("password"));
		int result = executeUpdate("insertUser", userData.getString("name"), userData.getString("lastname"),
				userData.getString("username"), encryptedPass, userData.getString("email"));
		return (result == 1) ? true : false;
	}
	
	public void closeResources() {
		try {
			closeMainResource();
			if(this.rs != null)
				this.rs.close();
		} catch (SQLException e) {
			System.out.println("Problema al cerrar los recursos");
			e.printStackTrace();
		}
	}
}
