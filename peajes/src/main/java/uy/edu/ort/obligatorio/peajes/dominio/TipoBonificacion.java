package uy.edu.ort.obligatorio.peajes.dominio;

public abstract class TipoBonificacion {
    private String nombre;
    private double porcentaje;

    public TipoBonificacion(String nombre, double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public abstract double calcularDescuento(Transito transito);
    
}
