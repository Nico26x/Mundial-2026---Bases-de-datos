package co.mundial2026.security;

import co.mundial2026.dao.UsuarioDAO;
import co.mundial2026.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
        usuarioDAO = new UsuarioDAO();

        try {
            usuarioDAO.agregarUsuario(new Usuario(1, "admin", "adminhashedpassword", "Administrador", LocalDateTime.now()));
            usuarioDAO.agregarUsuario(new Usuario(2, "user", "userhashedpassword", "Tradicional", LocalDateTime.now()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAutenticarUsuarioCorrecto() throws SQLException {
        boolean result = authService.autenticar("admin", "adminhashedpassword");
        assertTrue(result, "El login debería ser exitoso con credenciales correctas.");
    }

    @Test
    void testAutenticarUsuarioIncorrecto() throws SQLException {
        boolean result = authService.autenticar("admin", "wrongpassword");
        assertFalse(result, "El login debería fallar con contraseñas incorrectas.");
    }

    @Test
    void testTieneRolAdministrador() throws SQLException {
        authService.autenticar("admin", "adminhashedpassword");
        assertTrue(authService.tieneRol("Administrador"), "El usuario debería tener el rol de Administrador.");
    }

    @Test
    void testTieneRolUsuarioTradicional() throws SQLException {
        authService.autenticar("user", "userhashedpassword");
        assertTrue(authService.tieneRol("Tradicional"), "El usuario debería tener el rol de Tradicional.");
    }

    @Test
    void testCerrarSesion() throws SQLException {
        authService.autenticar("admin", "adminhashedpassword");
        authService.cerrarSesion();
        assertNull(authService.getUsuarioActual(), "El usuario debería estar desconectado después de cerrar sesión.");
    }
}