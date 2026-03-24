package co.mundial2026.model;

import java.time.LocalDateTime;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasenaHash;
    private String tipoUsuario; // Admin, Tradicional, Esporadico
    private LocalDateTime fechaCreacion;
    
    // Constructor
    public Usuario(int idUsuario, String nombreUsuario, String contrasenaHash, String tipoUsuario, LocalDateTime fechaCreacion) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasenaHash = contrasenaHash;
        this.tipoUsuario = tipoUsuario;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}