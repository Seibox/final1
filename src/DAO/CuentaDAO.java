package DAO;

import Entidades.Cuenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO implements ICuentaDAO {

    private static final String URL = "jdbc:h2:~/final1"; // URL de la base de datos H2
    private static final String USER = "sa"; // Usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    @Override
    public void acreditar(String cbu, double monto) throws DAOException {
        String sql = "UPDATE Cuenta SET saldo = saldo + ? WHERE cbu = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, monto);
            stmt.setString(2, cbu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al acreditar el monto en la cuenta con CBU: " + cbu, e);
        }
    }

    @Override
    public void debitar(String cbu, double monto) throws DAOException {
        String sql = "UPDATE Cuenta SET saldo = saldo - ? WHERE cbu = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, monto);
            stmt.setString(2, cbu);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al debitar el monto de la cuenta con CBU: " + cbu, e);
        }
    }

    @Override
    public Cuenta mostrarInfo(String cbu) throws DAOException {
        String sql = "SELECT cbu, alias, tipo_cuenta, moneda, saldo, usuario_id FROM Cuenta WHERE cbu = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cbu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cuenta(
                        rs.getString("cbu"),
                        rs.getString("alias"),
                        rs.getString("tipo_cuenta"),
                        rs.getString("moneda"),
                        rs.getDouble("saldo")
                );
            } else {
                return null; // Si no se encuentra la cuenta, devolvemos null
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener la información de la cuenta con CBU: " + cbu, e);
        }
    }

    public List<Cuenta> obtenerCuentasPorUsuario(int usuarioId) throws DAOException {
        String sql = "SELECT cbu, alias, tipo_cuenta, moneda, saldo FROM Cuenta WHERE usuario_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            List<Cuenta> cuentas = new ArrayList<>();
            while (rs.next()) {
                cuentas.add(new Cuenta(
                        rs.getString("cbu"),
                        rs.getString("alias"),
                        rs.getString("tipo_cuenta"),
                        rs.getString("moneda"),
                        rs.getDouble("saldo")
                ));
            }
            return cuentas;
        } catch (SQLException e) {
            throw new DAOException("Error al obtener las cuentas del usuario con ID: " + usuarioId, e);
        }
    }

    public Cuenta mostrarInfoPorAlias(String alias) throws DAOException {
        String sql = "SELECT cbu, alias, tipo_cuenta, moneda, saldo FROM Cuenta WHERE alias = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alias);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cuenta(
                        rs.getString("cbu"),
                        rs.getString("alias"),
                        rs.getString("tipo_cuenta"),
                        rs.getString("moneda"),
                        rs.getDouble("saldo")
                );
            } else {
                return null; // Si no se encuentra la cuenta, devolvemos null
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener la información de la cuenta con alias: " + alias, e);
        }
    }

}