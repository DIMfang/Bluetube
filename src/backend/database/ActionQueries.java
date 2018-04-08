package backend.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;


public class ActionQueries extends ExecuteSQL {
	
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public ActionQueries() {		
		super();
	}	
	// COMMENT'S QUERIES
	public boolean newComment(JSONObject commentData) throws SQLException {
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
		int result = executeUpdate("newComment", commentData.getInt("media_id"), commentData.getInt("id_user"), time, commentData.getString("comment_text"));
		return (result == 1) ? true : false;
	}
	public JSONArray getCommentList(int mediaId) throws SQLException {
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
	// LIKE/DISLIKE'S QUERIES
	public boolean likeVideo(int userId, int mediaId) throws SQLException {
		int result = executeUpdate("videoLike", userId, mediaId);
		return (result == 1) ? true : false;
	}	
	public boolean dislikeVideo(int userId, int mediaId) throws SQLException {
		int result = executeUpdate("videoDislike", userId, mediaId);
		return (result == 1) ? true : false;
	}
	public boolean changeState(boolean likeState, int userId, int mediaId) throws SQLException {
		int result = executeUpdate("changeState", likeState, userId, mediaId);
		return (result == 1) ? true : false;
	}
	public Boolean isLike(int userId, int mediaId) throws SQLException {
		rs = executeQuery("isLike", userId, mediaId);
		if(rs.next()) {
			return rs.getBoolean("like_state");
		}
		return null;
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
