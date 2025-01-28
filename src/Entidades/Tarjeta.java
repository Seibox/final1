package Entidades;

import Service.TarjetaService;
import Service.ServiceException;

public class Tarjeta {
    private int id;
    private double saldo;
    private String descripcion;

    private TarjetaService tarjetaService;

    public Tarjeta(int id, double saldo, String descripcion) {
        this.id = id;
        this.saldo = saldo;
        this.descripcion = descripcion;
        this.tarjetaService = new TarjetaService(); // Instancia del servicio
    }

    // Método para acreditar un monto en la tarjeta
    public void acreditarSaldo(double monto) {
        try {
            tarjetaService.acreditarSaldo(id, monto);
        } catch (ServiceException e) {
            System.err.println("Error al acreditar saldo en la tarjeta: " + e.getMessage());
        }
    }

    // Método para debitar un monto de la tarjeta
    public void debitarSaldo(double monto) {
        try {
            tarjetaService.debitarSaldo(id, monto);
        } catch (ServiceException e) {
            System.err.println("Error al debitar saldo de la tarjeta: " + e.getMessage());
        }
    }
}
