package backend.users.videos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import backend.database.MediaQueries;

/**
 * Servlet implementation class Upload
 */
@MultipartConfig
@WebServlet("/Upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */ 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Part file = request.getPart("file");
		InputStream fileContent = file.getInputStream();
		OutputStream os = null;
		String path = "C:\\Bluetube\\users-videos";
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		JSONObject mediaData = new JSONObject();
		MediaQueries mq = new MediaQueries();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		
		try {
			if(!session.isNew()) {
				String fileName = this.getFileName(file);
				File folder = new File(path + "\\" + userData.getString("email"));
				path = path + "\\" + userData.getString("email") + "\\" + fileName;
				if(!folder.exists()) 
					folder.mkdir(); 
				os = new FileOutputStream(path);
				int read = 0;
				byte[] bytes = new byte[1024];				
				while((read = fileContent.read(bytes)) != -1) {
			 		os.write(bytes, 0, read);
				} // COMO SE, SI SE GUARDO CORRECTAMENTE EL ARCHIVO?
				/* 
				 * if(new File(path + "\\" + filename).exist() 
				 * */
				mediaData.put("id_user", userData.getInt("id_user"));
				mediaData.put("media_url", path);
				mediaData.put("media_name", request.getParameter("media_name"));
				mediaData.put("media_filename", fileName);
				mediaData.put("media_des", request.getParameter("media_des"));
				mq.newVideo(mediaData);
				message.put("status", 200).put("description", "Success");
			} else {
				message.put("status", 403).put("description", "Access denied");
			}
		} catch(Exception e) {
			message.put("status", 500).put("description", "The System failed to read the uploaded file from disk");
			e.printStackTrace();
		} finally {
			mq.closeResources();
			if(fileContent != null) 
				fileContent.close();
			if(os != null) {
				os.flush();
				os.close();				
			}			
		}
		out.println(message);
	}
	
	private String getFileName(Part part) {
		for(String content : part.getHeader("content-disposition").split(";")) {
			if(content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
