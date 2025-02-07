package Service;

import DAO.DAOException;
import DAO.UsuarioAdministradorDAO;
import Entidades.UsuarioAdministrador;

import javax.swing.*;

public class UsuarioAdministradorService {
    private final UsuarioAdministradorDAO usuarioAdministradorDAO;

    public UsuarioAdministradorService() {
        this.usuarioAdministradorDAO = new UsuarioAdministradorDAO(); // Instancia del DAO
    }

    public void crearUsuario(int id, String nombre, String apellido, String clave) throws ServiceException {
        try {
            // Creamos el usuario con los datos recibidos
            UsuarioAdministrador usuario = new UsuarioAdministrador(id, nombre, apellido, clave);

            // Llamamos al DAO para crear el usuario en la base de datos
            usuarioAdministradorDAO.crearUsuario(usuario);

        } catch (DAOException e) {
            // Si el DAO lanza una excepción, capturamos y añadimos más información al error
            String errorMessage = "Error al crear el usuario. Detalles del error: " + e.getMessage();
            System.err.println(errorMessage); // Imprimimos el mensaje de error para mayor claridad en la consola
            throw new ServiceException(errorMessage, e); // Lanzamos la excepción con detalles completos
        }
    }




    public void crearTarjeta(int numeroTarjeta, String descripcion, int usuarioId) throws ServiceException {
        try {
            usuarioAdministradorDAO.crearTarjeta(numeroTarjeta, descripcion, usuarioId);
        } catch (DAOException e) {
            // Agregar información adicional del error para hacer más claro cuál fue el problema
            String errorMessage = "Error al crear tarjeta en el servicio. Detalles del error: " + e.getMessage();
            throw new ServiceException(errorMessage, e);
        }
    }



    public void crearCuenta(String cbu, String alias, String tipoCuenta, String moneda, double saldo, int usuarioId) throws ServiceException {
        try {
            usuarioAdministradorDAO.crearCuenta(cbu, alias, tipoCuenta, moneda, saldo, usuarioId);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear la cuenta", e);
        }
    }

}