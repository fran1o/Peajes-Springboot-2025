package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDateTime;

public abstract class Bonificacion {
    private String nombre;
    private double porcentaje;
    private Puesto puesto;
    private LocalDateTime fechaAsignacion;

    public Bonificacion(String nombre, double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public abstract double calcularDescuento(Transito transito);
    
}
