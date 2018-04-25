package backend.util.properties;

import java.sql.Connection;
import java.util.Properties;

public class PoolManager{
	
	private static Pool myPool = null;
	
	public static void InitializePool(String URL) {
		myPool = new Pool(URL);
	}
	public static void InitializePool(Properties props) {
		System.out.println("HOLA");
		myPool = new Pool(props);
	}
	public static Connection getConnection() {
		return myPool.getConnection();				
	}
	public static void returnConnection(Connection c) {
		myPool.returnConnection(c);
	}
	// Getters
	public static int onlineConnections() {
		return myPool.getHowManyAreOnline();
	}
	public static int offlineConnections() {
		return myPool.getHowManyAreOffline();
	}
	public static int getInactiveUsers() {
		return myPool.getInactiveConnections();
	}
	public static int getActiveUsers() {
		return myPool.getActiveConnections();
	}
}
