package uy.edu.ort.obligatorio.peajes.dominio;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

public class Propietario extends Usuario {

    private double saldoMinimo;
    private double saldoActual;
    private List<Vehiculo> vehiculos;
    private List<Bonificacion> bonificaciones;
    private EstadoPropietario estado;
    private List<Notificacion> notificaciones;

    public Propietario(String cedula, String nombreCompleto, String contrasena, double saldoMinimo, double saldoActual, EstadoPropietario estado) {
        super(cedula, nombreCompleto, contrasena);
        this.saldoMinimo = saldoMinimo;
        this.saldoActual = saldoActual; 
        this.estado = estado;
        this.vehiculos = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    public double getSaldoMinimo() {
        return saldoMinimo;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public EstadoPropietario getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropietario estado) {
        this.estado = estado;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void agregarNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    public void borrarNotificaciones() {
        notificaciones.clear();
    }

    public void deducirSaldo(double monto) {
        this.saldoActual -= monto;
    }

    public boolean tieneVehiculo(String matricula) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                return true;
            }
        }
        return false;
    }

    public Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equals(matricula)) {
                return vehiculo;
            }
        }
        return null;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

    public boolean tieneBonificacionEnPuesto(Puesto puesto) {
        for (Bonificacion bonificacion : bonificaciones) {
            if (bonificacion.getPuesto().equals(puesto)) {
                return true;
            }
        }
        return false;
    }

    public Bonificacion buscarBonificacionPorPuesto(Puesto puesto) {
        for (Bonificacion bonificacion : bonificaciones) {
            if (bonificacion.getPuesto().equals(puesto)) {
                return bonificacion;
            }
        }
        return null;
    }

    public void agregarBonificacion(Bonificacion bonificacion) {
        bonificaciones.add(bonificacion);
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

    @Override
    public boolean validarLogin() throws UsuarioException {
        if (estado.estaDeshabilitado()) {
                throw new UsuarioException("Usuario deshabilitado, no puede ingresar al sistema");
        }

        return true;
    }
    
}

