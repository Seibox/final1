package DAO;

import DAO.DAOException;

public interface ITarjetaDAO {

    // Acredita un monto al saldo de una tarjeta
    void acreditarSaldo(int tarjetaId, double monto) throws DAOException;

    // Debita un monto del saldo de una tarjeta
    void debitarSaldo(int tarjetaId, double monto) throws DAOException;
}
