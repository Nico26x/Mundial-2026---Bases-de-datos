package co.mundial2026.model;

public class PaisAnfitrion {
    private int idPaisAnfitrion;
    private String nombre;

    // Constructor
    public PaisAnfitrion(int idPaisAnfitrion, String nombre) {
        this.idPaisAnfitrion = idPaisAnfitrion;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdPaisAnfitrion() {
        return idPaisAnfitrion;
    }

    public void setIdPaisAnfitrion(int idPaisAnfitrion) {
        this.idPaisAnfitrion = idPaisAnfitrion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}