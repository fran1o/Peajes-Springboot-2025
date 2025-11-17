package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;
import uy.edu.ort.obligatorio.peajes.observer.ManejadorPropietarioObservable;
import uy.edu.ort.obligatorio.peajes.observer.Observador;
public class Propietario extends Usuario {

    private boolean estaLogueado;
    private double saldoMinimo;
    private double saldoActual;
    private List<Vehiculo> vehiculos;
    private List<Bonificacion> bonificaciones;
    private EstadoPropietario estado;
    private List<Notificacion> notificaciones;
    private ManejadorPropietarioObservable manejadorPropietarioObservable = new ManejadorPropietarioObservable();

    public Propietario(String cedula, String nombreCompleto, String contrasena, double saldoMinimo, double saldoActual,
            EstadoPropietario estado) {
        super(cedula, nombreCompleto, contrasena);
        this.saldoMinimo = saldoMinimo;
        this.saldoActual = saldoActual;
        this.estado = estado;
        this.vehiculos = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
        this.estaLogueado = false;
    }
    public boolean estaLogueado() {
        return estaLogueado;
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

    public void setEstado(EstadoPropietario nuevoEstado) throws UsuarioException {
        Notificacion notificacion = new Notificacion(LocalDateTime.now(),
                "Se ha cambiado tu estado en el sistema. Tu estado actual es " + nuevoEstado.getNombreEstado(),
                this);
        agregarNotificacion(notificacion);
        this.estado = nuevoEstado;
        notificar(Observador.Evento.ESTADOPROPIETARIO_ACTUALIZADO); 
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
        notificar(Observador.Evento.ESTADOPROPIETARIO_NUEVANOTIFICACION);
    }

    public void borrarNotificaciones() {
        notificaciones.clear();
        notificar(Observador.Evento.ESTADOPROPIETARIO_NOTIFICACIONESBORRADAS);
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
        notificar(Observador.Evento.ESTADOPROPIETARIO_NUEVABONIFICACION);
    }

    @Override
    public void logout() {
        this.estaLogueado = false;
    }

    @Override
    public boolean validarLogin() throws UsuarioException {
        if (estado.estaDeshabilitado()) {
            throw new UsuarioException("Usuario deshabilitado, no puede ingresar al sistema");
        }

        return true;
    }

    public void validarPuedeRealizarTransito() throws UsuarioException {
        if (estado.estaDeshabilitado()) {
            throw new UsuarioException("El propietario del vehículo está deshabilitado, no puede realizar tránsitos");
        }
        if (estado.estaSuspendido()) {
            throw new UsuarioException("El propietario del vehículo está suspendido, no puede realizar tránsitos");
        }
    }

    public void validarSaldoSuficiente(double monto) throws UsuarioException {
        if (saldoActual < monto) {
            throw new UsuarioException("Saldo insuficiente: " + saldoActual);
        }
    }

    public double calcularMontoConBonificacion(Vehiculo vehiculo, Puesto puesto, double montoTarifa,
            LocalDateTime fechaHora) {
        double montoBonificacion = calcularMontoBonificacion(vehiculo, puesto, montoTarifa, fechaHora);
        return montoTarifa - montoBonificacion;
    }

    public double calcularMontoBonificacion(Vehiculo vehiculo, Puesto puesto, double montoTarifa,
            LocalDateTime fechaHora) {
        if (estado.estaPenalizado()) {
            return 0;
        }

        Bonificacion bonificacion = buscarBonificacionPorPuesto(puesto);
        if (bonificacion != null) {
            Transito transitoTemp = new Transito(vehiculo, puesto, fechaHora, montoTarifa, 0);
            return bonificacion.calcularDescuento(transitoTemp);
        }

        return 0;
    }

    public void crearNotificacionesTransito(Vehiculo vehiculo, Puesto puesto) {
        if (!estado.estaPenalizado()) {
            Notificacion notificacionTransito = new Notificacion(
                    LocalDateTime.now(),
                    "Pasaste por el puesto " + puesto.getNombre() + " con el vehículo " + vehiculo.getMatricula(),
                    this);
            agregarNotificacion(notificacionTransito);

            if (saldoActual < saldoMinimo) {
                Notificacion notificacionSaldo = new Notificacion(
                        LocalDateTime.now(),
                        "Tu saldo actual es de $ " + saldoActual
                                + " Te recomendamos hacer una recarga",
                        this);
                agregarNotificacion(notificacionSaldo);
            }
        }
    }

    public List<Transito> getTransitos(){
        List<Transito> transitos = new ArrayList<>();

        for(Vehiculo v : vehiculos){
            for(Transito t : v.getTransitos()){
                transitos.add(t);
            }
        }

        return transitos;
    }

    public void suscribir(Observador observador){
        manejadorPropietarioObservable.suscribir(observador);
    }

    public void desubscribir(Observador observador){
        manejadorPropietarioObservable.desubscribir(observador);
    }

    public void notificar(Object evento){
        manejadorPropietarioObservable.notificar(evento);
    }


}
