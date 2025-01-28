package Entidades;

import Service.CuentaService;
import Service.ServiceException;

public class Cuenta {
    private String cbu;
    private String alias;
    private String tipoCuenta; // "Caja de ahorro" o "Cuenta corriente"
    private String moneda; // "Pesos" o "USD"
    private double saldo;

    private CuentaService cuentaService;

    public Cuenta(String cbu, String alias, String tipoCuenta, String moneda, double saldo) {
        this.cbu = cbu;
        this.alias = alias;
        this.tipoCuenta = tipoCuenta;
        this.moneda = moneda;
        this.saldo = saldo;
        this.cuentaService = new CuentaService(); // Instancia del servicio
    }

    // Método para acreditar un monto a la cuenta
    public void acreditar(double monto) {
        try {
            cuentaService.acreditar(cbu, monto);
        } catch (ServiceException e) {
            System.err.println("Error al acreditar el monto en la cuenta: " + e.getMessage());
        }
    }

    // Método para debitar un monto de la cuenta
    public void debitar(double monto) {
        try {
            cuentaService.debitar(cbu, monto);
        } catch (ServiceException e) {
            System.err.println("Error al debitar el monto de la cuenta: " + e.getMessage());
        }
    }

    // Método para mostrar la información de la cuenta
    public void mostrarInfo() {
        try {
            cuentaService.mostrarInfo(cbu);
        } catch (ServiceException e) {
            System.err.println("Error al obtener la información de la cuenta: " + e.getMessage());
        }
    }
}
