package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import backend.properties.Props;

public class DBConnection {
	
	private static class SingletonHelper {
		
		private static final Connection INSTANCE = createConnection();
		
		// Metodo para retornar la conexion a la clase primaria
		private static Connection createConnection() {
			Props props = Props.getInstance();
			Connection connection = null;
			try {
				Class.forName(props.getProp("db", "driver"));
				connection = DriverManager.getConnection(props.getProp("db", "url"), props.getProp("db", "user"), props.getProp("db", "password"));
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
