package backend.login;

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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection connex = DBConnection.getConnection();
		Props propiedades = Props.getInstance();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		JSONObject json = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		try {			
			stmt = connex.prepareStatement(propiedades.getProp("checkLogin"));
			stmt.setString(1, json.getString("username"));
			stmt.setString(2, json.getString("password"));
			rs = stmt.executeQuery();			
			if(!rs.next()) {
				System.out.println("The users does not exist or the password is incorrect");
			}else {
				System.out.println("Welcome");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
