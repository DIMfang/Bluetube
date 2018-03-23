package backend.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import backend.database.UserQueries;

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
		
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject(), params = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		UserQueries queries = new UserQueries(); 
		
		try {
			if (queries.checkEmail(params.getString("email"))) {
				// Cambiar status de username o del email
				message.put("status", 409).put("description", "The email is already registered");				
			} else if (queries.checkUsername(params.getString("username"))) {
				// Cambiar status de username o del email
				message.put("status", 409).put("description", "The username is already in use");
			} else {
				queries.newUser(params);
				message.put("status", 200).put("description", "Congratulations!");				
			}	
		} catch(SQLException e) {
			message.put("status", 503).put("description", "Unknown problem, try again");
			e.printStackTrace();
		} finally {
			queries.closeResources();
		}
		
		out.println(message.toString());
	}


}


















