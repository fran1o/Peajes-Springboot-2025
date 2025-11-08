package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;

public class BonificacionDto {
    @Getter
    private String nombre;

    public BonificacionDto(String nombre) {
        this.nombre = nombre;
    }

    public static List<BonificacionDto> listaDtos(List<Bonificacion> bonificaciones) {
        List<BonificacionDto> lista = new ArrayList<>();
        for (Bonificacion b : bonificaciones) {
            BonificacionDto dto = new BonificacionDto(b.getNombre());
            lista.add(dto);
        }
        return lista;
    }
}
