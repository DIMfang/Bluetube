
package backend.users.videos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import backend.database.MediaQueries;

import java.io.FileInputStream;
import java.io.InputStream;
/**
 * Servlet implementation class Streaming
 */
@MultipartConfig
@WebServlet("/Streaming")
public class Streaming extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Streaming() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		MediaQueries mq = new MediaQueries();
		int mediaId = Integer.parseInt(request.getParameter("key"));
		
		try {
			mq.addView(mediaId); // TEMPORALMENTE
			JSONObject filedata = mq.getMedia(mediaId); 
			InputStream in = new FileInputStream (filedata.getString("url"));			
			String type = "video/mp4";
			byte[] bytes = new byte[1024];
			int bytesRead;
			
			response.setContentType(type);
			while ((bytesRead = in.read(bytes)) != -1) {
				out.write(bytes, 0, bytesRead);
			}
			in.close();
			
		} catch(Exception e) {
			
		} finally {
			mq.closeResources();
			out.close();
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
