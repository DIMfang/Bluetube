package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import backend.util.properties.Props;

public class DBConnection {
	
	private static class SingletonHelper {
		
		private static final Connection INSTANCE = createConnection();
		
		// Metodo para retornar la conexion a la clase primaria
		private static Connection createConnection() {
			Props props = Props.getInstance();
			Connection connection = null;
			try {
				Class.forName(props.getDB("driver"));
				connection = DriverManager.getConnection(props.getDB("url"), props.getDB("user"), props.getDB("password"));
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
