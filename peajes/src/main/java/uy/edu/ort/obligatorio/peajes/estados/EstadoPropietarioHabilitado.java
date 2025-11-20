package uy.edu.ort.obligatorio.peajes.estados;

import java.time.LocalDateTime;

import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;
import uy.edu.ort.obligatorio.peajes.observer.Observador;

public class EstadoPropietarioHabilitado implements EstadoPropietario{

    @Override
    public boolean estaDeshabilitado() {
        return false;
    }

    @Override
    public boolean estaHabilitado() {
        return true;
    }

    @Override
    public boolean estaSuspendido() {
        return false;
    }

    @Override
    public boolean estaPenalizado() {
        return false;
    }

    @Override
    public String getNombreEstado() {
        return "Habilitado";
    }

    @Override
    public Transito emularTransito(Vehiculo vehiculo, Puesto puesto, LocalDateTime fechaHora, Propietario propietario) throws UsuarioException {
        if (puesto == null) {
            throw new UsuarioException("Debe especificar un puesto");
        }

        Tarifa tarifa = puesto.buscarTarifaPorCategoria(vehiculo.getCategoria());
        double montoTarifa = tarifa.getMonto();

        double montoBonificacion = propietario.calcularMontoBonificacion(vehiculo, puesto, montoTarifa, fechaHora);
        double montoACobrar = montoTarifa - montoBonificacion;

        propietario.validarSaldoSuficiente(montoACobrar);
        propietario.deducirSaldo(montoACobrar);

        Transito transito = new Transito(vehiculo, puesto, fechaHora, montoACobrar, montoBonificacion);
        puesto.agregarTransito(transito);
        vehiculo.agregarTransito(transito);      
        propietario.crearNotificacionesTransito(vehiculo, puesto);
        
        return transito;
    }

    @Override
    public void asignarBonificacion(TipoBonificacion tipoBonificacion, Propietario propietario, Puesto puesto, LocalDateTime fecha){
        Bonificacion bonificacion = new Bonificacion(tipoBonificacion, propietario, puesto, fecha);
        propietario.agregarBonificacion(bonificacion);
    }
    
}