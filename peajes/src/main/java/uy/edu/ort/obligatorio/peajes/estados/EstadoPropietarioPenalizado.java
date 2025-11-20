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

public class EstadoPropietarioPenalizado implements EstadoPropietario{

    @Override
    public boolean estaDeshabilitado() {
        return false;
    }

    @Override
    public boolean estaHabilitado() {
        return false;
    }

    @Override
    public boolean estaSuspendido() {
        return false;
    }

    @Override
    public boolean estaPenalizado() {
        return true;
    }

    @Override
    public String getNombreEstado() {
        return "Penalizado";
    }

    @Override
    public Transito emularTransito(Vehiculo vehiculo, Puesto puesto, LocalDateTime fechaHora, Propietario propietario)
            throws UsuarioException {
            if (puesto == null) {
                throw new UsuarioException("Debe especificar un puesto");
            }

            Tarifa tarifa = puesto.buscarTarifaPorCategoria(vehiculo.getCategoria());
            double montoTarifa = tarifa.getMonto();
            double montoBonificacion = 0; 
            double montoACobrar = montoTarifa - montoBonificacion;

            propietario.validarSaldoSuficiente(montoACobrar);
            propietario.deducirSaldo(montoACobrar);


            Transito transito = new Transito(vehiculo, puesto, fechaHora, montoACobrar, montoBonificacion);
            puesto.agregarTransito(transito);
            vehiculo.agregarTransito(transito);

        return transito;
    }

    @Override
    public void asignarBonificacion(TipoBonificacion tipoBonificacion, Propietario propietario, Puesto puesto,
            LocalDateTime fecha) throws UsuarioException {
        Bonificacion bonificacion = new Bonificacion(tipoBonificacion, propietario, puesto, fecha);
        propietario.agregarBonificacion(bonificacion);
    }

}
