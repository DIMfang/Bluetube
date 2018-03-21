package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;

import backend.util.properties.Props;
import backend.util.security.Encrypt;

public class UserQueries {
	
	private Connection con; 
	private PreparedStatement pst; 
	private ResultSet rs; 
	private ResultSetMetaData rsmd;
	
	public UserQueries() {		
		try {
			Class.forName(Props.getDB("driver"));
			this.con = DriverManager.getConnection(Props.getDB("url"), Props.getDB("user"), Props.getDB("password"));
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void executeQuery(String query, Object... values) throws SQLException {
		this.pst = this.con.prepareStatement(Props.getQuery(query));
		for(int i = 0; i < values.length; i++) {
			this.pst.setObject(i + 1, values[i]);
		}
		this.rs = this.pst.executeQuery();
	}
	
	private JSONObject getData() throws SQLException {
		JSONObject userData = new JSONObject();
		if(this.rs.next()) {
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
		}
		return userData;
	}
	
	// Function to verify if the username is already is use
	public boolean checkUsername(String username) throws SQLException {
		executeQuery("findUsername", username);
		return this.rs.next();
	}

	
	// Function to verify if the email is already registered
	public boolean checkEmail(String email) throws SQLException {
		executeQuery("findEmail", email);
		return this.rs.next();
	}
	
	// Function to verify is the user exist, and return he's data
	public JSONObject getUserData(JSONObject user) throws SQLException {	
		String encryptedPass = Encrypt.HashPassword(user.getString("password"));
		JSONObject userData = new JSONObject();
		executeQuery("checkLogin", user.get("username"), encryptedPass);
		userData = getData();
		return userData;
	}
	
	// Function to add a new user
	public boolean newUser(JSONObject userData) throws SQLException {
		String encryptedPass = Encrypt.HashPassword(userData.getString("password"));
		this.pst = this.con.prepareStatement(Props.getQuery("insertUser"));
		this.pst.setString(1, userData.getString("name"));
		this.pst.setString(2, userData.getString("lastname"));
		this.pst.setString(3, userData.getString("username"));
		this.pst.setString(4, encryptedPass);
		this.pst.setString(5, userData.getString("email"));
		int result = this.pst.executeUpdate();
		return (result == 1) ? true : false;
	}
	
	public void closeResources() {
		try {
			if(this.con != null)
				this.con.close();
			if(this.rs != null)
				this.rs.close();
			if(this.pst != null)
				this.pst.close();
		} catch(SQLException e) {
			System.out.println("Problema al cerrar los recursos");
			e.printStackTrace();
		}
	}
}
