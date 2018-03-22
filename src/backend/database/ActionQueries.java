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
