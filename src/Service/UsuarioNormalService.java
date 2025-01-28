package Service;

import DAO.DAOException;
import DAO.UsuarioNormalDAO;

public class UsuarioNormalService {
    private UsuarioNormalDAO usuarioNormalDAO;

    public UsuarioNormalService() {
        this.usuarioNormalDAO = new UsuarioNormalDAO(); // Instancia del DAO
    }

    public void transferir(int usuarioId, int cuentaOrigenId, int cuentaDestinoId, double monto) throws ServiceException {
        try {
            usuarioNormalDAO.transferir(usuarioId, cuentaOrigenId, cuentaDestinoId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al realizar la transferencia", e);
        }
    }

    public void usarTarjeta(int tarjetaId, double monto) throws ServiceException {
        try {
            usuarioNormalDAO.usarTarjeta(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al usar la tarjeta", e);
        }
    }

    public void pagarTarjeta(int tarjetaId, double monto) throws ServiceException {
        try {
            usuarioNormalDAO.pagarTarjeta(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al pagar la tarjeta", e);
        }
    }
}