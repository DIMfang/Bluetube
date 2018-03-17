package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;


import org.json.JSONObject;

import backend.util.properties.Props;

public class MediaQueries {
	
	private Connection con;
	private PreparedStatement pst;
	@SuppressWarnings("unused")
	private ResultSet rs;
	@SuppressWarnings("unused")
	private ResultSetMetaData rsmd;
	private Props props;
	
	public MediaQueries() {
		this.props = Props.getInstance();
		try {
			Class.forName(this.props.getDB("driver"));
			this.con = DriverManager.getConnection(props.getDB("url"), props.getDB("user"), props.getDB("password"));
		} catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void executeQuery(String query, Object... values) throws SQLException {
		this.pst = this.con.prepareStatement(this.props.getQuery(query));
		for(int i = 0; i < values.length; i++) {
			this.pst.setObject(i + 1, values[i]);
		}
		this.rs = this.pst.executeQuery();
	}
		
	// Function to check if a video exists in the DB, if it does, returns it's URL.
	public boolean checkVideo(String name) throws SQLException {
		executeQuery("searchMedia", name);
		return this.rs.next();
	}
	
	//Function to retrieve an existing videos' URL.
	public String getVideo(String name) throws SQLException{
		executeQuery("searchMedia", name);
		this.rs.next();
		return rs.getString("media_filename");
	}
	
	public boolean newVideo(JSONObject mediaData) throws SQLException {
		Calendar c = Calendar.getInstance();
		java.util.Date currentDate = c.getTime();
		java.sql.Date date = new java.sql.Date(currentDate.getTime());
		this.pst = con.prepareStatement(this.props.getQuery("insertMedia"));
		this.pst.setInt(1, mediaData.getInt("id_user"));
		this.pst.setString(2, mediaData.getString("media_url"));
		this.pst.setString(3, mediaData.getString("media_name"));
		this.pst.setString(4, mediaData.getString("media_filename"));
		this.pst.setString(5, mediaData.getString("media_des"));
		this.pst.setDate(6, date);
		int result = this.pst.executeUpdate();
		return (result == 1) ? true : false;
	}
	
	public void closeResources() {
		try {
			if(this.con != null )
				this.con.close();
			if(this.rs != null)
				this.rs.close();
			if(this.pst != null)
				this.pst.close();
		}catch(SQLException e) {
			System.out.println("Problema al cerrar los recursos");
			e.printStackTrace();
		}
	}

}
