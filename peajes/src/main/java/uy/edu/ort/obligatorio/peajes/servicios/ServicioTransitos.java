package uy.edu.ort.obligatorio.peajes.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;

import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.observer.Observador;


public class ServicioTransitos {

    private List<Transito> transitos;

    public ServicioTransitos() {
        this.transitos = new ArrayList<>();
    }

    public Transito emularTransito(String matricula, Puesto puesto, LocalDateTime fechaHora,Vehiculo vehiculo) throws UsuarioException {

        if (vehiculo == null) {
            throw new UsuarioException("No existe el vehículo: " + matricula);
        }
        Propietario propietario = vehiculo.getPropietario();
        if (propietario == null) {
            throw new UsuarioException("No se encontró el propietario del vehículo con matrícula: " + matricula);
        }
        Transito transito = propietario.getEstado().emularTransito(vehiculo, puesto, fechaHora, propietario);
        transitos.add(transito);
        propietario.notificar(Observador.Evento.ESTADOPROPIETARIO_NUEVOTRANSITO);
        return transito;
    }

    public List<Transito> getTransitosPorPropietario(Propietario propietario) {
        List<Transito> transitosPropietario = new ArrayList<>();
        for(Transito t : transitos){
            if(t.getVehiculo().getPropietario().equals(propietario)){
                transitosPropietario.add(t);
            }
        }
        return transitosPropietario;
    }

}
