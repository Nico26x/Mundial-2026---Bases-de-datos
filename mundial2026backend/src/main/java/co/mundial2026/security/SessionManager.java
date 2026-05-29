package co.mundial2026.security;

import co.mundial2026.model.Usuario;

public class SessionManager {

    private static SessionManager instance;
    private Usuario usuarioActual;
    private int idRegistroBitacora = -1;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void logout() {
        this.usuarioActual = null;
        this.idRegistroBitacora = -1;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public int getIdRegistroBitacora() {
        return idRegistroBitacora;
    }

    public void setIdRegistroBitacora(int idRegistroBitacora) {
        this.idRegistroBitacora = idRegistroBitacora;
    }

    public boolean tieneRol(String rol) {
        return usuarioActual != null && usuarioActual.getTipoUsuario().equals(rol);
    }
}