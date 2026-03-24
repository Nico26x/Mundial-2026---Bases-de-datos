package co.mundial2026.service;

import co.mundial2026.dao.ConfederacionDAO;
import co.mundial2026.model.Confederacion;

import java.sql.SQLException;
import java.util.List;

public class ConfederacionService {
    
    private final ConfederacionDAO confederacionDAO;
    
    public ConfederacionService() {
        confederacionDAO = new ConfederacionDAO();
    }

    public void agregarConfederacion(Confederacion confederacion) throws SQLException {
        confederacionDAO.agregarConfederacion(confederacion);
    }

    public List<Confederacion> obtenerConfederaciones() throws SQLException {
        return confederacionDAO.obtenerConfederaciones();
    }
}