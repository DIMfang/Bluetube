package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import backend.util.properties.Props;

public class ActionQueries {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public ActionQueries() {		
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
	private int executeUpdate(String query, Object... values) throws SQLException {
		this.pst = this.con.prepareStatement(Props.getQuery(query));
		for(int i = 0; i < values.length; i++) {
			this.pst.setObject(i+1, values[i]);
		}
		return this.pst.executeUpdate();
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
		executeQuery("searchComments", mediaId); 
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
		executeQuery("isLike", userId, mediaId);
		if(rs.next()) {
			return rs.getBoolean("like_state");
		}
		return null;
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
