package DAO;

import Entidades.Movimiento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO implements IMovimientoDAO {

    private static final String URL = "jdbc:h2:~/final"; // URL de la base de datos H2
    private static final String USER = "sa"; // Usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    @Override
    public List<Movimiento> obtenerMovimientosPorUsuario(int usuarioId) throws DAOException {
        String sql = "SELECT id, tipo_movimiento, monto, fecha, usuario_id, tarjeta_id FROM Movimiento WHERE usuario_id = ?";
        List<Movimiento> movimientos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movimientos.add(new Movimiento(
                        rs.getInt("id"),
                        rs.getString("tipo_movimiento"),
                        rs.getDouble("monto"),
                        new Date(rs.getTimestamp("fecha").getTime()),
                        rs.getInt("usuario_id"),
                        rs.getInt("tarjeta_id")
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los movimientos del usuario con ID: " + usuarioId, e);
        }
        return movimientos;
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorTarjeta(int tarjetaId) throws DAOException {
        String sql = "SELECT id, tipo_movimiento, monto, fecha, usuario_id, tarjeta_id FROM Movimiento WHERE tarjeta_id = ?";
        List<Movimiento> movimientos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tarjetaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movimientos.add(new Movimiento(
                        rs.getInt("id"),
                        rs.getString("tipo_movimiento"),
                        rs.getDouble("monto"),
                        new Date(rs.getTimestamp("fecha").getTime()),
                        rs.getInt("usuario_id"),
                        rs.getInt("tarjeta_id")
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los movimientos de la tarjeta con ID: " + tarjetaId, e);
        }
        return movimientos;
    }

    @Override
    public double obtenerTotalMovimientos(int usuarioId) throws DAOException {
        String sql = "SELECT SUM(monto) AS total FROM Movimiento WHERE usuario_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el total de movimientos del usuario con ID: " + usuarioId, e);
        }
        return 0.0;
    }

    @Override
    public void crearMovimiento(Movimiento movimiento) throws DAOException {
        String sql = "INSERT INTO Movimiento (tipo_movimiento, monto, fecha, usuario_id, tarjeta_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movimiento.getTipoMovimiento());
            stmt.setDouble(2, movimiento.getMonto());
            stmt.setTimestamp(3, new Timestamp(movimiento.getFecha().getTime()));
            stmt.setInt(4, movimiento.getUsuarioId());
            stmt.setInt(5, movimiento.getTarjetaId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al crear el movimiento", e);
        }
    }
}
