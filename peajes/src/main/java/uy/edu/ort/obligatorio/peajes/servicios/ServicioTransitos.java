package uy.edu.ort.obligatorio.peajes.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Notificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;

public class ServicioTransitos {

    private List<Transito> transitos;

    public ServicioTransitos() {
        this.transitos = new ArrayList<>();
    }

    public Transito emularTransito(String matricula, Puesto puesto, LocalDateTime fechaHora,
            Vehiculo vehiculo, Propietario propietario) throws UsuarioException {

        if (vehiculo == null) {
            throw new UsuarioException("No existe el vehículo");
        }

        if (propietario.getEstado().estaDeshabilitado()) {
            throw new UsuarioException("El propietario del vehículo está deshabilitado, no puede realizar tránsitos");
        }

        if (propietario.getEstado().estaSuspendido()) {
            throw new UsuarioException("El propietario del vehículo está suspendido, no puede realizar tránsitos");
        }

        Tarifa tarifa = puesto.buscarTarifaPorCategoria(vehiculo.getCategoria());
        double montoTarifa = tarifa.getMonto();
        double montoBonificacion = 0;

        if (!propietario.getEstado().estaPenalizado()) {
            Bonificacion bonificacion = propietario.buscarBonificacionPorPuesto(puesto);
            if (bonificacion != null) {
                Transito transitoTemp = new Transito(vehiculo, puesto, fechaHora, montoTarifa, 0);
                montoBonificacion = bonificacion.calcularDescuento(transitoTemp);
            }
        }

        double montoACobrar = montoTarifa - montoBonificacion;

        if (propietario.getSaldoActual() < montoACobrar) {
            throw new UsuarioException("Saldo insuficiente: " + propietario.getSaldoActual());
        }

        propietario.deducirSaldo(montoACobrar);

        Transito transito = new Transito(vehiculo, puesto, fechaHora, montoACobrar, montoBonificacion);
        transitos.add(transito);
        puesto.registrarTransito(transito);
        vehiculo.getTransitos().add(transito);

        if (!propietario.getEstado().estaPenalizado()) {
            Notificacion notificacionTransito = new Notificacion(
                    LocalDateTime.now(),
                    "Pasaste por el puesto " + puesto.getNombre() + " con el vehículo " + vehiculo.getMatricula(),
                    propietario);
            propietario.agregarNotificacion(notificacionTransito);

            if (propietario.getSaldoActual() < propietario.getSaldoMinimo()) {
                Notificacion notificacionSaldo = new Notificacion(
                        LocalDateTime.now(),
                        "Tu saldo actual es de $ " + propietario.getSaldoActual()
                                + " Te recomendamos hacer una recarga",
                        propietario);
                propietario.agregarNotificacion(notificacionSaldo);
            }
        }

        return transito;
    }

    public List<Transito> getAllTransitos() {
        return transitos;
    }

    public List<Transito> getTransitosPorPropietario(Propietario propietario) {
        List<Transito> transitosPropietario = new ArrayList<>();
        for (Transito transito : transitos) {
            if (transito.getVehiculo().getPropietario().equals(propietario)) {
                transitosPropietario.add(transito);
            }
        }
        return transitosPropietario;
    }

}
