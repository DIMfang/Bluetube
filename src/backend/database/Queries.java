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
	private ResultSetMetaData rsmd;
	private Props props; 
	
	public Queries() {
		this.con = DBConnection.getConnection();
		this.props = Props.getInstance();
	}
	
	private void executeQuery(String query, Object... values) throws SQLException {
		this.pst = this.con.prepareStatement(this.props.getQuery(query));
		for(int i = 0; i < values.length; i++) {
			this.pst.setObject(i + 1, values[i]);
		}
		this.rs = this.pst.executeQuery();
	}
	
	// Function to verify if the username is already is use
	public boolean checkUsername(String username) throws SQLException {
		executeQuery("findUsername", username);
		return this.rs.next();
	}
	
	// Function to verify if the email is already registered
	public boolean checkEmail(String email) throws SQLException{
		executeQuery("findEmail", email);
		return this.rs.next();
	}
	
	// Function to verify is the user exist, and return he's data
	public JSONObject getUserData(JSONObject user) throws SQLException {	
		String encryptedPass = Encrypt.HashPassword(user.getString("password"));
		JSONObject userData = new JSONObject();
		executeQuery("checkLogin", user.get("username"), encryptedPass);
		if(this.rs.next()) {
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				userData.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
		}
		return userData;
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

	public void newVideo() {
		
	}

}
