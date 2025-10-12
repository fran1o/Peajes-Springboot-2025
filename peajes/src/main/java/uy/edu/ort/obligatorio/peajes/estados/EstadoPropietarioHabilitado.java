package uy.edu.ort.obligatorio.peajes.estados;

import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

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
    
}