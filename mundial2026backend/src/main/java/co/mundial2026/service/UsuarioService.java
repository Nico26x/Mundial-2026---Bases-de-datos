package co.mundial2026.service;

import co.mundial2026.dao.UsuarioDAO;
import co.mundial2026.model.Usuario;

import java.sql.SQLException;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    // Método para agregar un nuevo usuario
    public void agregarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.agregarUsuario(usuario);
    }

    // Método para validar el login de un usuario
    public boolean validarLogin(String nombreUsuario, String contrasenaHash) throws SQLException {
        // Obtenemos el usuario desde la base de datos
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario);

        // Si el usuario existe y la contraseña es correcta, retornamos true
        if (usuario != null && usuario.getContrasenaHash().equals(contrasenaHash)) {
            return true;
        }

        return false; // Si no coincide, retornamos false
    }
}