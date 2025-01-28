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
    public void crearUsuario(String nombre, String apellido, String clave) {
        try {
            usuarioAdministradorService.crearUsuario(nombre, apellido, clave);
        } catch (ServiceException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
    }

    public void crearTarjeta(int usuarioId, double saldoInicial, String descripcion) {
        try {
            usuarioAdministradorService.crearTarjeta(usuarioId, saldoInicial, descripcion);
        } catch (ServiceException e) {
            System.err.println("Error al crear tarjeta: " + e.getMessage());
        }
    }

    public void crearCuenta(int usuarioId, String tipoCuenta, String moneda, double saldoInicial) {
        try {
            usuarioAdministradorService.crearCuenta(usuarioId, tipoCuenta, moneda, saldoInicial);
        } catch (ServiceException e) {
            System.err.println("Error al crear cuenta: " + e.getMessage());
        }
    }
}