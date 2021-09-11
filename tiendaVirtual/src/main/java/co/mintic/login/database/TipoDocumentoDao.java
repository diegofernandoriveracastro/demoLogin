package co.mintic.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.mintic.login.bean.TipoDocumento;

public class TipoDocumentoDao {

	private static final String SELECT_ALL = "SELECT * from tipoDocumento";

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda?useSSL=false", "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public List<TipoDocumento> selectAllDocumentType() {

		List<TipoDocumento> tipoDocumento = new ArrayList<>();
		try (Connection connection = getConnection();

				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);) {
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String tipo = rs.getString("tipo");
				tipoDocumento.add(new TipoDocumento(id, tipo));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return tipoDocumento;
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
