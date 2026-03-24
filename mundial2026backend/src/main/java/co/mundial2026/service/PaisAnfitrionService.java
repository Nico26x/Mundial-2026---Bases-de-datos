package co.mundial2026.service;

import co.mundial2026.dao.PaisAnfitrionDAO;
import co.mundial2026.model.PaisAnfitrion;

import java.sql.SQLException;
import java.util.List;

public class PaisAnfitrionService {

    private final PaisAnfitrionDAO paisAnfitrionDAO;

    public PaisAnfitrionService() {
        paisAnfitrionDAO = new PaisAnfitrionDAO();
    }

    // Método para agregar un país anfitrión
    public void agregarPaisAnfitrion(PaisAnfitrion paisAnfitrion) throws SQLException {
        paisAnfitrionDAO.agregarPaisAnfitrion(paisAnfitrion);
    }

    // Método para obtener todos los países anfitriones
    public List<PaisAnfitrion> obtenerPaisAnfitriones() throws SQLException {
        return paisAnfitrionDAO.obtenerPaisAnfitriones();
    }
}