package DAO;

import Entidades.Usuario;

public interface IUsuarioDAO {
    Usuario verificarCredenciales(String idUsuario, String password) throws DAOException;
}