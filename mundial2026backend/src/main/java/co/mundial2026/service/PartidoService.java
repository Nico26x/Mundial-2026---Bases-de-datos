package co.mundial2026.service;

import co.mundial2026.dao.PartidoDAO;
import co.mundial2026.model.Partido;

import java.sql.SQLException;
import java.util.List;

public class PartidoService {

    private final PartidoDAO partidoDAO;

    public PartidoService() {
        partidoDAO = new PartidoDAO();
    }

    // Método para agregar un partido
    public void agregarPartido(Partido partido) throws SQLException {
        partidoDAO.agregarPartido(partido);
    }

    // Método para obtener todos los partidos
    public List<Partido> obtenerPartidos() throws SQLException {
        return partidoDAO.obtenerPartidos();
    }
}