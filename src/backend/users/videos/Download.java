package backend.users.videos;

import java.io.IOException;
import java.io.OutputStream;

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
<<<<<<< HEAD
//		JSONObject userData = (JSONObject) session.getAttribute("session");
=======
		JSONObject userData = (JSONObject) session.getAttribute("session");
		JSONObject message = new JSONObject();
>>>>>>> 25c35043a1933bd90887214f18bf81253b262290
		MediaQueries queries = new MediaQueries();
		String mediaName = request.getParameter("search");
		response.setContentType("file");
		OutputStream out = response.getOutputStream();

		try {
			if(!session.isNew()) {
<<<<<<< HEAD
				JSONObject mediaData = queries.getVideo(mediaName);	
				response.setHeader("Content-disposition", "attachment; filename=" + mediaData.getString("fileName"));					
				File video = new File(mediaData.getString("url"));
=======
				JSONObject filedata = queries.getVideo(mediaName);	
				response.setHeader("Content-disposition", "attachment; filename=" + filedata.getString("filename"));					
				File video = new File(filedata.getString("media_url"));
>>>>>>> 25c35043a1933bd90887214f18bf81253b262290
				FileInputStream in = new FileInputStream(video);
				byte[] buffer = new byte[4096];
				int length;
				while ((length = in.read(buffer)) > 0){
					out.write(buffer, 0, length);
				}
				in.close();
				out.flush();
			} else {
				message.put("status", 403).put("description", "Access denied");
			}
		} catch(Exception e) {
			message.put("status", 500).put("description", "The System failed to read the uploaded file from disk");
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
