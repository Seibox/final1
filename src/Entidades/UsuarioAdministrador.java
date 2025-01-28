package Entidades;

import Service.UsuarioAdministradorService;
import Service.ServiceException;

public class UsuarioAdministrador extends Usuario {
    private UsuarioAdministradorService usuarioAdministradorService;

    public UsuarioAdministrador(int id, String nombre, String apellido, String clave) {
        super(id, nombre, apellido, clave, "administrador");
        this.usuarioAdministradorService = new UsuarioAdministradorService(); // Instancia del servicio
    }

    @Override
    public void transferir() {
        throw new UnsupportedOperationException("Los administradores no realizan transferencias.");
    }

    @Override
    public void usarTarjeta() {
        throw new UnsupportedOperationException("Los administradores no usan tarjetas.");
    }

    @Override
    public void pagarTarjeta() {
        throw new UnsupportedOperationException("Los administradores no pagan tarjetas.");
    }

    // Métodos específicos del administrador
    public void crearUsuario() {
        try {
            usuarioAdministradorService.crearUsuario();
        } catch (ServiceException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
        }
    }

    public void crearTarjeta() {
        try {
            usuarioAdministradorService.crearTarjeta();
        } catch (ServiceException e) {
            System.err.println("Error al crear tarjeta: " + e.getMessage());
        }
    }

    public void crearCuenta() {
        try {
            usuarioAdministradorService.crearCuenta();
        } catch (ServiceException e) {
            System.err.println("Error al crear cuenta: " + e.getMessage());
        }
    }
}
