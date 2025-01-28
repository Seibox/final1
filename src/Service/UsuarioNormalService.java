package Service;

import DAO.DAOException;

public class UsuarioNormalService {
    private UsuarioDAO usuarioDAO;

    public UsuarioNormalService() {
        this.usuarioDAO = new UsuarioDAO(); // Instancia del DAO
    }

    public void transferir(int usuarioId, int cuentaOrigenId, int cuentaDestinoId, double monto) throws ServiceException {
        try {
            usuarioDAO.transferir(usuarioId, cuentaOrigenId, cuentaDestinoId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al realizar la transferencia", e);
        }
    }

    public void usarTarjeta(int tarjetaId, double monto) throws ServiceException {
        try {
            usuarioDAO.usarTarjeta(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al usar la tarjeta", e);
        }
    }

    public void pagarTarjeta(int tarjetaId, double monto) throws ServiceException {
        try {
            usuarioDAO.pagarTarjeta(tarjetaId, monto);
        } catch (DAOException e) {
            throw new ServiceException("Error al pagar la tarjeta", e);
        }
    }
}
