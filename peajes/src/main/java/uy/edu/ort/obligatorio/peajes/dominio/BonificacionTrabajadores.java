package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.DayOfWeek;

public class BonificacionTrabajadores extends Bonificacion {

    public BonificacionTrabajadores() {
        super("Bonificación para trabajadores del peaje", 80);
    }

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
