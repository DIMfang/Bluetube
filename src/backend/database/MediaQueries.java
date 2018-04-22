package backend.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import backend.util.properties.Props;


public class MediaQueries extends ExecuteSQL {
	
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public MediaQueries() {		
		super();
	}
	
	public Boolean isUserMedia(int userId, int mediaId) throws SQLException {
		this.rs = executeQuery("isUserMedia", userId, mediaId);
		return this.rs.next();
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
	
	public JSONArray getUserVideoList(int userId) throws SQLException {
		JSONArray mediaData = new JSONArray();		
		this.rs = executeQuery("searchUserVideos", userId);
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
	
	public JSONArray getAllUserVideoList() throws SQLException {
		JSONArray mediaData = new JSONArray();		
		this.rs = executeQuery("searchAllUserVideos");
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
		JSONObject mediaData = new JSONObject();
		this.rs = executeQuery("mediaData", mediaId);
		if(this.rs.next()) {
			this.rsmd = this.rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				mediaData.put(this.rsmd.getColumnLabel(i), this.rs.getObject(i));
			}
			return mediaData;
		}
		return null;
	}
	public JSONArray getMediaComments(int mediaId) throws SQLException {
		JSONArray commentData = new JSONArray();	
		rs = executeQuery("searchComments", mediaId); 
		while(this.rs.next()) {
			JSONObject row = new JSONObject();
			this.rsmd = this.rs.getMetaData();
			for(int i = 1; i <= this.rsmd.getColumnCount(); i++) {
				row.put(this.rsmd.getColumnLabel(i), this.rs.getObject(i));
			}
			commentData.put(row);			
		}
		return commentData;
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
	public String deleteMedia(int mediaId) throws SQLException {
		this.rs = executeQuery("mediaURL", mediaId);
		if(this.rs.next()) {
			PreparedStatement deleteComments = null, deleteLikes = null, deleteMedia = null;
			Connection con = this.getConnection();
			try {
				con.setAutoCommit(false);
				deleteComments = con.prepareStatement(Props.getQuery("deleteComments"));
				deleteLikes = con.prepareStatement(Props.getQuery("deleteLikes"));
				deleteMedia = con.prepareStatement(Props.getQuery("deleteMedia"));
				deleteComments.setInt(1, mediaId);
				deleteComments.executeUpdate();
				deleteLikes.setInt(1, mediaId);
				deleteLikes.executeUpdate();
				deleteMedia.setInt(1, mediaId);
				deleteMedia.executeUpdate();
				con.commit();
			} finally {		
				con.setAutoCommit(true);			
				if(deleteComments != null) 
					deleteComments.close();
				if(deleteLikes != null) 
					deleteLikes.close();
				if(deleteComments != null) 
					deleteLikes.close();								
			}
		} else {
			return null; 
		}
		return this.rs.getString("media_url");
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
