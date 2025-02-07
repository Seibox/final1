package Service;

import DAO.IUsuarioDAO;
import DAO.UsuarioDAO;
import DAO.DAOException;
import Entidades.Usuario;

public class UsuarioService {
    private IUsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO(); // Inicializamos el DAO
    }

    /**
     * Método para verificar las credenciales de un usuario.
     *
     * @param idUsuario El ID del usuario a verificar.
     * @param password La contraseña del usuario a verificar.
     * @return El usuario si las credenciales son correctas, o null si no lo son.
     * @throws DAOException Si ocurre un error durante la consulta a la base de datos.
     */
    public Usuario verificarCredenciales(String idUsuario, String password) throws DAOException {
        // Validaciones adicionales (si es necesario)
        if (idUsuario == null || idUsuario.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("ID de usuario y contraseña no pueden estar vacíos");
        }

        // Llamada al DAO para verificar las credenciales
        return usuarioDAO.verificarCredenciales(idUsuario, password);
    }
}
