package co.mundial2026.service;

import co.mundial2026.model.Usuario;
import co.mundial2026.dao.UsuarioDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private UsuarioService usuarioService;
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() {
        usuarioService = new UsuarioService();
        usuarioDAO = new UsuarioDAO();
    }

    @Test
    void testAgregarUsuario() throws SQLException {
        // Asegúrate de pasar LocalDateTime.now() en lugar de null
        Usuario nuevoUsuario = new Usuario(0, "usuarioNuevo", "hashedpassword", "Tradicional", LocalDateTime.now());
        usuarioService.agregarUsuario(nuevoUsuario);

        Usuario usuarioRecuperado = usuarioDAO.obtenerUsuarioPorNombre("usuarioNuevo");
        assertNotNull(usuarioRecuperado, "El usuario debería haber sido agregado correctamente.");
    }

    @Test
    void testValidarLogin() throws SQLException {
        Usuario nuevoUsuario = new Usuario(0, "usuarioNuevo", "hashedpassword", "Tradicional", LocalDateTime.now());
        usuarioDAO.agregarUsuario(nuevoUsuario);

        boolean loginValido = usuarioService.validarLogin("usuarioNuevo", "hashedpassword");
        assertTrue(loginValido, "El login debería ser válido con las credenciales correctas.");
    }

    @Test
    void testValidarLoginIncorrecto() throws SQLException {
        boolean loginInvalido = usuarioService.validarLogin("usuarioNuevo", "wrongpassword");
        assertFalse(loginInvalido, "El login debería ser inválido con credenciales incorrectas.");
    }
}