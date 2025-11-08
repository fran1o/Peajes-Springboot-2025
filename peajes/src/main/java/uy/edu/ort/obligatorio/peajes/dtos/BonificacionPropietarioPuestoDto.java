package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionPropietarioPuesto;

public class BonificacionPropietarioPuestoDto {
    @Getter
    private String bonifiacionNombre;
     @Getter
    private String propietarioNombre;
    @Getter
    private String puestoNombre;
   

    public BonificacionPropietarioPuestoDto(String boni, String prop, String puesto){
        this.bonifiacionNombre = boni;
        this.propietarioNombre = prop;
        this.puestoNombre = puesto;
    }

    public static List<BonificacionPropietarioPuestoDto> listaDtos(List<BonificacionPropietarioPuesto> bonificaciones) {
        List<BonificacionPropietarioPuestoDto> lista = new ArrayList<>();
        for (BonificacionPropietarioPuesto b : bonificaciones) {
            BonificacionPropietarioPuestoDto dto = new BonificacionPropietarioPuestoDto(b.getBonificacion().getNombre(), b.getPropietario().getNombreCompleto(),b.getPuesto().getNombre());
            lista.add(dto);
        }
        return lista;
    }
}


