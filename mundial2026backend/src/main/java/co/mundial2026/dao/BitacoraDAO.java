package co.mundial2026.dao;

import co.mundial2026.model.Bitacora;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BitacoraDAO {

    public int registrarIngreso(int idUsuario) throws SQLException {
        String sql = "INSERT INTO Bitacora (id_usuario, fecha_hora_ingreso) VALUES (?, NOW())";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }

    public void registrarSalida(int idRegistro) throws SQLException {
        String sql = "UPDATE Bitacora SET fecha_hora_salida = NOW() WHERE id_registro = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRegistro);
            stmt.executeUpdate();
        }
    }

    public void agregarBitacora(Bitacora bitacora) throws SQLException {
        String sql = """
                INSERT INTO Bitacora (id_usuario, fecha_hora_ingreso, fecha_hora_salida)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bitacora.getIdUsuario());
            stmt.setTimestamp(2, Timestamp.valueOf(bitacora.getFechaHoraIngreso()));

            if (bitacora.getFechaHoraSalida() != null) {
                stmt.setTimestamp(3, Timestamp.valueOf(bitacora.getFechaHoraSalida()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }

            stmt.executeUpdate();
        }
    }

    public List<Bitacora> obtenerBitacoras() throws SQLException {
        List<Bitacora> bitacoras = new ArrayList<>();

        String sql = "SELECT * FROM Bitacora ORDER BY fecha_hora_ingreso DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Timestamp salida = rs.getTimestamp("fecha_hora_salida");

                LocalDateTime fechaSalida = null;
                if (salida != null) {
                    fechaSalida = salida.toLocalDateTime();
                }

                Bitacora bitacora = new Bitacora(
                        rs.getInt("id_registro"),
                        rs.getInt("id_usuario"),
                        rs.getTimestamp("fecha_hora_ingreso").toLocalDateTime(),
                        fechaSalida
                );

                bitacoras.add(bitacora);
            }
        }

        return bitacoras;
    }
}