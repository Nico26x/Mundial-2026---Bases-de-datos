package co.mundial2026.dao;

import co.mundial2026.model.Partido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartidoDAOTest {

    private PartidoDAO partidoDAO;

    @BeforeEach
    void setUp() {
        partidoDAO = new PartidoDAO();
    }

    @Test
    void testAgregarPartido() throws SQLException {
        Partido nuevoPartido = new Partido(0, LocalDateTime.now(), 1, 1, 1, 2, 2, 2);
        partidoDAO.agregarPartido(nuevoPartido);

        List<Partido> partidos = partidoDAO.obtenerPartidos();
        assertTrue(partidos.size() > 0, "Debería haber al menos un partido en la base de datos.");
    }
}