package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;

public class BonificacionDto {
    @Getter
    private String nombre;
    @Getter
    private String puesto;
    @Getter
    private String fechaAsignacion;

    public BonificacionDto(String nombre, String puesto, String fechaAsignacion) {
        this.nombre = nombre;
        this.puesto = puesto != null ? puesto : "";
        this.fechaAsignacion = fechaAsignacion != null ? fechaAsignacion : "";
    }

    public static List<BonificacionDto> listaDtos(List<Bonificacion> bonificaciones) {
        List<BonificacionDto> lista = new ArrayList<>();
        for (Bonificacion b : bonificaciones) {
            String puestoNombre = b.getPuesto() != null ? b.getPuesto().getNombre() : "";
            String fechaAsignacionStr = b.getFechaAsignacion() != null ? b.getFechaAsignacion().toString() : "";
            BonificacionDto dto = new BonificacionDto(b.getNombre(), puestoNombre, fechaAsignacionStr);
            lista.add(dto);
        }
        return lista;
    }
}
