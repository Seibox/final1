package Service;

import DAO.CuentaDAO;
import DAO.DAOException;
import Entidades.Cuenta;

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
}