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
		JSONObject userData = (JSONObject) session.getAttribute("session");
		JSONObject message = new JSONObject();
//		PrintWriter outtext = response.getWriter();
		MediaQueries queries = new MediaQueries();
		String mediaName = request.getParameter("search");
		String path = "C:\\Bluetube\\users-videos";
		response.setContentType("file");
		
		OutputStream out = response.getOutputStream();
		
				
		try {
			if(!session.isNew()) {
//				File folder = new File(path + "\\" + userData.getString("email"));
//				if(folder.exists()) {
//					if(queries.checkVideo(mediaName)) {
						String filename = queries.getVideo(mediaName);	
						response.setHeader("Content-disposition", "attachment; filename=" + filename);					
						File video = new File(path + "\\" + userData.getString("email") + "\\" + filename);
						FileInputStream in = new FileInputStream(video);
						byte[] buffer = new byte[1024];
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
		
//		 outtext.println(message);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
