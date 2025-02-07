package Entidades;

import Service.TarjetaService;
import Service.ServiceException;

public class Tarjeta {
    private int id;
    private double saldo;
    private double limite;
    private String descripcion;

    private TarjetaService tarjetaService;

    public Tarjeta(int id, double saldo, String descripcion, int usuarioId, double limite) {
        this.id = id;
        this.saldo = saldo;
        this.descripcion = descripcion;
        this.limite = limite;
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

    public int getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TarjetaService getTarjetaService() {
        return tarjetaService;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTarjetaService(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getLimite() {
        return limite;
    }
}
