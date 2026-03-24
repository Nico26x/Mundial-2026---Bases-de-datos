package co.mundial2026.model;

public class Equipo {
    private int idEquipo;
    private String nombre;
    private String pais;
    private double valorTotalEquipo;
    private int idConfederacion;

    // Constructor
    public Equipo(int idEquipo, String nombre, String pais, double valorTotalEquipo, int idConfederacion) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.pais = pais;
        this.valorTotalEquipo = valorTotalEquipo;
        this.idConfederacion = idConfederacion;
    }

    // Getters y Setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getValorTotalEquipo() {
        return valorTotalEquipo;
    }

    public void setValorTotalEquipo(double valorTotalEquipo) {
        this.valorTotalEquipo = valorTotalEquipo;
    }

    public int getIdConfederacion() {
        return idConfederacion;
    }

    public void setIdConfederacion(int idConfederacion) {
        this.idConfederacion = idConfederacion;
    }
}