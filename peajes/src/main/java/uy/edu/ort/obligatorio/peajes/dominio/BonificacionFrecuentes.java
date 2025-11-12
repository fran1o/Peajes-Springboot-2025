package uy.edu.ort.obligatorio.peajes.dominio;

import uy.edu.ort.obligatorio.peajes.dominio.estrategias.EstrategiaFrecuentes;

public class BonificacionFrecuentes {

    public static Bonificacion crear() {
        return new Bonificacion("Bonificación para vehículos frecuentes", 50, new EstrategiaFrecuentes());
    }

}
