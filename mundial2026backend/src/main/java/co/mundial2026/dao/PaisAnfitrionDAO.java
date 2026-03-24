package co.mundial2026.dao;

import co.mundial2026.model.PaisAnfitrion;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaisAnfitrionDAO {

    // Método para agregar un país anfitrión
    public void agregarPaisAnfitrion(PaisAnfitrion paisAnfitrion) throws SQLException {
        String query = "INSERT INTO PaisAnfitrion (nombre) VALUES (?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, paisAnfitrion.getNombre());
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los países anfitriones
    public List<PaisAnfitrion> obtenerPaisAnfitriones() throws SQLException {
        List<PaisAnfitrion> paisesAnfitriones = new ArrayList<>();
        String query = "SELECT * FROM PaisAnfitrion";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                PaisAnfitrion paisAnfitrion = new PaisAnfitrion(
                        rs.getInt("id_pais_anfitrion"),
                        rs.getString("nombre")
                );
                paisesAnfitriones.add(paisAnfitrion);
            }
        }
        return paisesAnfitriones;
    }
}