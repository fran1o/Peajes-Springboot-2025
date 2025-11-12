package uy.edu.ort.obligatorio.peajes.dominio;

import uy.edu.ort.obligatorio.peajes.dominio.estrategias.EstrategiaExonerados;

public class BonificacionExonerados {

    public static Bonificacion crear() {
        return new Bonificacion("Exonerados", 100, new EstrategiaExonerados());
    }

}