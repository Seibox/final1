package DAO;

import Entidades.Tarjeta;

import java.util.List;

public interface ITarjetaDAO {

    // Acredita un monto al saldo de una tarjeta
    void acreditarSaldo(int tarjetaId, double monto) throws DAOException;

    // Debita un monto del saldo de una tarjeta
    void debitarSaldo(int tarjetaId, double monto) throws DAOException;

    List<Tarjeta> obtenerTarjetasPorUsuario(int usuarioId) throws DAOException;
}
