package backend.users.videos;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import backend.database.MediaQueries;

/**
 * Servlet implementation class VideosList
 */
@WebServlet("/VideoList")
public class VideoList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VideoList() {
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
		String mediaName = request.getParameter("key");
		
		try {
			videos = mq.getVideoList(mediaName);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			mq.closeResources();
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
