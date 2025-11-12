package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;

public class TipoBonificacionDto {
    @Getter
    private String nombre;

    public TipoBonificacionDto(String nombre) {
        this.nombre = nombre;
    }

    public static List<TipoBonificacionDto> listaDtos(List<TipoBonificacion> bonificaciones) {
        List<TipoBonificacionDto> lista = new ArrayList<>();
        for (TipoBonificacion b : bonificaciones) {
            TipoBonificacionDto dto = new TipoBonificacionDto(b.getNombre());
            lista.add(dto);
        }
        return lista;
    }
}
