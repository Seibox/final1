package Service;

import DAO.DAOException;

public class UsuarioAdministradorService {
    private UsuarioDAO usuarioDAO;

    public UsuarioAdministradorService() {
        this.usuarioDAO = new UsuarioDAO(); // Instancia del DAO
    }

    public void crearUsuario(String nombre, String apellido, String clave, String tipoUsuario) throws ServiceException {
        try {
            usuarioDAO.crearUsuario(nombre, apellido, clave, tipoUsuario);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear el usuario", e);
        }
    }

    public void crearTarjeta(int usuarioId, double saldoInicial, String descripcion) throws ServiceException {
        try {
            usuarioDAO.crearTarjeta(usuarioId, saldoInicial, descripcion);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear la tarjeta", e);
        }
    }

    public void crearCuenta(int usuarioId, String tipoCuenta, String moneda, double saldoInicial) throws ServiceException {
        try {
            usuarioDAO.crearCuenta(usuarioId, tipoCuenta, moneda, saldoInicial);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear la cuenta", e);
        }
    }
}
