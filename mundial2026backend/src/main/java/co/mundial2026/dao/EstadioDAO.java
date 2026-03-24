package co.mundial2026.dao;

import co.mundial2026.model.Estadio;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadioDAO {

    // Método para agregar un estadio
    public void agregarEstadio(Estadio estadio) throws SQLException {
        String query = "INSERT INTO Estadio (nombre, capacidad, id_ciudad) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, estadio.getNombre());
            stmt.setInt(2, estadio.getCapacidad());
            stmt.setInt(3, estadio.getIdCiudad());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los estadios
    public List<Estadio> obtenerEstadios() throws SQLException {
        List<Estadio> estadios = new ArrayList<>();
        String query = "SELECT * FROM Estadio";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Estadio estadio = new Estadio(
                        rs.getInt("id_estadio"),
                        rs.getString("nombre"),
                        rs.getInt("capacidad"),
                        rs.getInt("id_ciudad")
                );
                estadios.add(estadio);
            }
        }
        return estadios;
    }
}