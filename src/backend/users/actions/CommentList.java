package backend.users.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import backend.database.ActionQueries;

/**
 * Servlet implementation class CommentList
 */
@WebServlet("/CommentList")
public class CommentList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentList() {
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
		
		try {
			comments = aq.getCommentList(mediaId);
		} catch(SQLException e) {
			// Could not loaded the comments
			e.printStackTrace();
		} finally {
			aq.closeResources();
		}
		out.println(comments.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
