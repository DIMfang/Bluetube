package backend.users.actions;

import java.io.File;
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
 * Servlet implementation class DeleteMedia
 */
@WebServlet("/DeleteMedia")
public class DeleteMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMedia() {
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
		
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject(), params = new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		MediaQueries mq = new MediaQueries();
		HttpSession session = request.getSession();
		
		try {
			JSONObject userData = (JSONObject) session.getAttribute("session");
			int userId = userData.getInt("id_user");
			int mediaId = params.getInt("mediaId");			
			if(mq.isUserMedia(userId, mediaId)) {
				String URL = mq.deleteMedia(mediaId);
				if(URL != null) {
					File media = new File(URL);
					media.delete();
					message.put("status", 200).put("response", "You have deleted this video");
				} else {
					message.put("status", 403).put("response", "Video does not exist");
				}	
			} else {
				message.put("status", 403).put("response", "Video does not exist");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			message.put("status", 503);
		} finally {
			mq.closeResources();
		}
		out.println(message.toString());
	}

}
