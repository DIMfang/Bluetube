package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import backend.util.properties.Props;

public class MediaQueries {
	
	private Connection con;
	private PreparedStatement pst;
	@SuppressWarnings("unused")
	private ResultSet rs;
	@SuppressWarnings("unused")
	private ResultSetMetaData rsmd;
	
	public MediaQueries() {		
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
	
	public JSONArray getVideoList(String mediaName) throws SQLException {
		JSONArray mediaData = new JSONArray();
		
		executeQuery("searchVideos", "%" + mediaName + "%");
		while(this.rs.next()) {
			JSONObject row = new JSONObject();
			this.rsmd = rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				row.put(rsmd.getColumnLabel(i), rs.getObject(i));
			}
			mediaData.put(row);
		}
		return mediaData;
	}
	
	//Function to retrieve an existing videos' URL and file name.
	public JSONObject getMedia(int id) throws SQLException {
		JSONObject mediaData = new JSONObject();
		executeQuery("downloadMedia", id);
		if(this.rs.next()) {
			return mediaData.put("url", rs.getString("media_url")).put("fileName", rs.getString("media_filename"));
		}
		return null;
	}
	
	public boolean newVideo(JSONObject mediaData) throws SQLException {
		Calendar c = Calendar.getInstance();
		java.util.Date currentDate = c.getTime();
		java.sql.Date date = new java.sql.Date(currentDate.getTime());
		this.pst = con.prepareStatement(Props.getQuery("insertMedia"));
		this.pst.setInt(1, mediaData.getInt("id_user"));
		this.pst.setString(2, mediaData.getString("media_url"));
		this.pst.setString(3, mediaData.getString("media_name"));
		this.pst.setString(4, mediaData.getString("media_filename"));
		this.pst.setString(5, mediaData.getString("media_des"));
		this.pst.setDate(6, date);
		int result = this.pst.executeUpdate();
		return (result == 1) ? true : false;
	}
	
	public boolean likeVideo(Integer id, Integer media_id) throws SQLException {
		this.pst = con.prepareStatement(Props.getQuery("videoLike"));
		this.pst.setInt(1, id);
		this.pst.setInt(2, media_id);
		int result = pst.executeUpdate();
//		int result = executeUpdate("videoLike", id, media_id);
		return (result == 1) ? true : false;
	}
	
	
	public boolean newComment(JSONObject commentData) throws SQLException {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
		this.pst = this.con.prepareStatement(Props.getQuery("newComment"));
		this.pst.setInt(1, commentData.getInt("media_id"));
		this.pst.setInt(2, commentData.getInt("id_user")); 
		this.pst.setTimestamp(3, time);
		this.pst.setString(4, commentData.getString("comment_text"));		
		int result = pst.executeUpdate();
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
		} catch (SQLException e) {
			System.out.println("Problema al cerrar los recursos");
			e.printStackTrace();
		}
	}

}
