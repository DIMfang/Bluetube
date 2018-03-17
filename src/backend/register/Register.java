package backend.register;

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
				System.out.println("The email is already registered");
				message.put("status", 409).put("description", "The email is already registered");
				
			} else if (queries.checkUsername(params.getString("username"))) {
				System.out.println("The username is already in use");
				message.put("status", 409).put("description", "The username is already in use");
			} else {		
				if(queries.newUser(params)) {
					System.out.println("User successfully added");
					message.put("status", 200).put("description", "Congratulations!");
				} else {
					System.err.println("Unknow problem");
					message.put("status", 503).put("description", "Unknown problem, try again");
				}
			}	
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		out.println(message.toString());
	}


}


















