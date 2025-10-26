package uy.edu.ort.obligatorio.peajes.interfaces;

public interface EstadoPropietario {

    boolean estaDeshabilitado();
    boolean estaHabilitado();
    boolean estaSuspendido();
    boolean estaPenalizado();
    String getNombreEstado();
    
}
