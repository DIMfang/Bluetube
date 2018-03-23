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
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import backend.database.UserQueries;


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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserQueries queries = new UserQueries();
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject(), params = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONObject userData = new JSONObject();
		HttpSession session = request.getSession();
		
		if(session.isNew()) {
			try {
				userData = queries.getUserData(params);
				if(userData.length() > 0) {
					// Sucess
					message.put("status", 200).put("description", "Sucess");
					storeValue(session, userData);
				} else {
					// Invalid username or password
					message.put("status", 409).put("description", "Invalid username or password");
					session.invalidate();
				}
			} catch(SQLException e) {
				message.put("status", 503).put("description", "Unknown problem, try again");
				e.printStackTrace();
				session.invalidate();
			} finally {
				queries.closeResources();
			}
			
		} else {
			// Acess denied
			message.put("status", 403).put("description", "Access denied");
		}
		out.println(message.toString());
	}	
	
	private void storeValue(HttpSession session, JSONObject object) {
		if(object == null) {
			session.setAttribute("session", "");	
		} else {
			session.setAttribute("session", object);
		}
	}
	
}
