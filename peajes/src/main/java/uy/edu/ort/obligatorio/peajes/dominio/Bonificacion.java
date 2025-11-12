package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDateTime;

import uy.edu.ort.obligatorio.peajes.interfaces.EstrategiaCalculoDescuento;

public class Bonificacion {
    private String nombre;
    private double porcentaje;
    private Puesto puesto;
    private LocalDateTime fechaAsignacion;
    private EstrategiaCalculoDescuento estrategiaCalculo;

    public Bonificacion(String nombre, double porcentaje, EstrategiaCalculoDescuento estrategiaCalculo) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.estrategiaCalculo = estrategiaCalculo;
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

    public double calcularDescuento(Transito transito) {
        return estrategiaCalculo.calcularDescuento(transito);
    }

}
