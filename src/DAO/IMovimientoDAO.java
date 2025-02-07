package DAO;

import Entidades.Movimiento;

import java.util.List;

public interface IMovimientoDAO {
    List<Movimiento> obtenerMovimientosPorUsuario(int usuarioId) throws DAOException;
    List<Movimiento> obtenerMovimientosPorTarjeta(int tarjetaId) throws DAOException;
    double obtenerTotalMovimientos(int usuarioId) throws DAOException;
    void crearMovimiento(Movimiento movimiento) throws DAOException;

    List<Movimiento> obtenerTodosLosMovimientos() throws DAOException;
}