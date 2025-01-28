package Service;

import DAO.DAOException;
import DAO.UsuarioAdministradorDAO;
import Entidades.UsuarioAdministrador;

public class UsuarioAdministradorService {
    private final UsuarioAdministradorDAO usuarioAdministradorDAO;

    public UsuarioAdministradorService() {
        this.usuarioAdministradorDAO = new UsuarioAdministradorDAO(); // Instancia del DAO
    }

    public void crearUsuario(String nombre, String apellido, String clave) throws ServiceException {
        try {
            UsuarioAdministrador usuario = new UsuarioAdministrador(0, nombre, apellido, clave);
            usuarioAdministradorDAO.crearUsuario(usuario);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear el usuario", e);
        }
    }

    public void crearTarjeta(int usuarioId, double saldoInicial, String descripcion) throws ServiceException {
        try {
            usuarioAdministradorDAO.crearTarjeta(usuarioId, descripcion);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear la tarjeta", e);
        }
    }

    public void crearCuenta(int usuarioId, String tipoCuenta, String moneda, double saldoInicial) throws ServiceException {
        try {
            usuarioAdministradorDAO.crearCuenta(usuarioId, tipoCuenta);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear la cuenta", e);
        }
    }
}