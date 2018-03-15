package backend.users.videos.down;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

import backend.database.Queries;

import java.io.File;
import java.io.FileInputStream;


/**
 * Servlet implementation class Download
 */
@WebServlet("/Download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		JSONObject message = new JSONObject();
		PrintWriter outtext = response.getWriter();
		Queries queries = new Queries();
		
		String path = "C:\\Bluetube\\users-videos";
		
		try {
			if(!session.isNew()) {
				message.put("test", 1);
				File folder = new File(path + "\\" + userData.getString("email"));
				if(folder.exists()) {
					message.put("test", 2);
					if(queries.checkVideo(request.getParameter("search"))) {
						path = queries.getVideo(request.getParameter("search"));
						message.put("status", 504).put("description", "Success! Video was found in the DB.");
						message.put("path", path);
					}else {
						message.put("status", 502).put("description", "That video does not exist in the database.");
					}
				}else {
					message.put("status", 503).put("description", "User hasn't uploaded a video.");
				}
			} else {
				message.put("status", 403).put("description", "Access denied");
			}
		} catch(Exception e) {
			message.put("status", 500).put("description", "The System failed to read the uploaded file from disk");
		}
		outtext.println(message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
