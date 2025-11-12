package uy.edu.ort.obligatorio.peajes.servicios;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;

public class ServicioBonificaciones {
    private List<TipoBonificacion> bonificaciones;

    public ServicioBonificaciones(){
        bonificaciones = new ArrayList<>();
    }

    public void agregarTipoBonificacion(TipoBonificacion bonificacion){
        bonificaciones.add(bonificacion);
    }

    public List<TipoBonificacion> getTipoBonificaciones(){
        return bonificaciones;
    }

    public TipoBonificacion getTipoBonificacion(String bonificacionNombre) {
        for(TipoBonificacion b : bonificaciones){
            if (b.getNombre().equalsIgnoreCase(bonificacionNombre.trim())) { 
                return b;
            }
        }

        return null;
    }
    
}
