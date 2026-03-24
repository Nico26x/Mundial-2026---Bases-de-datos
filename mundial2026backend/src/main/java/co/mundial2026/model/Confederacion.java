package co.mundial2026.model;

public class Confederacion {
    private int idConfederacion;
    private String nombre;
    private String siglas;
    
    // Constructor
    public Confederacion(int idConfederacion, String nombre, String siglas) {
        this.idConfederacion = idConfederacion;
        this.nombre = nombre;
        this.siglas = siglas;
    }

    // Getters y Setters
    public int getIdConfederacion() {
        return idConfederacion;
    }

    public void setIdConfederacion(int idConfederacion) {
        this.idConfederacion = idConfederacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }
}