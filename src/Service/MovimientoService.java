package Service;

import DAO.MovimientoDAO;
import DAO.DAOException;

public class MovimientoService {
    private MovimientoDAO movimientoDAO;

    public MovimientoService() {
        this.movimientoDAO = new MovimientoDAO(); // Instancia del DAO
    }

    public void emitirTotalMovimientos(int movimientoId) throws ServiceException {
        try {
            movimientoDAO.emitirTotalMovimientos(movimientoId);
        } catch (DAOException e) {
            throw new ServiceException("Error al emitir el total de movimientos", e);
        }
    }

    public void emitirMovimientosTarjeta(int tarjetaId) throws ServiceException {
        try {
            movimientoDAO.emitirMovimientosTarjeta(tarjetaId);
        } catch (DAOException e) {
            throw new ServiceException("Error al emitir movimientos de la tarjeta", e);
        }
    }

    public void emitirMovimientosUsuario(int usuarioId) throws ServiceException {
        try {
            movimientoDAO.emitirMovimientosUsuario(usuarioId);
        } catch (DAOException e) {
            throw new ServiceException("Error al emitir movimientos del usuario", e);
        }
    }
}
