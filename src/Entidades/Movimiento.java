package Entidades;

import Service.MovimientoService;
import Service.ServiceException;

import java.util.Date;

public class Movimiento {
    private int id;
    private String tipoMovimiento; // "Uso de tarjeta", "Pago de tarjeta", "Recibir transferencia", "Enviar transferencia"
    private double monto;
    private Date fecha;

    private MovimientoService movimientoService;

    public Movimiento(int id, String tipoMovimiento, double monto, Date fecha) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.monto = monto;
        this.fecha = fecha;
        this.movimientoService = new MovimientoService(); // Instancia del servicio
    }

    // Método para emitir el total de movimientos
    public void emitirTotalMovimientos() {
        try {
            movimientoService.emitirTotalMovimientos(id);
        } catch (ServiceException e) {
            System.err.println("Error al emitir el total de movimientos: " + e.getMessage());
        }
    }

    // Método para emitir los movimientos de una tarjeta
    public void emitirMovimientosTarjeta(int tarjetaId) {
        try {
            movimientoService.emitirMovimientosTarjeta(tarjetaId);
        } catch (ServiceException e) {
            System.err.println("Error al emitir los movimientos de la tarjeta: " + e.getMessage());
        }
    }

    // Método para emitir los movimientos de un usuario
    public void emitirMovimientosUsuario(int usuarioId) {
        try {
            movimientoService.emitirMovimientosUsuario(usuarioId);
        } catch (ServiceException e) {
            System.err.println("Error al emitir los movimientos del usuario: " + e.getMessage());
        }
    }
}
