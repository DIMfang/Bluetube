package backend.users.videos;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

import backend.database.MediaQueries;

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
		MediaQueries queries = new MediaQueries();
		OutputStream out = response.getOutputStream();
		String mediaName = request.getParameter("search");
		response.setContentType("file");		
		
		try {
			if(!session.isNew()) {
				JSONObject mediaData = queries.getVideo(mediaName);	
				System.out.println(mediaData.toString());
				System.out.println(userData.toString());
				response.setHeader("Content-disposition", "attachment; filename=" + mediaData.getString("fileName"));					
				File video = new File(mediaData.getString("url"));
				FileInputStream in = new FileInputStream(video);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
			} else {
				// Manejar que no tenga cuenta... 
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			queries.closeResources();
			out.flush();
//			out.close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
