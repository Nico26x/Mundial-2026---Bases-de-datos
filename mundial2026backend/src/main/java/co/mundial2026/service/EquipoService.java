package co.mundial2026.service;

import co.mundial2026.dao.EquipoDAO;
import co.mundial2026.model.Equipo;

import java.sql.SQLException;
import java.util.List;

public class EquipoService {
    
    private final EquipoDAO equipoDAO;
    
    public EquipoService() {
        equipoDAO = new EquipoDAO();
    }

    public void agregarEquipo(Equipo equipo) throws SQLException {
        equipoDAO.agregarEquipo(equipo);
    }

    public List<Equipo> obtenerEquipos() throws SQLException {
        return equipoDAO.obtenerEquipos();
    }
}