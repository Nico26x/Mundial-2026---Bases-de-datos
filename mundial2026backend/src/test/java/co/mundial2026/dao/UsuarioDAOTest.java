package co.mundial2026.dao;

import co.mundial2026.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDAOTest {

    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() {
        usuarioDAO = new UsuarioDAO();
    }

    @Test
    void testAgregarUsuario() throws SQLException {
        Usuario nuevoUsuario = new Usuario(0, "nuevo_usuario", "hashedpassword", "Tradicional", LocalDateTime.now());
        usuarioDAO.agregarUsuario(nuevoUsuario);

        Usuario usuarioRecuperado = usuarioDAO.obtenerUsuarioPorNombre("nuevo_usuario");
        assertNotNull(usuarioRecuperado, "El usuario debería haber sido agregado correctamente.");
        assertEquals("nuevo_usuario", usuarioRecuperado.getNombreUsuario(), "El nombre del usuario debería coincidir.");
    }

    @Test
    void testObtenerUsuarioPorNombre() throws SQLException {
        Usuario usuarioRecuperado = usuarioDAO.obtenerUsuarioPorNombre("admin");
        assertNotNull(usuarioRecuperado, "El usuario debería ser recuperado correctamente.");
        assertEquals("admin", usuarioRecuperado.getNombreUsuario(), "El nombre del usuario debería ser 'admin'.");
    }
}