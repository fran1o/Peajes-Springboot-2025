package uy.edu.ort.obligatorio.peajes.interfaces;

import java.time.LocalDateTime;

import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;

public interface EstadoPropietario {

    Transito emularTransito(Vehiculo vehiculo, Puesto puesto, LocalDateTime fechaHora, Propietario propietario) throws UsuarioException;
    void asignarBonificacion(TipoBonificacion tipoBonificacion, Propietario propietario,Puesto puesto, LocalDateTime fecha)throws UsuarioException;
    boolean estaDeshabilitado();
    boolean estaHabilitado();
    boolean estaSuspendido();
    boolean estaPenalizado();
    String getNombreEstado();
    
}
