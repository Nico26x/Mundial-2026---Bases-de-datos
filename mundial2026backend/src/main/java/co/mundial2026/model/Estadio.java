package co.mundial2026.model;

public class Estadio {
    private int idEstadio;
    private String nombre;
    private int capacidad;
    private int idCiudad;

    // Constructor
    public Estadio(int idEstadio, String nombre, int capacidad, int idCiudad) {
        this.idEstadio = idEstadio;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.idCiudad = idCiudad;
    }

    // Getters y Setters
    public int getIdEstadio() {
        return idEstadio;
    }

    public void setIdEstadio(int idEstadio) {
        this.idEstadio = idEstadio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }
}