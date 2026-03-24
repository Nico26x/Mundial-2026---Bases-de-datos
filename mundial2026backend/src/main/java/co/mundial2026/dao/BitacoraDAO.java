package co.mundial2026.dao;

import co.mundial2026.model.Bitacora;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BitacoraDAO {

    // Método para agregar un registro en la bitacora
    public void agregarBitacora(Bitacora bitacora) throws SQLException {
        String query = "INSERT INTO Bitacora (id_usuario, fecha_hora_ingreso, fecha_hora_salida) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, bitacora.getIdUsuario());
            stmt.setTimestamp(2, Timestamp.valueOf(bitacora.getFechaHoraIngreso()));
            stmt.setTimestamp(3, bitacora.getFechaHoraSalida() != null ? Timestamp.valueOf(bitacora.getFechaHoraSalida()) : null);
            stmt.executeUpdate();
        }
    }

    // Método para obtener todos los registros de la bitacora
    public List<Bitacora> obtenerBitacoras() throws SQLException {
        List<Bitacora> bitacoras = new ArrayList<>();
        String query = "SELECT * FROM Bitacora";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Bitacora bitacora = new Bitacora(
                        rs.getInt("id_registro"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_hora_ingreso").toLocalDateTime(),
                        rs.getTimestamp("fecha_hora_salida") != null ? rs.getTimestamp("fecha_hora_salida").toLocalDateTime() : null
                );
                bitacoras.add(bitacora);
            }
        }
        return bitacoras;
    }
}