package co.mundial2026.service;

import co.mundial2026.dao.DirectorTecnicoDAO;
import co.mundial2026.model.DirectorTecnico;

import java.sql.SQLException;
import java.util.List;

public class DirectorTecnicoService {

    private final DirectorTecnicoDAO directorTecnicoDAO;

    public DirectorTecnicoService() {
        directorTecnicoDAO = new DirectorTecnicoDAO();
    }

    // Método para agregar un director técnico
    public void agregarDirectorTecnico(DirectorTecnico directorTecnico) throws SQLException {
        directorTecnicoDAO.agregarDirectorTecnico(directorTecnico);
    }

    // Método para obtener todos los directores técnicos
    public List<DirectorTecnico> obtenerDirectoresTecnicos() throws SQLException {
        return directorTecnicoDAO.obtenerDirectoresTecnicos();
    }
}