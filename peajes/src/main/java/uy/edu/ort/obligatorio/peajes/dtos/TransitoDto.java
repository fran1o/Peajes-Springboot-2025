package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionPropietarioPuesto;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;

public class TransitoDto {
    @Getter
    private String puesto;
    @Getter
    private String matricula;
    @Getter
    private String categoria;
    @Getter
    private double montoTarifa;
    @Getter
    private String bonificacionNombre;
    @Getter
    private double montoBonificacion;
    @Getter
    private double montoPagado;
    @Getter
    private String fechaHora;

    public TransitoDto(String puesto, String matricula, String categoria, double montoTarifa,
                        String bonificacionNombre, double montoBonificacion, double montoPagado, String fechaHora) {
        this.puesto = puesto;
        this.matricula = matricula;
        this.categoria = categoria;
        this.montoTarifa = montoTarifa;
        this.bonificacionNombre = bonificacionNombre;
        this.montoBonificacion = montoBonificacion;
        this.montoPagado = montoPagado;
        this.fechaHora = fechaHora;
    }

    public static List<TransitoDto> listaDtos(List<Transito> transitosList, Propietario propietario) {
        List<TransitoDto> lista = new ArrayList<>();

        transitosList.sort(Comparator.comparing(Transito::getFechaHora).reversed());

        for (Transito t : transitosList) {
            double montoTarifa = t.getMontoACobrar() + t.getMontoBonificacion();
            BonificacionPropietarioPuesto bonif = propietario.buscarBonificacionPorPuesto(t.getPuesto());

            TransitoDto dto = new TransitoDto(
                t.getPuesto().getNombre(),
                t.getVehiculo().getMatricula(),
                t.getVehiculo().getCategoria().getNombre(),
                montoTarifa,
                bonif != null ? bonif.getBonificacion().getNombre() : "",
                t.getMontoBonificacion(),
                t.getMontoACobrar(),
                t.getFechaHora().toString()
            );

            lista.add(dto);
        }

        return lista;
    }
}
