
package backend.users.videos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		JSONObject userData = (JSONObject) session.getAttribute("session");
		MediaQueries queries = new MediaQueries();
		String mediaName = request.getParameter("search");
		System.out.println(mediaName);
		
		try {
			JSONObject filedata = queries.getVideo(mediaName);	
			InputStream in = new FileInputStream (filedata.getString("url"));
			String Type = "video/mp4";
			byte[] bytes = new byte[1024];
			int bytesRead;
			
			response.setContentType(Type);
			
			while ((bytesRead = in.read(bytes)) != -1) {
				out.write(bytes, 0, bytesRead);
			}
			in.close();
			System.out.println("enviando");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			queries.closeResources();
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
