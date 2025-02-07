package DAO;

import Entidades.UsuarioAdministrador;
import java.util.List;

public interface IUsuarioAdministradorDAO {
    void crearUsuario(UsuarioAdministrador usuario) throws DAOException;
    void crearTarjeta(int numeroTarjeta, String descripcion, int usuarioId) throws DAOException;    public void crearCuenta(String cbu, String alias, String tipoCuenta, String moneda, double saldo, int usuarioId) throws DAOException;

    List<UsuarioAdministrador> listarAdministradores() throws DAOException;
}
