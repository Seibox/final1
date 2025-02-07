package DAO;

import Entidades.Tarjeta;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarjetaDAO implements ITarjetaDAO {

    private static final String URL = "jdbc:h2:~/final1"; // URL de la base de datos H2
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

    public List<Tarjeta> obtenerTarjetasPorUsuario(int usuarioId) throws DAOException {
        String sql = "SELECT id, saldo, descripcion, limite FROM Tarjeta WHERE usuario_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            List<Tarjeta> tarjetas = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                double saldo = rs.getDouble("saldo");
                String descripcion = rs.getString("descripcion");
                int limite = rs.getInt("limite");

                // Imprimir los valores para ver qué se está recuperando
                System.out.println("ID: " + id + ", Saldo: " + saldo + ", Descripción: " + descripcion + ", Limite: " + limite);

                tarjetas.add(new Tarjeta(id, saldo, descripcion, usuarioId, limite));
            }
            return tarjetas;
        } catch (SQLException e) {
            throw new DAOException("Error al obtener las tarjetas del usuario con ID: " + usuarioId, e);
        }
    }

}
