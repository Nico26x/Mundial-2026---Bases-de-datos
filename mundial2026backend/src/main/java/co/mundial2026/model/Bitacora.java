package co.mundial2026.model;

import java.time.LocalDateTime;

public class Bitacora {
    private int idRegistro;
    private int idUsuario;
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraSalida;

    // Constructor
    public Bitacora(int idRegistro, int idUsuario, LocalDateTime fechaHoraIngreso, LocalDateTime fechaHoraSalida) {
        this.idRegistro = idRegistro;
        this.idUsuario = idUsuario;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraSalida = fechaHoraSalida;
    }

    // Getters y Setters
    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) {
        this.fechaHoraIngreso = fechaHoraIngreso;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }
}