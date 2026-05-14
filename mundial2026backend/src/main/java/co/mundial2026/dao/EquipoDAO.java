package co.mundial2026.dao;

import co.mundial2026.model.Equipo;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public void eliminarEquipo(int idEquipo) throws SQLException {
    String sql = "DELETE FROM Equipo WHERE id_equipo = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idEquipo);
        stmt.executeUpdate();
    }
}

    public void actualizarEquipo(Equipo equipo) throws SQLException {
        String sql = """
                UPDATE Equipo
                SET nombre = ?,
                    pais = ?,
                    valor_total_equipo = ?,
                    id_confederacion = ?
                WHERE id_equipo = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getPais());
            stmt.setDouble(3, equipo.getValorTotalEquipo());
            stmt.setInt(4, equipo.getIdConfederacion());
            stmt.setInt(5, equipo.getIdEquipo());

            stmt.executeUpdate();
        }
    }
}