package co.mundial2026.dao;

import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public List<Object[]> reporteBitacoraUsuarios(String fechaInicio, String fechaFin) throws SQLException {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT 
                    u.nombre_usuario,
                    u.tipo_usuario,
                    b.fecha_hora_ingreso,
                    b.fecha_hora_salida,
                    TIMEDIFF(b.fecha_hora_salida, b.fecha_hora_ingreso) AS tiempo_sesion
                FROM Bitacora b
                INNER JOIN Usuario u ON b.id_usuario = u.id_usuario
                WHERE b.fecha_hora_ingreso BETWEEN ? AND ?
                   OR b.fecha_hora_salida BETWEEN ? AND ?
                ORDER BY b.fecha_hora_ingreso DESC;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fechaInicio);
            stmt.setString(2, fechaFin);
            stmt.setString(3, fechaInicio);
            stmt.setString(4, fechaFin);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new Object[]{
                            rs.getString("nombre_usuario"),
                            rs.getString("tipo_usuario"),
                            rs.getTimestamp("fecha_hora_ingreso"),
                            rs.getTimestamp("fecha_hora_salida"),
                            rs.getString("tiempo_sesion")
                    });
                }
            }
        }

        return resultados;
    }

    public List<Object[]> reporteJugadoresFiltrados(
            double pesoMin,
            double pesoMax,
            double estaturaMin,
            double estaturaMax,
            int idEquipo
    ) throws SQLException {

        List<Object[]> resultados = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT 
                    j.nombre AS jugador,
                    e.nombre AS equipo,
                    j.posicion,
                    j.peso,
                    j.estatura,
                    j.valor_mercado,
                    TIMESTAMPDIFF(YEAR, j.fecha_nacimiento, CURDATE()) AS edad
                FROM Jugador j
                INNER JOIN Equipo e ON j.id_equipo = e.id_equipo
                WHERE j.peso BETWEEN ? AND ?
                  AND j.estatura BETWEEN ? AND ?
                """);

        if (idEquipo > 0) {
            sql.append(" AND e.id_equipo = ? ");
        }

        sql.append(" ORDER BY j.valor_mercado DESC; ");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            stmt.setDouble(1, pesoMin);
            stmt.setDouble(2, pesoMax);
            stmt.setDouble(3, estaturaMin);
            stmt.setDouble(4, estaturaMax);

            if (idEquipo > 0) {
                stmt.setInt(5, idEquipo);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new Object[]{
                            rs.getString("jugador"),
                            rs.getString("equipo"),
                            rs.getString("posicion"),
                            rs.getDouble("peso"),
                            rs.getDouble("estatura"),
                            rs.getDouble("valor_mercado"),
                            rs.getInt("edad")
                    });
                }
            }
        }

        return resultados;
    }

    public List<Object[]> reporteValorJugadoresPorConfederacion(int idConfederacion) throws SQLException {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT 
                    c.siglas AS confederacion,
                    e.nombre AS equipo,
                    COUNT(j.id_jugador) AS num_jugadores,
                    IFNULL(SUM(j.valor_mercado), 0) AS valor_total_plantilla,
                    IFNULL(AVG(j.valor_mercado), 0) AS valor_promedio_jugador
                FROM Equipo e
                INNER JOIN Confederacion c ON e.id_confederacion = c.id_confederacion
                LEFT JOIN Jugador j ON e.id_equipo = j.id_equipo
                WHERE c.id_confederacion = ?
                GROUP BY c.siglas, e.id_equipo, e.nombre
                ORDER BY valor_total_plantilla DESC;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConfederacion);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new Object[]{
                            rs.getString("confederacion"),
                            rs.getString("equipo"),
                            rs.getInt("num_jugadores"),
                            rs.getDouble("valor_total_plantilla"),
                            rs.getDouble("valor_promedio_jugador")
                    });
                }
            }
        }

        return resultados;
    }

    public List<Object[]> reportePaisesPorPaisAnfitrion() throws SQLException {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT 
                    pa.nombre AS pais_anfitrion,
                    e.pais AS pais_equipo,
                    e.nombre AS equipo,
                    COUNT(DISTINCT p.id_partido) AS partidos_jugara
                FROM PaisAnfitrion pa
                INNER JOIN Ciudad ci ON pa.id_pais_anfitrion = ci.id_pais_anfitrion
                INNER JOIN Estadio es ON ci.id_ciudad = es.id_ciudad
                INNER JOIN Partido p ON es.id_estadio = p.id_estadio
                INNER JOIN Equipo e ON p.id_equipo_local = e.id_equipo
                                  OR p.id_equipo_visitante = e.id_equipo
                GROUP BY pa.nombre, e.pais, e.nombre
                ORDER BY pa.nombre, partidos_jugara DESC, e.nombre;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultados.add(new Object[]{
                        rs.getString("pais_anfitrion"),
                        rs.getString("pais_equipo"),
                        rs.getString("equipo"),
                        rs.getInt("partidos_jugara")
                });
            }
        }

        return resultados;
    }

    public List<Object[]> obtenerEquipos() throws SQLException {
        List<Object[]> equipos = new ArrayList<>();

        String sql = """
                SELECT id_equipo, nombre
                FROM Equipo
                ORDER BY nombre;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                equipos.add(new Object[]{
                        rs.getInt("id_equipo"),
                        rs.getString("nombre")
                });
            }
        }

        return equipos;
    }

    public List<Object[]> obtenerConfederaciones() throws SQLException {
        List<Object[]> confederaciones = new ArrayList<>();

        String sql = """
                SELECT id_confederacion, siglas
                FROM Confederacion
                ORDER BY siglas;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                confederaciones.add(new Object[]{
                        rs.getInt("id_confederacion"),
                        rs.getString("siglas")
                });
            }
        }

        return confederaciones;
    }
}