package co.mundial2026.service;

import co.mundial2026.dao.GrupoDAO;
import co.mundial2026.model.Grupo;

import java.sql.SQLException;
import java.util.List;

public class GrupoService {

    private final GrupoDAO grupoDAO;

    public GrupoService() {
        grupoDAO = new GrupoDAO();
    }

    // Método para agregar un grupo
    public void agregarGrupo(Grupo grupo) throws SQLException {
        grupoDAO.agregarGrupo(grupo);
    }

    // Método para obtener todos los grupos
    public List<Grupo> obtenerGrupos() throws SQLException {
        return grupoDAO.obtenerGrupos();
    }
}