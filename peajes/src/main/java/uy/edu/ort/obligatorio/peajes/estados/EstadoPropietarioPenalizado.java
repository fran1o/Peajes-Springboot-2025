package uy.edu.ort.obligatorio.peajes.estados;

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
}
