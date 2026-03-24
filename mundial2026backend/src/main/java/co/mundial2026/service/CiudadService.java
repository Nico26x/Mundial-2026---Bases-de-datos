package co.mundial2026.service;

import co.mundial2026.dao.CiudadDAO;
import co.mundial2026.model.Ciudad;

import java.sql.SQLException;
import java.util.List;

public class CiudadService {

    private final CiudadDAO ciudadDAO;

    public CiudadService() {
        ciudadDAO = new CiudadDAO();
    }

    // Método para agregar una ciudad
    public void agregarCiudad(Ciudad ciudad) throws SQLException {
        ciudadDAO.agregarCiudad(ciudad);
    }

    // Método para obtener todas las ciudades
    public List<Ciudad> obtenerCiudades() throws SQLException {
        return ciudadDAO.obtenerCiudades();
    }
}