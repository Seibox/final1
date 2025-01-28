package Service;

import DAO.DAOException;
import DAO.ITarjetaDAO;
import DAO.TarjetaDAO;

public class TarjetaService {

    private final ITarjetaDAO tarjetaDAO;

    // Constructor: permite inyectar el DAO o usar el DAO por defecto
    public TarjetaService() {
        this.tarjetaDAO = new TarjetaDAO();
    }

    public TarjetaService(ITarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;
    }

    /**
     * Acredita un monto al saldo de una tarjeta.
     *
     * @param tarjetaId ID de la tarjeta a la que se acreditará el saldo.
     * @param monto     Monto a acreditar.
     * @throws ServiceException Si ocurre un error en el DAO.
     */
    public void acreditarSaldo(int tarjetaId, double monto) throws ServiceException {
        if (monto <= 0) {
            throw new ServiceException("El monto a acreditar debe ser mayor a 0.");
        }
        try {
            tarjetaDAO.acreditarSaldo(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al acreditar saldo en la tarjeta con ID: " + tarjetaId, e);
        }
    }

    /**
     * Debita un monto del saldo de una tarjeta.
     *
     * @param tarjetaId ID de la tarjeta de la que se debitará el saldo.
     * @param monto     Monto a debitar.
     * @throws ServiceException Si ocurre un error en el DAO o si el monto no es válido.
     */
    public void debitarSaldo(int tarjetaId, double monto) throws ServiceException {
        if (monto <= 0) {
            throw new ServiceException("El monto a debitar debe ser mayor a 0.");
        }
        try {
            tarjetaDAO.debitarSaldo(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al debitar saldo de la tarjeta con ID: " + tarjetaId, e);
        }
    }
}
