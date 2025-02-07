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

    private static final String URL = "jdbc:h2:~/final1"; // URL de la base de datos H2
    private static final String USER = "sa"; // Usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    @Override
    public void crearUsuario(UsuarioAdministrador usuario) throws DAOException {
        String sqlCheck = "SELECT COUNT(*) FROM usuario WHERE id = ?";
        String sqlInsert = "INSERT INTO usuario (id, nombre, apellido, clave, tipo_usuario) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // Verificación de ID duplicado
            try (PreparedStatement statementCheck = connection.prepareStatement(sqlCheck)) {
                statementCheck.setInt(1, usuario.getId());
                ResultSet rs = statementCheck.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // Si el ID ya existe, lanzamos una excepción
                    throw new DAOException("El ID ya está en uso.");
                }
            }

            // Si el ID no existe, realizamos la inserción
            try (PreparedStatement statementInsert = connection.prepareStatement(sqlInsert)) {
                statementInsert.setInt(1, usuario.getId());
                statementInsert.setString(2, usuario.getNombre());
                statementInsert.setString(3, usuario.getApellido());
                statementInsert.setString(4, usuario.getClave());
                statementInsert.setString(5, "normal"); // El tipo de usuario siempre será "normal"

                statementInsert.executeUpdate();
            }

        } catch (SQLException e) {
            // Detalles del error de SQL
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Error code: " + e.getErrorCode());
            throw new DAOException("Error al crear el usuario: " + e.getMessage(), e);
        }
    }




    public void crearTarjeta(int numeroTarjeta, String descripcion, int usuarioId) throws DAOException {
        String sql = "INSERT INTO tarjeta (id, saldo, descripcion, usuario_id) VALUES (?, 0, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, numeroTarjeta);
            statement.setString(2, descripcion);
            statement.setInt(3, usuarioId);

            statement.executeUpdate();
        } catch (SQLException e) {
            // Imprimir detalles del error en consola
            System.err.println("Error al crear la tarjeta: " + e.getMessage());
            e.printStackTrace();
            throw new DAOException("Error al crear la tarjeta para el usuario.", e);
        }
    }



    @Override
    public void crearCuenta(String cbu, String alias, String tipoCuenta, String moneda, double saldo, int usuarioId) throws DAOException {
        String sql = "INSERT INTO cuenta (cbu, alias, tipo_cuenta, moneda, saldo, usuario_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cbu);
            statement.setString(2, alias);
            statement.setString(3, tipoCuenta);
            statement.setString(4, moneda);
            statement.setDouble(5, saldo);
            statement.setInt(6, usuarioId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al crear cuenta para el usuario.", e);
        }
    }


    @Override
    public List<UsuarioAdministrador> listarAdministradores() throws DAOException {
        String sql = "SELECT * FROM usuario WHERE tipo_usuario = 'administrador'";
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