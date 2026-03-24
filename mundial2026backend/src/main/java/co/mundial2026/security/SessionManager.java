package co.mundial2026.security;

import co.mundial2026.model.Usuario;

public class SessionManager {

    private static SessionManager instance;
    private Usuario usuarioActual;

    // Constructor privado para asegurarse de que solo haya una instancia
    private SessionManager() {}

    // Obtener la instancia de SessionManager (Singleton)
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Establecer el usuario en sesión
    public void login(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    // Cerrar sesión
    public void logout() {
        this.usuarioActual = null;
    }

    // Obtener el usuario actual
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Verificar si el usuario tiene el rol adecuado
    public boolean tieneRol(String rol) {
        return usuarioActual != null && usuarioActual.getTipoUsuario().equals(rol);
    }
}