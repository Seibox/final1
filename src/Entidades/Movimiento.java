package Entidades;

import Service.MovimientoService;
import Service.ServiceException;

import java.util.Date;

public class Movimiento {
    private int id;
    private String tipoMovimiento; // "Uso de tarjeta", "Pago de tarjeta", "Recibir transferencia", "Enviar transferencia"
    private double monto;
    private Date fecha;

    private int usuarioId; // Nuevo campo


    private MovimientoService movimientoService;

    public Movimiento(int id, String tipoMovimiento, double monto, Date fecha, int usuarioId) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.monto = monto;
        this.fecha = fecha;
        this.usuarioId = usuarioId;

    }

    // Método para emitir el total de movimientos
    public void emitirTotalMovimientos() {
        try {
            movimientoService.emitirTotalMovimientos(id);
        } catch (ServiceException e) {
            System.err.println("Error al emitir el total de movimientos: " + e.getMessage());
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }


    public MovimientoService getMovimientoService() {
        return movimientoService;
    }

    public void setMovimientoService(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }
}
