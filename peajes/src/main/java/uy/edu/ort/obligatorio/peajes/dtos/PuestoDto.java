package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;

public class PuestoDto {
    @Getter
    private String nombre;
    @Getter
    private String direccion;
    

    public PuestoDto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public static List<PuestoDto> listaDtos(List<Puesto> puestos) {
        List<PuestoDto> lista = new ArrayList<>();
        for (Puesto p : puestos) {
            PuestoDto dto = new PuestoDto(p.getNombre(), p.getDireccion());
            lista.add(dto);
        }
        return lista;
        
    }

}
