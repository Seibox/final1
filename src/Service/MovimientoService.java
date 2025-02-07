package Service;

import DAO.MovimientoDAO;
import DAO.DAOException;
import Entidades.Movimiento;
import java.util.List;

public class MovimientoService {
    private MovimientoDAO movimientoDAO;

    public MovimientoService() {
        this.movimientoDAO = new MovimientoDAO(); // Instancia del DAO
    }

    // Método para emitir el total de movimientos de un usuario
    public double emitirTotalMovimientos(int usuarioId) throws ServiceException {
        try {
            return movimientoDAO.obtenerTotalMovimientos(usuarioId);
        } catch (DAOException e) {
            throw new ServiceException("Error al emitir el total de movimientos", e);
        }
    }

    // Método para emitir los movimientos de una tarjeta
    public List<Movimiento> emitirMovimientosTarjeta(int tarjetaId) throws ServiceException {
        try {
            return movimientoDAO.obtenerMovimientosPorTarjeta(tarjetaId);
        } catch (DAOException e) {
            throw new ServiceException("Error al emitir los movimientos de la tarjeta", e);
        }
    }

    // Método para emitir los movimientos de un usuario
    public List<Movimiento> emitirMovimientosUsuario(int usuarioId) throws ServiceException {
        try {
            return movimientoDAO.obtenerMovimientosPorUsuario(usuarioId);
        } catch (DAOException e) {
            throw new ServiceException("Error al emitir los movimientos del usuario", e);
        }
    }

    // Método para emitir todos los movimientos
    public List<Movimiento> emitirTodosLosMovimientos() throws ServiceException {
        try {
            return movimientoDAO.obtenerTodosLosMovimientos();
        } catch (DAOException e) {
            throw new ServiceException("Error al emitir todos los movimientos", e);
        }
    }

    // Método para crear un movimiento
    public void crearMovimiento(Movimiento movimiento) throws ServiceException {
        try {
            movimientoDAO.crearMovimiento(movimiento);
        } catch (DAOException e) {
            throw new ServiceException("Error al crear el movimiento", e);
        }
    }
}