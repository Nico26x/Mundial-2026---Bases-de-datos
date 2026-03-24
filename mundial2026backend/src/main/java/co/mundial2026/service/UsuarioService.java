package co.mundial2026.service;

import co.mundial2026.dao.UsuarioDAO;
import co.mundial2026.model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
    }

    // Método para agregar un nuevo usuario
    public void agregarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.agregarUsuario(usuario);
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() throws SQLException {
        return usuarioDAO.obtenerUsuarios();
    }

    // Método para verificar si el usuario y la contraseña son correctos
    public boolean verificarLogin(String nombreUsuario, String contrasenaHash) throws SQLException {
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getContrasenaHash().equals(contrasenaHash)) {
                return true; // Login correcto
            }
        }
        return false; // Login incorrecto
    }
}