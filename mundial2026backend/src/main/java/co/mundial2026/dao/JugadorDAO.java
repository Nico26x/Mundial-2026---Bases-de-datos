package co.mundial2026.dao;

import co.mundial2026.model.Jugador;
import co.mundial2026.security.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JugadorDAO {

    public void agregarJugador(Jugador jugador) throws SQLException {
        String query = "INSERT INTO Jugador (nombre, fecha_nacimiento, posicion, peso, estatura, valor_mercado, id_equipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, jugador.getNombre());
            stmt.setDate(2, Date.valueOf(jugador.getFechaNacimiento()));
            stmt.setString(3, jugador.getPosicion());
            stmt.setDouble(4, jugador.getPeso());
            stmt.setDouble(5, jugador.getEstatura());
            stmt.setDouble(6, jugador.getValorMercado());
            stmt.setInt(7, jugador.getIdEquipo());
            stmt.executeUpdate();
        }
    }

    public List<Jugador> obtenerJugadores() throws SQLException {
        List<Jugador> jugadores = new ArrayList<>();
        String query = "SELECT * FROM Jugador";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Jugador jugador = new Jugador(
                        rs.getInt("id_jugador"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("posicion"),
                        rs.getDouble("peso"),
                        rs.getDouble("estatura"),
                        rs.getDouble("valor_mercado"),
                        rs.getInt("id_equipo")
                );
                jugadores.add(jugador);
            }
        }
        return jugadores;
    }

    public void eliminarJugador(int idJugador) throws SQLException {
        String sql = "DELETE FROM Jugador WHERE id_jugador = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idJugador);
            stmt.executeUpdate();
        }
    }

    public void actualizarJugador(Jugador jugador) throws SQLException {
        String sql = """
                UPDATE Jugador
                SET nombre = ?,
                    fecha_nacimiento = ?,
                    posicion = ?,
                    peso = ?,
                    estatura = ?,
                    valor_mercado = ?,
                    id_equipo = ?
                WHERE id_jugador = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jugador.getNombre());
            stmt.setDate(2, Date.valueOf(jugador.getFechaNacimiento()));
            stmt.setString(3, jugador.getPosicion());
            stmt.setDouble(4, jugador.getPeso());
            stmt.setDouble(5, jugador.getEstatura());
            stmt.setDouble(6, jugador.getValorMercado());
            stmt.setInt(7, jugador.getIdEquipo());
            stmt.setInt(8, jugador.getIdJugador());

            stmt.executeUpdate();
        }
    }
}