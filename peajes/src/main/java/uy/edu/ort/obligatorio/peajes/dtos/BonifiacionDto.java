package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;

public class BonifiacionDto {
    @Getter
    private String nombre;
    @Getter
    private String puesto;

    public BonifiacionDto(String nombre, String puesto){
        this.nombre = nombre;
        this.puesto = puesto;
    }

    public static List<BonifiacionDto> listaDtos(List<Bonificacion> bonificaciones) {
        List<BonifiacionDto> lista = new ArrayList<>();
        for (Bonificacion b : bonificaciones) {
            BonifiacionDto dto = new BonifiacionDto(b.getTipoBonificacion().getNombre(), b.getPuesto().getNombre());
            lista.add(dto);
        }
        return lista;
    }

}
