package co.mundial2026.dao;

import co.mundial2026.model.Partido;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    // Método para agregar un partido
    public void agregarPartido(Partido partido) throws SQLException {
        String query = "INSERT INTO Partido (fecha_hora, id_estadio, id_grupo, id_equipo_local, id_equipo_visitante, goles_local, goles_visitante) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(partido.getFechaHora()));
            stmt.setInt(2, partido.getIdEstadio());
            stmt.setInt(3, partido.getIdGrupo());
            stmt.setInt(4, partido.getIdEquipoLocal());
            stmt.setInt(5, partido.getIdEquipoVisitante());
            stmt.setInt(6, partido.getGolesLocal());
            stmt.setInt(7, partido.getGolesVisitante());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los partidos
    public List<Partido> obtenerPartidos() throws SQLException {
        List<Partido> partidos = new ArrayList<>();
        String query = "SELECT * FROM Partido";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Partido partido = new Partido(
                        rs.getInt("id_partido"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getInt("id_estadio"),
                        rs.getInt("id_grupo"),
                        rs.getInt("id_equipo_local"),
                        rs.getInt("id_equipo_visitante"),
                        rs.getInt("goles_local"),
                        rs.getInt("goles_visitante")
                );
                partidos.add(partido);
            }
        }
        return partidos;
    }
}