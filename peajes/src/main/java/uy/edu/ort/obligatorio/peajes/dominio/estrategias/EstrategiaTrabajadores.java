package uy.edu.ort.obligatorio.peajes.dominio.estrategias;

import java.time.DayOfWeek;

import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.interfaces.EstrategiaCalculoDescuento;

public class EstrategiaTrabajadores implements EstrategiaCalculoDescuento {

    @Override
    public double calcularDescuento(Transito transito) {
        DayOfWeek diaSemana = transito.getFechaHora().getDayOfWeek();
        boolean esFinDeSemana = (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY);

        if (esFinDeSemana) {
            return 0;
        } else {
            Tarifa tarifa = transito.getPuesto().buscarTarifaPorCategoria(transito.getVehiculo().getCategoria());
            return tarifa.getMonto() * 0.8;
        }
    }
}
