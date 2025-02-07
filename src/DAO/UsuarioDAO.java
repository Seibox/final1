package DAO;

import Entidades.Usuario;
import java.sql.*;

public class UsuarioDAO implements IUsuarioDAO {
    private static final String URL = "jdbc:h2:~/final1"; // URL de la base de datos H2
    private static final String USER = "sa"; // Usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    @Override
    public Usuario verificarCredenciales(String idUsuario, String password) throws DAOException {
        String sql = "SELECT id, nombre, apellido, clave, tipo_usuario FROM Usuario WHERE id = ? AND clave = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);
            stmt.setString(2, password);

            System.out.println("Ejecutando consulta: " + stmt.toString()); // DEBUG

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Usuario encontrado: " + rs.getString("nombre") + " " + rs.getString("apellido")); // DEBUG
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("clave"),
                        rs.getString("tipo_usuario")
                );
            } else {
                System.out.println("No se encontró usuario con esas credenciales."); // DEBUG
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error exacto en la consola
            throw new DAOException("Error al verificar las credenciales del usuario con ID: " + idUsuario, e);
        }
    }
}
