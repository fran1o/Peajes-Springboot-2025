package uy.edu.ort.obligatorio.peajes.dominio.estrategias;

import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.interfaces.EstrategiaCalculoDescuento;

public class EstrategiaFrecuentes implements EstrategiaCalculoDescuento {

    @Override
    public double calcularDescuento(Transito transito) {
        Vehiculo vehiculo = transito.getVehiculo();
        int transitosDelDia = vehiculo.calculateTransitosDelDia(transito.getFechaHora().toLocalDate());

        if (transitosDelDia == 0) {
            return 0;
        } else {
            Tarifa tarifa = transito.getPuesto().buscarTarifaPorCategoria(vehiculo.getCategoria());
            return tarifa.getMonto() * 0.5;
        }
    }
}
