package backend.users.videos;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import backend.database.MediaQueries;

/**
 * Servlet implementation class UserVideoList
 */
@WebServlet("/UserVideoList")
public class UserVideoList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserVideoList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MediaQueries mq = new MediaQueries();
		JSONArray videos = new JSONArray(); 
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		int userId = userData.getInt("id_user");
		
		if(!session.isNew()) {
			try {
				videos = mq.getUserVideoList(userId);
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				mq.closeResources();
			}
		}else {
			session.invalidate();
		} 
		out.println(videos.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
