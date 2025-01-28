package DAO;

import Entidades.UsuarioAdministrador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioAdministradorDAO implements IUsuarioAdministradorDAO {

    private static final String URL = "jdbc:h2:~/final"; // URL de la base de datos H2
    private static final String USER = "sa"; // Usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    @Override
    public void crearUsuario(UsuarioAdministrador usuario) throws DAOException {
        String sql = "INSERT INTO usuarios (id, nombre, apellido, clave, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, usuario.getId());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getApellido());
            statement.setString(4, usuario.getClave());
            statement.setString(5, "administrador");

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al crear el usuario administrador.", e);
        }
    }

    @Override
    public void crearTarjeta(int idUsuario, String descripcion) throws DAOException {
        String sql = "INSERT INTO tarjetas (id_usuario, descripcion, saldo) VALUES (?, ?, 0)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);
            statement.setString(2, descripcion);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al crear tarjeta para el usuario.", e);
        }
    }

    @Override
    public void crearCuenta(int idUsuario, String tipoCuenta) throws DAOException {
        String sql = "INSERT INTO cuentas (id_usuario, tipo_cuenta, saldo) VALUES (?, ?, 0)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idUsuario);
            statement.setString(2, tipoCuenta);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al crear cuenta para el usuario.", e);
        }
    }

    @Override
    public List<UsuarioAdministrador> listarAdministradores() throws DAOException {
        String sql = "SELECT * FROM usuarios WHERE tipo_usuario = 'administrador'";
        List<UsuarioAdministrador> administradores = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UsuarioAdministrador administrador = new UsuarioAdministrador(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("clave")
                );
                administradores.add(administrador);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar los administradores.", e);
        }
        return administradores;
    }
}