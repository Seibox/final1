package Entidades;

import Service.ServiceException;
import Service.UsuarioNormalService;

public class UsuarioNormal extends Usuario {
    private UsuarioNormalService usuarioNormalService;

    public UsuarioNormal(int id, String nombre, String apellido, String clave) {
        super(id, nombre, apellido, clave, "normal");
        this.usuarioNormalService = new UsuarioNormalService(); // Instancia del servicio
    }

    @Override
    public void transferir() {
        try {
            usuarioNormalService.transferir(this);
        } catch (ServiceException e) {
            System.err.println("Error al realizar la transferencia: " + e.getMessage());
        }
    }

    @Override
    public void usarTarjeta() {
        try {
            usuarioNormalService.usarTarjeta(this);
        } catch (ServiceException e) {
            System.err.println("Error al usar la tarjeta: " + e.getMessage());
        }
    }

    @Override
    public void pagarTarjeta() {
        try {
            usuarioNormalService.pagarTarjeta(this);
        } catch (ServiceException e) {
            System.err.println("Error al pagar la tarjeta: " + e.getMessage());
        }
    }
}
