package backend.users.actions;

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
import backend.database.MediaQueries;

/**
 * Servlet implementation class Like
 */
@WebServlet("/Like")
public class Like extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Like() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		out.print(userData.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		MediaQueries mq = new MediaQueries();
		HttpSession session = request.getSession();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		JSONObject params = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));		
		int id = Integer.parseInt(params.getString("media_id"));
		if(!session.isNew()) {
			try {
				mq.likeVideo(userData.getInt("id_user"), id);
				message.put("status", 200).put("description", "You have liked this video");
			} catch(SQLException E) {
				E.printStackTrace();
				message.put("status", 403).put("description", "This user already likes this video");
			} catch(Exception e) {
				e.printStackTrace();
				message.put("status", 403).put("description", "Like could not be processed");
			}			
		} else {
			message.put("status", 403).put("description", "session not started");
			session.invalidate();
		}
		out.println(message);
	}

}
