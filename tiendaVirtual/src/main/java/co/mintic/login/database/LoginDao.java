package co.mintic.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.mintic.login.bean.Login;

public class LoginDao {

	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda?useSSL=false", "root", "");
		} catch (SQLException e) {
			
			printSQLException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	
	public boolean validate(Login loginBean) throws ClassNotFoundException {
		boolean status = false;

		try (

				Connection cn = getConnection();

				PreparedStatement preparedStatement = cn
						.prepareStatement("select * from usuario where nombreUsuario= ? and password = ? ")) {
			preparedStatement.setString(1, loginBean.getNombreUsuario());
			preparedStatement.setString(2, loginBean.getPassword());

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			status = rs.next();

		} catch (SQLException e) {
			printSQLException(e);
		}
		return status;
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
