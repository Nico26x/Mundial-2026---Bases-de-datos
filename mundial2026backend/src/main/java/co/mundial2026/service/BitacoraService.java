package co.mundial2026.service;

import co.mundial2026.dao.BitacoraDAO;
import co.mundial2026.model.Bitacora;

import java.sql.SQLException;
import java.util.List;

public class BitacoraService {

    private final BitacoraDAO bitacoraDAO;

    public BitacoraService() {
        bitacoraDAO = new BitacoraDAO();
    }

    // Método para agregar un registro en la bitacora
    public void agregarBitacora(Bitacora bitacora) throws SQLException {
        bitacoraDAO.agregarBitacora(bitacora);
    }

    // Método para obtener todos los registros de la bitacora
    public List<Bitacora> obtenerBitacoras() throws SQLException {
        return bitacoraDAO.obtenerBitacoras();
    }
}