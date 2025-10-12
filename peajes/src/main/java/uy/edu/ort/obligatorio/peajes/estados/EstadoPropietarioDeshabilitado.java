package uy.edu.ort.obligatorio.peajes.estados;

import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

public class EstadoPropietarioDeshabilitado implements EstadoPropietario{

    @Override
    public boolean estaDeshabilitado() {
        return true;
    }

    @Override
    public boolean estaHabilitado() {
        return false;
    }

    @Override
    public boolean estaSuspendido() {
        return false;
    }
}