package backend.util.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Pool {
	
	private ArrayList<Connection> availableConnections = new ArrayList<Connection>();
	private Properties props = null;
	private int howManyAreOnline = 0;
	private int howManyAreOffline = 0;
	
	public Pool(String url) {
		props = getProperties(url);
		howManyAreOffline = Integer.parseInt(props.getProperty("maxConnections"));
		initializeConnectionPool();
	}
	public Pool(Properties prop) {
		this.props = prop;
		howManyAreOffline = Integer.parseInt(prop.getProperty("maxConnections"));
		System.out.println(howManyAreOffline);
		initializeConnectionPool();
	} 
	
	// Metodo para iniciar el pool de conexiones
	private void initializeConnectionPool() {
		while(!checkPoolIsAlmostFull()) {
			availableConnections.add(newConnection());
		}
	}
	
	// Metodo para crear una conexion
	private Connection newConnection() {
			try {
				Class.forName(props.getProperty("driver"));
				Connection connection = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
				howManyAreOnline += 1;
				howManyAreOffline -= 1;
				return connection;
			} catch(SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	// Metodo para obtener una conexion del ArrayList
	public synchronized Connection getConnection() {
		Connection connection = null;

		do{
			if(availableConnections.size() > 0) {
				connection = availableConnections.get(0);
				availableConnections.remove(0);
			}else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}while(connection == null);
			 
		checkMinConnections();
		return connection;
	}
	
	// Metodo para devolver una conexion al ArrayList
	public void returnConnection(Connection connection) {
		availableConnections.add(connection);
		checkConnectionUnused();
	}
		
	// Metodo para verificar si el pool se encuentra lleno
	private synchronized boolean checkPoolIsAlmostFull() {
		final int minSize = Integer.parseInt(props.getProperty("maxWaitingCnx"));
		if(availableConnections.size() < minSize) {
			return false;
		}
		return true;
	}
	
	// Metodo para verificar si hay menos conexiones en espera de las recomendadas
	private synchronized void checkMinConnections() {
		final int minSize = Integer.parseInt(props.getProperty("maxWaitingCnx"));
		if(availableConnections.size() < minSize) {
			while((availableConnections.size() < 5) && (howManyAreOffline > 0)){
				Connection connection = newConnection();
				availableConnections.add(connection);
			}
		}
	}
	
	// Metodo para verificar si hay mas conexiones disponibles sin usar de la cantidad maxima de conexiones disponibles
	private synchronized void checkConnectionUnused() {
		final int maxSize = Integer.parseInt(props.getProperty("maxWaitingCnx"));
		if(availableConnections.size() > maxSize) {
			while(availableConnections.size() > maxSize) {
				try {
					Connection connection = availableConnections.get(0);
					availableConnections.remove(0);
					connection.close();
					howManyAreOnline -= 1;
					howManyAreOffline += 1;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Getters
	public int getHowManyAreOnline() {
		return howManyAreOnline;
	}
	public int getHowManyAreOffline() {
		return howManyAreOffline;
	}
	public int getActiveConnections() {
		return (howManyAreOnline - availableConnections.size());
	}
	public int getInactiveConnections() {
		return availableConnections.size();
	}
	
	// Metodo para obtener el archivo .properties necesaro para realizar la conexion con la DB
	private static Properties getProperties(String url) {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(url);
			prop.load(is);
		}catch(IOException e){
			System.out.println(e.toString());
		}
		return prop;
	}

}
