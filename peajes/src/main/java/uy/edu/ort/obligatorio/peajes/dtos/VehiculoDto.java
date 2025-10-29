package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Categoria;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;

public class VehiculoDto {

    @Getter
    private String matricula;
    @Getter
    private String modelo;
    @Getter
    private String color;
    @Getter
    private String categoria;
    @Getter
    private String cedulaPropietario;
    @Getter
    private List<Transito> transitos;

    public VehiculoDto(String matricula, String modelo, String color, String categoria, String cedulaPropietario) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
        this.cedulaPropietario = cedulaPropietario;
        this.transitos = new ArrayList<>();
    }

    public static List<VehiculoDto> listaDtos(List<Vehiculo> vehiculos) {
        List<VehiculoDto> lista = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            VehiculoDto dto = new VehiculoDto(v.getMatricula(), v.getModelo(), v.getColor(), v.getCategoria().getNombre(), v.getPropietario().getCedula());
            lista.add(dto);
        }
        return lista;
        
    }

}