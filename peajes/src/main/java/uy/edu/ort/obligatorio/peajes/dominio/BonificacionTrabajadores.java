package uy.edu.ort.obligatorio.peajes.dominio;

import uy.edu.ort.obligatorio.peajes.dominio.estrategias.EstrategiaTrabajadores;

public class BonificacionTrabajadores {

    public static Bonificacion crear() {
        return new Bonificacion("Bonificación para trabajadores del peaje", 80, new EstrategiaTrabajadores());
    }

}
