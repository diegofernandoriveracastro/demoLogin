package co.mintic.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda?useSSL=false", "root", "");
		} catch (SQLException e) {

			printSQLException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("stadosql: " + ((SQLException) e).getSQLState());
				System.err.println("erro: " + ((SQLException) e).getErrorCode());
				System.err.println("Mensaje: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("causa: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
