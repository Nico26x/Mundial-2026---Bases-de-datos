package co.mundial2026.model;

import java.time.LocalDate;

public class Jugador {
    private int idJugador;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String posicion;
    private double peso;
    private double estatura;
    private double valorMercado;
    private int idEquipo;

    // Constructor
    public Jugador(int idJugador, String nombre, LocalDate fechaNacimiento, String posicion, double peso, double estatura, double valorMercado, int idEquipo) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.posicion = posicion;
        this.peso = peso;
        this.estatura = estatura;
        this.valorMercado = valorMercado;
        this.idEquipo = idEquipo;
    }

    // Getters y Setters
    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(double valorMercado) {
        this.valorMercado = valorMercado;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}