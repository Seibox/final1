package DAO;

import Entidades.Cuenta;
import DAO.DAOException;

import java.util.List;

public interface ICuentaDAO {
    void acreditar(String cbu, double monto) throws DAOException;
    void debitar(String cbu, double monto) throws DAOException;
    Cuenta mostrarInfo(String cbu) throws DAOException;
    public Cuenta mostrarInfoPorAlias(String alias) throws DAOException;
    public List<Cuenta> obtenerCuentasPorUsuario(int usuarioId) throws DAOException;
}