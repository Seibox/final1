package Entidades;

import Service.ServiceException;
import Service.UsuarioNormalService;

public class UsuarioNormal extends Usuario {
    private UsuarioNormalService usuarioNormalService;

    public UsuarioNormal(int id, String nombre, String apellido, String clave) {
        super(id, nombre, apellido, clave, "normal");
        this.usuarioNormalService = new UsuarioNormalService(); // Instancia del servicio
    }


    public void transferir(int cuentaOrigenId, int cuentaDestinoId, double monto) {
        try {
            usuarioNormalService.transferir(this.getId(), cuentaOrigenId, cuentaDestinoId, monto);
        } catch (ServiceException e) {
            System.err.println("Error al realizar la transferencia: " + e.getMessage());
        }
    }


    public void usarTarjeta(int tarjetaId, double monto) {
        try {
            usuarioNormalService.usarTarjeta(tarjetaId, monto);
        } catch (ServiceException e) {
            System.err.println("Error al usar la tarjeta: " + e.getMessage());
        }
    }


    public void pagarTarjeta(int tarjetaId, double monto) {
        try {
            usuarioNormalService.pagarTarjeta(tarjetaId, monto);
        } catch (ServiceException e) {
            System.err.println("Error al pagar la tarjeta: " + e.getMessage());
        }
    }


}