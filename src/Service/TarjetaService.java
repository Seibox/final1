package Service;

import DAO.TarjetaDAO;
import DAO.DAOException;

public class TarjetaService {
    private TarjetaDAO tarjetaDAO;

    public TarjetaService() {
        this.tarjetaDAO = new TarjetaDAO(); // Instancia del DAO
    }

    public void acreditarSaldo(int tarjetaId, double monto) throws ServiceException {
        try {
            tarjetaDAO.acreditarSaldo(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al acreditar saldo a la tarjeta", e);
        }
    }

    public void debitarSaldo(int tarjetaId, double monto) throws ServiceException {
        try {
            tarjetaDAO.debitarSaldo(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al debitar saldo de la tarjeta", e);
        }
    }
}
