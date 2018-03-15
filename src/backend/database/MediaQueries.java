package backend.database;

import java.sql.Connection;
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
		this.con = DBConnection.getConnection();
		this.props = Props.getInstance();
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
