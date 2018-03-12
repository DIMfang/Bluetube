package backend.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
	
	@SuppressWarnings("unused")
	private void executeQuery(String query, Object... values) throws SQLException {
		this.pst = this.con.prepareStatement(this.props.getQuery(query));
		for(int i = 0; i < values.length; i++) {
			this.pst.setObject(i + 1, values[i]);
		}
		this.rs = this.pst.executeQuery();
	}
	
	public boolean newVideo(JSONObject mediaData) throws SQLException {
		this.pst = con.prepareStatement(this.props.getQuery("insertMedia"));
		this.pst.setString(1, mediaData.getString("id_user"));
		this.pst.setString(2, mediaData.getString("media_url"));
		this.pst.setString(3, mediaData.getString("media_name"));
		this.pst.setString(4, mediaData.getString("media_filename"));
		this.pst.setString(5, mediaData.getString("media_des"));
		this.pst.setString(6, mediaData.getString("created_at"));
		int result = this.pst.executeUpdate();
		return (result == 1) ? true : false;
	}

}
