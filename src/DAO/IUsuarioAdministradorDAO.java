package DAO;

import Entidades.UsuarioAdministrador;
import java.util.List;

public interface IUsuarioAdministradorDAO {
    void crearUsuario(UsuarioAdministrador usuario) throws DAOException;
    void crearTarjeta(int idUsuario, String descripcion) throws DAOException;
    void crearCuenta(int idUsuario, String tipoCuenta) throws DAOException;
    List<UsuarioAdministrador> listarAdministradores() throws DAOException;
}