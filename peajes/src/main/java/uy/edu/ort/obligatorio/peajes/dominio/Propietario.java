package uy.edu.ort.obligatorio.peajes.dominio;

import java.util.List;

import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

public class Propietario extends Usuario {

    private double saldoMinimo;
    private double saldoActual;
    private List<Vehiculo> vehiculos;
    private List<Bonificacion> bonificaciones;
    private EstadoPropietario estado;

    public Propietario(String cedula, String nombreCompleto, String contrasena, double saldoMinimo, double saldoActual, EstadoPropietario estado) {
        super(cedula, nombreCompleto, contrasena);
        this.saldoMinimo = saldoMinimo;
        this.saldoActual = saldoActual; 
        this.estado = estado;

    }

    public double getSaldoMinimo() {
        return saldoMinimo;
    }

    public double getSaldoActual() {
        return saldoActual;
    }  

    public EstadoPropietario getEstado() {
        return estado;
    }
    
}

