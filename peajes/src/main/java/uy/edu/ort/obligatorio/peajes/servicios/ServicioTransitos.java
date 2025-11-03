package uy.edu.ort.obligatorio.peajes.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        propietario.validarPuedeRealizarTransito();

        Tarifa tarifa = puesto.buscarTarifaPorCategoria(vehiculo.getCategoria());
        double montoTarifa = tarifa.getMonto();

        double montoBonificacion = propietario.calcularMontoBonificacion(vehiculo, puesto, montoTarifa, fechaHora);
        double montoACobrar = montoTarifa - montoBonificacion;

        propietario.validarSaldoSuficiente(montoACobrar);
        propietario.deducirSaldo(montoACobrar);

        Transito transito = new Transito(vehiculo, puesto, fechaHora, montoACobrar, montoBonificacion);
        transitos.add(transito);
        puesto.registrarTransito(transito);
        vehiculo.agregarTransito(transito);

        propietario.crearNotificacionesTransito(vehiculo, puesto);

        return transito;
    }

    public List<Transito> getAllTransitos() {
        return transitos;
    }

    public List<Transito> getTransitosPorPropietario(Propietario propietario) {
        return propietario.getTransitos();
    }

}
