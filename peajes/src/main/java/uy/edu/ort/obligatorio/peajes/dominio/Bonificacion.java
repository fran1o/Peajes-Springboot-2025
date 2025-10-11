package uy.edu.ort.obligatorio.peajes.dominio;

public class Bonificacion {
    private String nombre;
    private double porcentaje; // ??
    private Puesto puesto; 

    public Bonificacion(String nombre, double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }
    
}
