package uy.edu.ort.obligatorio.peajes.servicios;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;

public class ServicioBonificaciones {
    private List<TipoBonificacion> tipoBonificaciones;

    public ServicioBonificaciones(){
        tipoBonificaciones = new ArrayList<>();
    }

    public void agregarTipoBonificacion(TipoBonificacion bonificacion){
        tipoBonificaciones.add(bonificacion);
    }

    public List<TipoBonificacion> getTipoBonificaciones(){
        return tipoBonificaciones;
    }

    public TipoBonificacion getTipoBonificacion(String bonificacionNombre) {
        for(TipoBonificacion b : tipoBonificaciones){
            if (b.getNombre().equalsIgnoreCase(bonificacionNombre.trim())) { 
                return b;
            }
        }

        return null;
    }
    
}
