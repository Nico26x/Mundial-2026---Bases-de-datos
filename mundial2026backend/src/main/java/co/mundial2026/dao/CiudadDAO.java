package co.mundial2026.dao;

import co.mundial2026.model.Ciudad;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiudadDAO {

    // Método para agregar una ciudad
    public void agregarCiudad(Ciudad ciudad) throws SQLException {
        String query = "INSERT INTO Ciudad (nombre, id_pais_anfitrion) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, ciudad.getNombre());
            stmt.setInt(2, ciudad.getIdPaisAnfitrion());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todas las ciudades
    public List<Ciudad> obtenerCiudades() throws SQLException {
        List<Ciudad> ciudades = new ArrayList<>();
        String query = "SELECT * FROM Ciudad";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Ciudad ciudad = new Ciudad(
                        rs.getInt("id_ciudad"),
                        rs.getString("nombre"),
                        rs.getInt("id_pais_anfitrion")
                );
                ciudades.add(ciudad);
            }
        }
        return ciudades;
    }
}