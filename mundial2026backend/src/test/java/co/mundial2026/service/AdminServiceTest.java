package co.mundial2026.service;

import co.mundial2026.model.Usuario;
import co.mundial2026.security.AuthService;
import co.mundial2026.dao.UsuarioDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    private AdminService adminService; // Asegúrate de que esta variable esté declarada
    private AuthService authService;
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    void setUp() {
        // Inicializar las dependencias antes de cada prueba
        adminService = new AdminService(); // Asegúrate de que adminService esté inicializado
        authService = new AuthService();
        usuarioDAO = new UsuarioDAO();

        try {
            // Asegúrate de que 'fechaCreacion' se inicialice correctamente
            usuarioDAO.agregarUsuario(new Usuario(1, "admin", "adminhashedpassword", "Administrador", LocalDateTime.now()));
            usuarioDAO.agregarUsuario(new Usuario(2, "user", "userhashedpassword", "Tradicional", LocalDateTime.now()));

            // Autenticamos al usuario admin
            authService.autenticar("admin", "adminhashedpassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRealizarAccionAdmin() {
        String resultado = adminService.realizarAccionAdmin("crearUsuario");
        assertEquals("Acción realizada: Crear nuevo usuario", resultado, "El administrador debería poder realizar esta acción.");
    }

    @Test
    void testAccionNoPermitidaParaUsuarioTradicional() {
        // Autenticamos a un usuario tradicional
        try {
            authService.autenticar("user", "userhashedpassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String resultado = adminService.realizarAccionAdmin("crearUsuario");
        assertEquals("Acción no permitida: Solo un administrador puede acceder a esta funcionalidad.", resultado, "Un usuario tradicional no debería poder realizar esta acción.");
    }

    @Test
    void testObtenerInfoAdmin() {
        String resultado = adminService.obtenerInfoAdmin();
        assertEquals("Información restringida a administradores: Estadísticas, reportes, etc.", resultado, "Solo los administradores deberían poder acceder a esta información.");
    }
}