package Service;

import DAO.CuentaDAO;
import DAO.DAOException;
import Entidades.Cuenta;

import java.util.List;

public class CuentaService {
    private CuentaDAO cuentaDAO;

    public CuentaService() {
        this.cuentaDAO = new CuentaDAO(); // Instancia del DAO
    }

    // Método para acreditar un monto a la cuenta
    public void acreditar(String cbu, double monto) throws ServiceException {
        try {
            cuentaDAO.acreditar(cbu, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al acreditar el monto en la cuenta con CBU: " + cbu, e);
        }
    }

    // Método para debitar un monto de la cuenta
    public void debitar(String cbu, double monto) throws ServiceException {
        try {
            cuentaDAO.debitar(cbu, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al debitar el monto de la cuenta con CBU: " + cbu, e);
        }
    }

    // Método para mostrar la información de la cuenta
    public Cuenta mostrarInfo(String cbu) throws ServiceException {
        try {
            return cuentaDAO.mostrarInfo(cbu);
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener la información de la cuenta con CBU: " + cbu, e);
        }
    }

    public List<Cuenta> obtenerCuentasPorUsuario(int usuarioId) throws ServiceException {
        try {
            return cuentaDAO.obtenerCuentasPorUsuario(usuarioId);
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener las cuentas del usuario con ID: " + usuarioId, e);
        }
    }

    public Cuenta mostrarInfoPorAlias(String alias) throws ServiceException {
        try {
            return cuentaDAO.mostrarInfoPorAlias(alias);
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener la información de la cuenta con alias: " + alias, e);
        }
    }

}