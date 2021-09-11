package co.mintic.login.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.mintic.login.bean.TipoDocumento;
import co.mintic.login.bean.Usuario;

public class UsuarioDao {

	private static final String SELECT_ALL_USERS = "SELECT U.*,TD.id AS tipoDocumento,TD.tipo FROM usuario U INNER JOIN tipoDocumento TD ON (U.idtipoDocumento=Td.id)";
	private static final String INSERT_USERS_SQL = "insert into usuario(idTipoDocumento,numeroDocumento,nombre,password,nombreUsuario) values(?,?,?,?,?);";
	private static final String DELETE_USERS_SQL = "delete from usuario where id = ?;";
	private static final String UPDATE_USERS_SQL = "update usuario  set idTipoDocumento = ?,numeroDocumento= ?, nombre =? ,password =?  where id = ?;";

	public void insertUser(Usuario usuario) throws SQLException {
		System.out.println(INSERT_USERS_SQL);
		Conexion connection = new Conexion();

		try (Connection p = connection.getConnection();

				PreparedStatement preparedStatement = p.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setInt(1, usuario.getTipoDocumento().getId());
			preparedStatement.setString(2, usuario.getNumeroDocumento());
			preparedStatement.setString(3, usuario.getNombre());
			preparedStatement.setString(4, usuario.getPassword());
			preparedStatement.setString(5, usuario.getNombreUsuario());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	public List<Usuario> selectAllUsers() {

		List<Usuario> users = new ArrayList<>();
		Conexion connection = new Conexion();
		try (

				Connection p = connection.getConnection();

				PreparedStatement preparedStatement = p.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");

				TipoDocumento td = new TipoDocumento();
				td.setId(rs.getInt("tipoDocumento"));
				td.setTipo(rs.getString("tipo"));

				String numeroDocumento = rs.getString("numeroDocumento");
				String nombre = rs.getString("nombre");
				String password = rs.getString("password");
				String nombreUsuario = rs.getString("nombreUsuario");

				users.add(new Usuario(id, td, numeroDocumento, nombre, password, nombreUsuario));
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return users;
	}

	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		Conexion connection = new Conexion();
		try (Connection p = connection.getConnection();
				PreparedStatement statement = p.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateUser(Usuario usuario) throws SQLException {
		boolean rowUpdated;
		Conexion connection = new Conexion();
		try (Connection p = connection.getConnection();
				PreparedStatement statement = p.prepareStatement(UPDATE_USERS_SQL);) {
			statement.setInt(1, usuario.getTipoDocumento().getId());
			statement.setString(2, usuario.getNumeroDocumento());
			statement.setString(3, usuario.getNombre());
			statement.setString(4, usuario.getPassword());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

}
