package co.mundial2026.model;

import java.time.LocalDate;

public class DirectorTecnico {
    private int idDt;
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;
    private int idEquipo;

    // Constructor
    public DirectorTecnico(int idDt, String nombre, String nacionalidad, LocalDate fechaNacimiento, int idEquipo) {
        this.idDt = idDt;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.idEquipo = idEquipo;
    }

    // Getters y Setters
    public int getIdDt() {
        return idDt;
    }

    public void setIdDt(int idDt) {
        this.idDt = idDt;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}