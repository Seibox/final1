package Service;

import DAO.CuentaDAO;
import DAO.DAOException;

public class CuentaService {
    private CuentaDAO cuentaDAO;

    public CuentaService() {
        this.cuentaDAO = new CuentaDAO(); // Instancia del DAO
    }

    public void consultarSaldo(int cuentaId) throws ServiceException {
        try {
            cuentaDAO.consultarSaldo(cuentaId);
        } catch (DAOException e) {
            throw new ServiceException("Error al consultar el saldo de la cuenta", e);
        }
    }

    public void actualizarSaldo(int cuentaId, double nuevoSaldo) throws ServiceException {
        try {
            cuentaDAO.actualizarSaldo(cuentaId, nuevoSaldo);
        } catch (DAOException e) {
            throw new ServiceException("Error al actualizar el saldo de la cuenta", e);
        }
    }
}
