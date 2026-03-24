package co.mundial2026.security;

import co.mundial2026.dao.UsuarioDAO;
import co.mundial2026.model.Usuario;

import java.sql.SQLException;

public class AuthService {

    private final UsuarioDAO usuarioDAO;
    private final SessionManager sessionManager;

    public AuthService() {
        usuarioDAO = new UsuarioDAO();
        sessionManager = SessionManager.getInstance();
    }

    // Método para autenticar al usuario
    public boolean autenticar(String nombreUsuario, String contrasenaHash) throws SQLException {
        // Obtener el usuario por nombre de usuario desde la base de datos
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario);

        // Verificar si el usuario existe y la contraseña es correcta
        if (usuario != null && usuario.getContrasenaHash().equals(contrasenaHash)) {
            sessionManager.login(usuario);
            return true;
        }
        return false;
    }

    // Método para cerrar sesión
    public void cerrarSesion() {
        sessionManager.logout();
    }

    // Obtener el usuario actual
    public Usuario getUsuarioActual() {
        return sessionManager.getUsuarioActual();
    }

    // Verificar si el usuario tiene el rol adecuado
    public boolean tieneRol(String rol) {
        return sessionManager.tieneRol(rol);
    }

    // Métodos adicionales si necesitas más validaciones en el futuro
    public boolean esAdministrador() {
        return tieneRol("Administrador");
    }

    public boolean esUsuarioTradicional() {
        return tieneRol("Tradicional");
    }

    public boolean esUsuarioEsporadico() {
        return tieneRol("Esporádico");
    }
}