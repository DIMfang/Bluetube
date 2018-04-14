package backend.users.actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import backend.database.MediaQueries;

/**
 * Servlet implementation class DeleteMedia
 */
@WebServlet("/DeleteMediaAsAdmin")
public class DeleteMediaAsAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMediaAsAdmin() {
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
		try {
			String URL = mq.deleteMedia(params.getInt("mediaId"));
			if(URL != null) {
				File media = new File(URL);
				media.delete();
				message.put("status", 200);
			} else {
				message.put("status", 403).put("response", "Video does not exist");
			}			
		} catch(Exception e) {
			e.printStackTrace();
			message.put("status", 500);
		} finally {
			mq.closeResources();
		}
		out.println(message.toString());
	}
	
}
