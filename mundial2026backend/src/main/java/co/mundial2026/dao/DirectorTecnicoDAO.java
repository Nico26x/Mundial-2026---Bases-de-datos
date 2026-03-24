package co.mundial2026.dao;

import co.mundial2026.model.DirectorTecnico;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectorTecnicoDAO {

    // Método para agregar un director técnico
    public void agregarDirectorTecnico(DirectorTecnico directorTecnico) throws SQLException {
        String query = "INSERT INTO DirectorTecnico (nombre, nacionalidad, fecha_nacimiento, id_equipo) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, directorTecnico.getNombre());
            stmt.setString(2, directorTecnico.getNacionalidad());
            stmt.setDate(3, Date.valueOf(directorTecnico.getFechaNacimiento()));
            stmt.setInt(4, directorTecnico.getIdEquipo());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los directores técnicos
    public List<DirectorTecnico> obtenerDirectoresTecnicos() throws SQLException {
        List<DirectorTecnico> directores = new ArrayList<>();
        String query = "SELECT * FROM DirectorTecnico";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                DirectorTecnico director = new DirectorTecnico(
                        rs.getInt("id_dt"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getInt("id_equipo")
                );
                directores.add(director);
            }
        }
        return directores;
    }
}