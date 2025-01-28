package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO implements IUsuarioDAO {

    private final String url = "jdbc:h2:~/final1";
    private final String user = "sa";
    private final String password = "";

    @Override
    public void crearUsuario(String nombre, String apellido, String clave, String tipoUsuario) throws DAOException {
        String sql = "INSERT INTO USUARIOS (nombre, apellido, clave, tipo_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, this.password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, clave);
            stmt.setString(4, tipoUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al crear el usuario.", e);
        }
    }

    @Override
    public boolean verificarUsuario(String nombre, String clave) throws DAOException {
        String sql = "SELECT COUNT(*) FROM USUARIOS WHERE nombre = ? AND clave = ?";

        try (Connection conn = DriverManager.getConnection(url, user, this.password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, clave);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si el usuario y clave coinciden
            }
        } catch (SQLException e) {
            throw new DAOException("Error al verificar usuario.", e);
        }
        return false;
    }

    @Override
    public boolean existeUsuario(String nombre) throws DAOException {
        String sql = "SELECT COUNT(*) FROM USUARIOS WHERE nombre = ?";

        try (Connection conn = DriverManager.getConnection(url, user, this.password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DAOException("Error al verificar la existencia del usuario.", e);
        }
        return false;
    }
}
