package backend.register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import backend.database.DBConnection;
import backend.properties.Props;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = DBConnection.getConnection();
		Props props = Props.getInstance();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));

		try {
//			Class.forName(props.getProp("driver"));
			pst = con.prepareStatement(props.getProp("q1"));
			rs = pst.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("type_des"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}



















