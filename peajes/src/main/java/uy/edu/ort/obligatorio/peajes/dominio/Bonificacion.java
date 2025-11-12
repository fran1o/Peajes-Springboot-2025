package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDateTime;


import lombok.Getter;

public class Bonificacion {
    @Getter
    private TipoBonificacion tipoBonificacion;
    @Getter
    private Propietario propietario;
    @Getter
    private Puesto puesto;
    private LocalDateTime fecha;

    public Bonificacion(TipoBonificacion tipo, Propietario prop, Puesto puesto, LocalDateTime fecha){
        this.tipoBonificacion = tipo;
        this.propietario = prop;
        this.puesto = puesto;
        this.fecha = fecha;
    }


}
