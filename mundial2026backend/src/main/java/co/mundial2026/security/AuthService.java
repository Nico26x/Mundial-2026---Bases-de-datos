package co.mundial2026.security;

import co.mundial2026.dao.BitacoraDAO;
import co.mundial2026.dao.UsuarioDAO;
import co.mundial2026.model.Usuario;

import java.sql.SQLException;

public class AuthService {

    private final UsuarioDAO usuarioDAO;
    private final BitacoraDAO bitacoraDAO;
    private final SessionManager sessionManager;

    public AuthService() {
        usuarioDAO = new UsuarioDAO();
        bitacoraDAO = new BitacoraDAO();
        sessionManager = SessionManager.getInstance();
    }

    public boolean autenticar(String nombreUsuario, String contrasenaHash) throws SQLException {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario);

        if (usuario != null && usuario.getContrasenaHash().equals(contrasenaHash)) {
            sessionManager.login(usuario);

            int idRegistro = bitacoraDAO.registrarIngreso(usuario.getIdUsuario());
            sessionManager.setIdRegistroBitacora(idRegistro);

            return true;
        }

        return false;
    }

    public void cerrarSesion() {
        try {
            int idRegistro = sessionManager.getIdRegistroBitacora();

            if (idRegistro > 0) {
                bitacoraDAO.registrarSalida(idRegistro);
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar salida en bitácora: " + e.getMessage());
        } finally {
            sessionManager.logout();
        }
    }

    public Usuario getUsuarioActual() {
        return sessionManager.getUsuarioActual();
    }

    public boolean tieneRol(String rol) {
        return sessionManager.tieneRol(rol);
    }

    public boolean esAdministrador() {
        return tieneRol("Administrador");
    }

    public boolean esUsuarioTradicional() {
        return tieneRol("Tradicional");
    }

    public boolean esUsuarioEsporadico() {
        return tieneRol("Esporadico") || tieneRol("Esporádico");
    }
}