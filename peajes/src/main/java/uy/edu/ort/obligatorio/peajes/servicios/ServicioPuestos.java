package uy.edu.ort.obligatorio.peajes.servicios;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Puesto;

public class ServicioPuestos {

    private List<Puesto> puestos;

    public ServicioPuestos() {
        puestos = new ArrayList<>();
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public void agregarPuesto(Puesto puesto) {
        puestos.add(puesto);
    }

    public Puesto getPuestoPorNombre(String puestoNombre){
        for(Puesto p : puestos){
            if(p.getNombre().equalsIgnoreCase(puestoNombre.trim())){
                return p;
            }
        }

        return null;
    }
    
}
