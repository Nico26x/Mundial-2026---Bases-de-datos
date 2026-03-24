package co.mundial2026.dao;

import co.mundial2026.model.Equipo;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public void agregarEquipo(Equipo equipo) throws SQLException {
        String query = "INSERT INTO Equipo (nombre, pais, valor_total_equipo, id_confederacion) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getPais());
            stmt.setDouble(3, equipo.getValorTotalEquipo());
            stmt.setInt(4, equipo.getIdConfederacion());
            stmt.executeUpdate();
        }
    }

    public List<Equipo> obtenerEquipos() throws SQLException {
        List<Equipo> equipos = new ArrayList<>();
        String query = "SELECT * FROM Equipo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Equipo equipo = new Equipo(
                        rs.getInt("id_equipo"),
                        rs.getString("nombre"),
                        rs.getString("pais"),
                        rs.getDouble("valor_total_equipo"),
                        rs.getInt("id_confederacion")
                );
                equipos.add(equipo);
            }
        }
        return equipos;
    }
}