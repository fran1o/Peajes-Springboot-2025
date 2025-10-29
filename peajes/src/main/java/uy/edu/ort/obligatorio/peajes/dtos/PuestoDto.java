package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;

public class PuestoDto {
    @Getter
    private String nombre;
    @Getter
    private String direccion;
    @Getter
    private List<Tarifa> tarifas;
    @Getter
    private List<Transito> transitos;
    

    public PuestoDto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        tarifas = new ArrayList<>();
        transitos = new ArrayList<>();
    }

    public static List<PuestoDto> listaDtos(List<Puesto> puestos) {
        List<PuestoDto> lista = new ArrayList<>();
        for (Puesto p : puestos) {
            PuestoDto dto = new PuestoDto(p.getNombre(), p.getDireccion());
            lista.add(dto);
            dto.tarifas = p.getTarifas();
            dto.transitos = p.getTransitos(); 
        }
        return lista;
        
    }

}
