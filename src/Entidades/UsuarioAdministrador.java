package Entidades;

import Service.UsuarioAdministradorService;
import Service.ServiceException;

public class UsuarioAdministrador extends Usuario {
    private UsuarioAdministradorService usuarioAdministradorService;

    public UsuarioAdministrador(int id, String nombre, String apellido, String clave) {
        super(id, nombre, apellido, clave, "administrador");
        this.usuarioAdministradorService = new UsuarioAdministradorService(); // Instancia del servicio
    }


    public void transferir() {
        throw new UnsupportedOperationException("Los administradores no realizan transferencias.");
    }


    public void usarTarjeta() {
        throw new UnsupportedOperationException("Los administradores no usan tarjetas.");
    }


    public void pagarTarjeta() {
        throw new UnsupportedOperationException("Los administradores no pagan tarjetas.");
    }

    // Métodos específicos del administrador
    public void crearUsuario(int id, String nombre, String apellido, String clave) {
        try {
            usuarioAdministradorService.crearUsuario (id, nombre, apellido, clave);
        } catch (ServiceException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
    }

    public void crearTarjeta(int numeroTarjeta, String descripcion, int usuarioId) {
        try {
            usuarioAdministradorService.crearTarjeta(numeroTarjeta, descripcion, usuarioId);
        } catch (ServiceException e) {
            System.err.println("Error al crear tarjeta: " + e.getMessage());
        }
    }


    public void crearCuenta(String cbu, String alias, String tipoCuenta, String moneda, double saldo, int usuarioId) {
        try {
            // Llamamos al servicio para crear la cuenta con todos los parámetros necesarios
            usuarioAdministradorService.crearCuenta(cbu, alias, tipoCuenta, moneda, saldo, usuarioId);
        } catch (ServiceException e) {
            System.err.println("Error al crear cuenta: " + e.getMessage());
        }
    }

}