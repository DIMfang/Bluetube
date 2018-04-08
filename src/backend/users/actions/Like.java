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
		MediaQueries mq = new MediaQueries();
		JSONObject message = new JSONObject();
		int mediaId = Integer.parseInt(request.getParameter("key"));
		try {
			JSONObject likes = mq.getMediaData(mediaId);
			message.put("status", 200).put("count", likes);
		} catch(SQLException e) {
			message.put("status", 503);
			e.printStackTrace();
		} finally {
			mq.closeResources();
		}
		out.println(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();		
		ActionQueries aq = new ActionQueries();		
		JSONObject message = new JSONObject();
		JSONObject params = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));		
		int mediaId = Integer.parseInt(params.getString("media_id"));
		
		if(!session.isNew()) {
			JSONObject userData = (JSONObject) session.getAttribute("session");
			try {
				int userId = userData.getInt("id_user");
				Boolean isBool = aq.isLike(userId, mediaId);
				if(isBool == null) {  
					aq.likeVideo(userId, mediaId);
					message.put("status", 200).put("description", "Like");
				} else if(isBool.equals(true)) {
					// Ya hizo un like
					message.put("status", 200).put("description", "This user already likes this video");
				} else {
					// Hizo un dislike -> Se cambia a like
					aq.changeState(true, userId, mediaId);
					message.put("status", 200).put("description", "Changing to like");					
				}
			} catch(SQLException | NullPointerException e) {
				message.put("status", 503).put("description", "Unknown problem, try again");
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
	//	message.put("status", 403).put("description", "Like could not be processed");
}
