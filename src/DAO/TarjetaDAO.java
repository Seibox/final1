package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TarjetaDAO implements ITarjetaDAO {

    private static final String URL = "jdbc:h2:~/final"; // URL de la base de datos H2
    private static final String USER = "sa"; // Usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    @Override
    public void acreditarSaldo(int tarjetaId, double monto) throws DAOException {
        String sql = "UPDATE Tarjeta SET saldo = saldo + ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, tarjetaId);
            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas == 0) {
                throw new DAOException("No se encontró la tarjeta con ID: " + tarjetaId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al acreditar saldo en la tarjeta con ID: " + tarjetaId, e);
        }
    }

    @Override
    public void debitarSaldo(int tarjetaId, double monto) throws DAOException {
        String sql = "UPDATE Tarjeta SET saldo = saldo - ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, monto);
            stmt.setInt(2, tarjetaId);
            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas == 0) {
                throw new DAOException("No se encontró la tarjeta con ID: " + tarjetaId);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al debitar saldo de la tarjeta con ID: " + tarjetaId, e);
        }
    }
}
