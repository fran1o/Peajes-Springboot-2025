package uy.edu.ort.obligatorio.peajes.dominio;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Sesion {
    @Getter
    private Usuario usuario;
    @Setter
    @Getter
    private Date fechaInicio;

    public Sesion(Usuario usuario) {
        this.usuario = usuario;
    }
}
