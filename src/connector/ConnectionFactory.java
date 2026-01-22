package connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConnection() throws SQLException {
	String url = "jdbc:mysql://localhost:3306/store";
	String username = "";
	String password = "";

	return DriverManager.getConnection(url,username,password);
	}
}
