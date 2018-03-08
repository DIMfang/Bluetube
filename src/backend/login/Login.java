package backend.login;

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

import backend.database.Queries;


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
	
		Queries queries = new Queries();
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject(), params = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		HttpSession session = request.getSession();
		
		if(session.isNew()) {
			try {
				if(queries.checkLogin(params.getString("username"), params.getString("password"))) {
					message.put("status", 13).put("message", "Sucess");
				} else {
					message.put("status", 14).put("message", "invalid username or password");
					session.invalidate();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} else {
			message.put("status", 2).put("message", "You are already log in");
		}
		
		out.println(message.toString());
	}
	
	
	@SuppressWarnings("unused")
	private void storeValue(String value, HttpSession session) {
		if(value==null) {
			session.setAttribute("session", "");
		} else {
			session.setAttribute("session", value);
		}
	}
	
}
