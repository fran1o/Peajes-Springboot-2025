package uy.edu.ort.obligatorio.peajes.estados;

import java.time.LocalDateTime;

import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

public class EstadoPropietarioSuspendido implements EstadoPropietario{

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
        return true;
    }

    @Override
    public boolean estaPenalizado() {
        return false;
    }

    @Override
    public String getNombreEstado() {
        return "Suspendido";
    }

    @Override
    public Transito emularTransito(Vehiculo vehiculo, Puesto puesto, LocalDateTime fechaHora, Propietario propietario)
            throws UsuarioException {
        throw new UsuarioException("El propietario está suspendido, no puede realizar tránsitos");
    }

    @Override
    public void asignarBonificacion(TipoBonificacion tipoBonificacion, Propietario propietario, Puesto puesto,
            LocalDateTime fecha) throws UsuarioException {
        Bonificacion bonificacion = new Bonificacion(tipoBonificacion, propietario, puesto, fecha);
        propietario.agregarBonificacion(bonificacion);
    }
}
