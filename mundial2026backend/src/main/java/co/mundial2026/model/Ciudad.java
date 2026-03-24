package co.mundial2026.model;

public class Ciudad {
    private int idCiudad;
    private String nombre;
    private int idPaisAnfitrion;

    // Constructor
    public Ciudad(int idCiudad, String nombre, int idPaisAnfitrion) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.idPaisAnfitrion = idPaisAnfitrion;
    }

    // Getters y Setters
    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPaisAnfitrion() {
        return idPaisAnfitrion;
    }

    public void setIdPaisAnfitrion(int idPaisAnfitrion) {
        this.idPaisAnfitrion = idPaisAnfitrion;
    }
}