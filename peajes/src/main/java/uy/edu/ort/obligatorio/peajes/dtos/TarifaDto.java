package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;

public class TarifaDto {

    @Getter
    private double monto;
    @Getter
    private String categoria;

    public TarifaDto(String categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }
    

    public static List<TarifaDto> listaDtos(List<Tarifa> tarifas) {
        List<TarifaDto> lista = new ArrayList<>();
        for (Tarifa t : tarifas) {
            TarifaDto dto = new TarifaDto(t.getCategoria().getNombre(), t.getMonto());
            lista.add(dto);
        }
        return lista;
        
    }
}
