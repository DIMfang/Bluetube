package backend.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;


public class MediaQueries extends ExecuteSQL {
	
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public MediaQueries() {		
		super();
	}
	
	public JSONArray getVideoList(String mediaName) throws SQLException {
		JSONArray mediaData = new JSONArray();		
		this.rs = executeQuery("searchVideos", "%" + mediaName + "%");
		while(this.rs.next()) {
			JSONObject row = new JSONObject();
			this.rsmd = this.rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				row.put(this.rsmd.getColumnLabel(i), this.rs.getObject(i));
			}
			mediaData.put(row);
		}
		return mediaData;
	}
	
	// Function to retrieve an existing videos' URL and file name.
	public JSONObject getMedia(int mediaId) throws SQLException {
		JSONObject mediaData = new JSONObject();
		this.rs = executeQuery("downloadMedia", mediaId);
		if(this.rs.next()) {
			return mediaData.put("url", rs.getString("media_url")).put("fileName", rs.getString("media_filename"));
		}
		return null;
	}
	
	// Function to retrieve how many likes and dislikes a video has
	public JSONObject getMediaData(int mediaId) throws SQLException {
		JSONObject likes = new JSONObject();
		this.rs = executeQuery("mediaData", mediaId, mediaId);
		if(this.rs.next()) {
			return likes.put("likes", rs.getInt("likes")).put("dislikes", rs.getInt("dislikes")).put("views", rs.getInt("views"));
		}
		return null;
	}
	
	public boolean newVideo(JSONObject mediaData) throws SQLException {
		Calendar c = Calendar.getInstance();
		java.util.Date currentDate = c.getTime();
		java.sql.Date date = new java.sql.Date(currentDate.getTime());
		int result = executeUpdate("insertMedia", mediaData.getInt("id_user"), mediaData.getString("media_url"), mediaData.getString("media_name"), 
				mediaData.getString("media_filename"), mediaData.getString("media_des"), date);
		return (result == 1) ? true : false;
	}
	public boolean addView(int mediaId) throws SQLException {
		int result = executeUpdate("addView", mediaId);
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
