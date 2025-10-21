package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDateTime;

public class Transito {
    private Vehiculo vehiculo;
    private Puesto puesto;
    private LocalDateTime fechaHora;
    private double montoACobrar;
    private double montoBonificacion;

    public Transito(Vehiculo vehiculo, Puesto puesto, LocalDateTime fechaHora, double montoACobrar, double montoBonificacion) {
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.fechaHora = fechaHora;
        this.montoACobrar = montoACobrar;
        this.montoBonificacion = montoBonificacion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getMontoACobrar() {
        return montoACobrar;
    }

    public void setMontoACobrar(double montoACobrar) {
        this.montoACobrar = montoACobrar;
    }

    public double getMontoBonificacion() {
        return montoBonificacion;
    }

    public void setMontoBonificacion(double montoBonificacion) {
        this.montoBonificacion = montoBonificacion;
    }

}
