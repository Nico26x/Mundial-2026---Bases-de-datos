package co.mundial2026.service;

import co.mundial2026.dao.EstadioDAO;
import co.mundial2026.model.Estadio;

import java.sql.SQLException;
import java.util.List;

public class EstadioService {

    private final EstadioDAO estadioDAO;

    public EstadioService() {
        estadioDAO = new EstadioDAO();
    }

    // Método para agregar un estadio
    public void agregarEstadio(Estadio estadio) throws SQLException {
        estadioDAO.agregarEstadio(estadio);
    }

    // Método para obtener todos los estadios
    public List<Estadio> obtenerEstadios() throws SQLException {
        return estadioDAO.obtenerEstadios();
    }
}