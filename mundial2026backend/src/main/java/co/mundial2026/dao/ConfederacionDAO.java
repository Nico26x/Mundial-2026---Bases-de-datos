package co.mundial2026.dao;

import co.mundial2026.model.Confederacion;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfederacionDAO {

    public void agregarConfederacion(Confederacion confederacion) throws SQLException {
        String query = "INSERT INTO Confederacion (nombre, siglas) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, confederacion.getNombre());
            stmt.setString(2, confederacion.getSiglas());
            stmt.executeUpdate();
        }
    }

    public List<Confederacion> obtenerConfederaciones() throws SQLException {
        List<Confederacion> confederaciones = new ArrayList<>();
        String query = "SELECT * FROM Confederacion";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Confederacion confederacion = new Confederacion(
                        rs.getInt("id_confederacion"),
                        rs.getString("nombre"),
                        rs.getString("siglas")
                );
                confederaciones.add(confederacion);
            }
        }
        return confederaciones;
    }
}