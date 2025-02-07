package DAO;

import Entidades.UsuarioNormal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioNormalDAO implements IUsuarioNormalDAO {

    private static final String URL = "jdbc:h2:~/final1"; // URL de la base de datos H2
    private static final String USER = "sa"; // Usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    @Override
    public void transferir(int usuarioId, int cuentaOrigenId, int cuentaDestinoId, double monto) throws DAOException {
        String sqlTransferencia = "UPDATE cuentas SET saldo = saldo - ? WHERE id = ?; " +
                "UPDATE cuentas SET saldo = saldo + ? WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sqlTransferencia)) {

            // Primera operación: Restar el monto de la cuenta de origen
            statement.setDouble(1, monto);
            statement.setInt(2, cuentaOrigenId);

            // Segunda operación: Sumar el monto a la cuenta de destino
            statement.setDouble(3, monto);
            statement.setInt(4, cuentaDestinoId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al realizar la transferencia.", e);
        }
    }

    @Override
    public void usarTarjeta(int tarjetaId, double monto) throws DAOException {
        String sql = "UPDATE tarjetas SET saldo = saldo - ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, monto);
            statement.setInt(2, tarjetaId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al usar la tarjeta.", e);
        }
    }

    @Override
    public void pagarTarjeta(int tarjetaId, double monto) throws DAOException {
        String sql = "UPDATE tarjetas SET saldo = saldo + ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, monto);
            statement.setInt(2, tarjetaId);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al pagar la tarjeta.", e);
        }
    }

    @Override
    public List<UsuarioNormal> listarUsuariosNormales() throws DAOException {
        String sql = "SELECT * FROM usuarios WHERE tipo_usuario = 'normal'";
        List<UsuarioNormal> usuariosNormales = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                UsuarioNormal usuario = new UsuarioNormal(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("clave")
                );
                usuariosNormales.add(usuario);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al listar los usuarios normales.", e);
        }
        return usuariosNormales;
    }
}