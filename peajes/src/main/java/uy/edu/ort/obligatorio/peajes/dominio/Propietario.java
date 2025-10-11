package uy.edu.ort.obligatorio.peajes.dominio;

import java.util.List;

public class Propietario extends Usuario {

    private double saldoMinimo;
    private double saldoActual;
    private List<Vehiculo> vehiculos;
    private List<Bonificacion> bonificaciones;

    public Propietario(String cedula, String nombreCompleto, String contrasena, double saldoMinimo, double saldoActual) {
        super(cedula, nombreCompleto, contrasena);
        this.saldoMinimo = saldoMinimo;
        this.saldoActual = saldoActual; 

    }
    
}

