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

import backend.database.ActionQueries;

/**
 * Servlet implementation class Dislike
 */
@WebServlet("/Dislike")
public class Dislike extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dislike() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();		
		ActionQueries aq = new ActionQueries();
		JSONObject message = new JSONObject();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		JSONObject params = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));		
		int mediaId = Integer.parseInt(params.getString("media_id"));
		
		if(!session.isNew()) {
			int userId = userData.getInt("id_user");
			try {
				Boolean isBool = aq.isLike(userId, mediaId);
				if(isBool == null) {
					aq.dislikeVideo(userId, mediaId);	
					message.put("status", 200).put("description", "Dislike");
				} else if(isBool.equals(false)) {
					// Ya hizo un dislike
					message.put("status", 403).put("description", "This user already dislikes this video");	
				} else {
					aq.changeState(false, userId, mediaId);
					message.put("status", 200).put("description", "Changing to dislike");
				}				
			} catch(SQLException e) {
				message.put("status", 200).put("description", "Changing dislike");
				e.printStackTrace();				
			} finally {
				aq.closeResources();
			}
		} else {
			message.put("status", 403).put("description", "session not started");
			session.invalidate();
		}
		out.println(message);
	}

}
