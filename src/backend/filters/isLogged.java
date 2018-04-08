package backend.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet Filter implementation class isLogged
 */
@WebFilter(urlPatterns = {"/Comments", "/Like", "/Dislike", "/Login", "/Upload"})
public class isLogged implements Filter {

    /**
     * Default constructor. 
     */
    public isLogged() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false); 
		System.out.println("4");
		if(session == null) {
			// Invitado
			res.setHeader("Authorization", "Invited");			
		} else {
			try {
				JSONObject userData = (JSONObject) session.getAttribute("session");
				String authType = userData.getString("type_des");
				res.setHeader("Authorization", authType);			
				System.out.println(authType + "1");
			} catch(Exception e) {
				session.invalidate();
				res.setHeader("Authorization", "Invited");				
			}
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
