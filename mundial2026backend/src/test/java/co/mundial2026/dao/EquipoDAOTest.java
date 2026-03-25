package co.mundial2026.dao;

import co.mundial2026.model.Equipo;
import co.mundial2026.model.Confederacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EquipoDAOTest {

    private EquipoDAO equipoDAO;
    private ConfederacionDAO confederacionDAO;

    @BeforeEach
    void setUp() {
        equipoDAO = new EquipoDAO();
        confederacionDAO = new ConfederacionDAO();
    }

    @Test
    void testAgregarEquipo() throws SQLException {
        Confederacion confederacion = new Confederacion(1, "Confederación Example", "CE");
        confederacionDAO.agregarConfederacion(confederacion);

        Equipo nuevoEquipo = new Equipo(0, "Equipo Nuevo", "PaísX", 5000000, 1);
        equipoDAO.agregarEquipo(nuevoEquipo);

        List<Equipo> equipos = equipoDAO.obtenerEquipos();
        assertTrue(equipos.size() > 0, "Debería haber al menos un equipo.");
    }
}