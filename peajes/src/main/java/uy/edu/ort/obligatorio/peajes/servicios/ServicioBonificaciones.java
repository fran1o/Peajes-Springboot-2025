package uy.edu.ort.obligatorio.peajes.servicios;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;

public class ServicioBonificaciones {
    private List<Bonificacion> bonificaciones;

    public ServicioBonificaciones(){
        bonificaciones = new ArrayList<>();
    }

    public void agregarBonificacion(Bonificacion bonificacion){
        bonificaciones.add(bonificacion);
    }

    public List<Bonificacion> getBonificacions(){
        return bonificaciones;
    }

    public Bonificacion getBonificacion(String bonificacionNombre) {
        for(Bonificacion b : bonificaciones){
            if (b.getNombre().equalsIgnoreCase(bonificacionNombre.trim())) { 
                return b;
            }
        }

        return null;
    }
    
}
