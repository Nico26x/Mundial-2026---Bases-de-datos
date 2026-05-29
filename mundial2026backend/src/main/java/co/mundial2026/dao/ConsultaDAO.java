package co.mundial2026.dao;

import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public List<Object[]> jugadorMasCostosoPorConfederacion() throws SQLException {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT 
                    c.siglas AS confederacion,
                    c.nombre AS nombre_confederacion,
                    j.nombre AS jugador,
                    e.nombre AS equipo,
                    j.posicion,
                    TIMESTAMPDIFF(YEAR, j.fecha_nacimiento, CURDATE()) AS edad,
                    j.valor_mercado
                FROM Jugador j
                INNER JOIN Equipo e ON j.id_equipo = e.id_equipo
                INNER JOIN Confederacion c ON e.id_confederacion = c.id_confederacion
                WHERE j.valor_mercado = (
                    SELECT MAX(j2.valor_mercado)
                    FROM Jugador j2
                    INNER JOIN Equipo e2 ON j2.id_equipo = e2.id_equipo
                    WHERE e2.id_confederacion = e.id_confederacion
                )
                ORDER BY c.siglas, j.valor_mercado DESC;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultados.add(new Object[]{
                        rs.getString("confederacion"),
                        rs.getString("nombre_confederacion"),
                        rs.getString("jugador"),
                        rs.getString("equipo"),
                        rs.getString("posicion"),
                        rs.getInt("edad"),
                        rs.getDouble("valor_mercado")
                });
            }
        }

        return resultados;
    }

    public List<Object[]> obtenerEstadios() throws SQLException {
        List<Object[]> estadios = new ArrayList<>();

        String sql = """
                SELECT id_estadio, nombre
                FROM Estadio
                ORDER BY nombre;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                estadios.add(new Object[]{
                        rs.getInt("id_estadio"),
                        rs.getString("nombre")
                });
            }
        }

        return estadios;
    }

    public List<Object[]> partidosPorEstadio(int idEstadio) throws SQLException {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT 
                    es.nombre AS estadio,
                    p.fecha_hora,
                    el.nombre AS local,
                    ev.nombre AS visitante,
                    g.nombre_grupo AS grupo,
                    p.goles_local,
                    p.goles_visitante
                FROM Partido p
                INNER JOIN Estadio es ON p.id_estadio = es.id_estadio
                INNER JOIN Equipo el ON p.id_equipo_local = el.id_equipo
                INNER JOIN Equipo ev ON p.id_equipo_visitante = ev.id_equipo
                INNER JOIN Grupo g ON p.id_grupo = g.id_grupo
                WHERE es.id_estadio = ?
                ORDER BY p.fecha_hora;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstadio);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultados.add(new Object[]{
                            rs.getString("estadio"),
                            rs.getTimestamp("fecha_hora"),
                            rs.getString("local"),
                            rs.getString("visitante"),
                            "Grupo " + rs.getString("grupo"),
                            rs.getInt("goles_local") + " - " + rs.getInt("goles_visitante")
                    });
                }
            }
        }

        return resultados;
    }

    public List<Object[]> equipoMasCostosoPorPaisAnfitrion() throws SQLException {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT 
                    pais_anfitrion,
                    equipo,
                    pais_equipo,
                    valor_total_equipo,
                    num_jugadores
                FROM (
                    SELECT 
                        pa.nombre AS pais_anfitrion,
                        e.nombre AS equipo,
                        e.pais AS pais_equipo,
                        IFNULL(SUM(j.valor_mercado), 0) AS valor_total_equipo,
                        COUNT(j.id_jugador) AS num_jugadores,
                        DENSE_RANK() OVER (
                            PARTITION BY pa.id_pais_anfitrion
                            ORDER BY IFNULL(SUM(j.valor_mercado), 0) DESC
                        ) AS ranking
                    FROM PaisAnfitrion pa
                    INNER JOIN Ciudad ci ON pa.id_pais_anfitrion = ci.id_pais_anfitrion
                    INNER JOIN Estadio es ON ci.id_ciudad = es.id_ciudad
                    INNER JOIN Partido p ON es.id_estadio = p.id_estadio
                    INNER JOIN Equipo e ON p.id_equipo_local = e.id_equipo
                                      OR p.id_equipo_visitante = e.id_equipo
                    LEFT JOIN Jugador j ON e.id_equipo = j.id_equipo
                    GROUP BY pa.id_pais_anfitrion, pa.nombre, e.id_equipo, e.nombre, e.pais
                ) AS ranking_equipos
                WHERE ranking = 1
                ORDER BY pais_anfitrion;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultados.add(new Object[]{
                        rs.getString("pais_anfitrion"),
                        rs.getString("equipo"),
                        rs.getString("pais_equipo"),
                        rs.getDouble("valor_total_equipo"),
                        rs.getInt("num_jugadores")
                });
            }
        }

        return resultados;
    }

    public List<Object[]> cantidadJugadoresMenores21PorEquipo() throws SQLException {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT 
                    e.nombre AS equipo,
                    e.pais,
                    COUNT(j.id_jugador) AS cantidad_menores_21
                FROM Equipo e
                LEFT JOIN Jugador j ON e.id_equipo = j.id_equipo
                    AND TIMESTAMPDIFF(YEAR, j.fecha_nacimiento, CURDATE()) < 21
                GROUP BY e.id_equipo, e.nombre, e.pais
                ORDER BY cantidad_menores_21 DESC, e.nombre;
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultados.add(new Object[]{
                        rs.getString("equipo"),
                        rs.getString("pais"),
                        rs.getInt("cantidad_menores_21")
                });
            }
        }

        return resultados;
    }
}