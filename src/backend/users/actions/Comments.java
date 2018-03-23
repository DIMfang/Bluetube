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

import org.json.JSONArray;
import org.json.JSONObject;

import backend.database.ActionQueries;

/**
 * Servlet implementation class Comments
 */
@WebServlet("/Comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionQueries aq = new ActionQueries();
		int mediaId = Integer.parseInt(request.getParameter("key"));
		PrintWriter out = response.getWriter();
		JSONArray comments = new JSONArray();
		JSONObject message = new JSONObject();
		
		try {
			comments = aq.getCommentList(mediaId);
			message.put("status", 200).put("comments", comments);
		} catch(SQLException e) {
			message.put("status", 403).put("description", "database error");
			e.printStackTrace();
		} finally {
			aq.closeResources();
		}
		out.println(message.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(); 
		ActionQueries mq = new ActionQueries(); 
		JSONObject message = new JSONObject();		
		JSONObject commentData = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		JSONObject userData = (JSONObject) session.getAttribute("session");
		if(!session.isNew()) {	
			try {
				commentData.put("id_user", userData.getInt("id_user"));
				mq.newComment(commentData);
				message.put("status", 200).put("description", "Success");
			} catch(SQLException e) {		
				e.printStackTrace();
			} finally {
				mq.closeResources();
			}
		} else {		
			message.put("status", 403).put("description", "session not started");
			session.invalidate();
		}
		out.println(message);
	}

}
