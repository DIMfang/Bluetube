package backend.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import backend.util.properties.Props;

/**
 * Application Lifecycle Listener implementation class Context
 *
 */
@WebListener
public class Context implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public Context() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {     	
    	Props.getInstance();
    }
	
}
