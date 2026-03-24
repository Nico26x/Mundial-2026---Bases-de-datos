package co.mundial2026.service;

import co.mundial2026.dao.JugadorDAO;
import co.mundial2026.model.Jugador;

import java.sql.SQLException;
import java.util.List;

public class JugadorService {
    
    private final JugadorDAO jugadorDAO;
    
    public JugadorService() {
        jugadorDAO = new JugadorDAO();
    }

    // Método para agregar un nuevo jugador
    public void agregarJugador(Jugador jugador) throws SQLException {
        jugadorDAO.agregarJugador(jugador);
    }

    // Método para obtener todos los jugadores
    public List<Jugador> obtenerJugadores() throws SQLException {
        return jugadorDAO.obtenerJugadores();
    }

    // Método para obtener jugadores por equipo
    public List<Jugador> obtenerJugadoresPorEquipo(int idEquipo) throws SQLException {
        List<Jugador> jugadores = jugadorDAO.obtenerJugadores();
        return jugadores.stream()
                        .filter(j -> j.getIdEquipo() == idEquipo)
                        .toList();
    }
}