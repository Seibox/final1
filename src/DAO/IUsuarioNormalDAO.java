package DAO;

import Entidades.UsuarioNormal;
import java.util.List;

public interface IUsuarioNormalDAO {
    void transferir(int usuarioId, int cuentaOrigenId, int cuentaDestinoId, double monto) throws DAOException;
    void usarTarjeta(int tarjetaId, double monto) throws DAOException;
    void pagarTarjeta(int tarjetaId, double monto) throws DAOException;
    List<UsuarioNormal> listarUsuariosNormales() throws DAOException;
}