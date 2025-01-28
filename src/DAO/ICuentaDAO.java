package DAO;

import Entidades.Cuenta;
import DAO.DAOException;

public interface ICuentaDAO {
    void acreditar(String cbu, double monto) throws DAOException;
    void debitar(String cbu, double monto) throws DAOException;
    Cuenta mostrarInfo(String cbu) throws DAOException;
}