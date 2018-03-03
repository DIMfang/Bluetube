package backend.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import backend.properties.Props;

public class DBConnection {
	
	private static class SingletonHelper {
		
		private static final Connection INSTANCE = createConnection();
		
		// Metodo para retornar la conexion a la clase primaria
		private static Connection createConnection() {
			Props props = Props.getInstance();
			Connection connection = null;
			try {
				Class.forName(""); // prop.getProperty("driver")
				connection = DriverManager.getConnection(); // prop.getProperty("url"), prop.getProperty("user"), prop.getProperty("password")
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			return connection;
		}
	}
	
	// Devuelvo la conexion
	public static Connection getConnection() {
		return SingletonHelper.INSTANCE;
	}
}
