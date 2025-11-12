package uy.edu.ort.obligatorio.peajes.dominio.estrategias;

import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.interfaces.EstrategiaCalculoDescuento;

public class EstrategiaExonerados implements EstrategiaCalculoDescuento {

    @Override
    public double calcularDescuento(Transito transito) {
        Tarifa tarifa = transito.getPuesto().buscarTarifaPorCategoria(transito.getVehiculo().getCategoria());
        return tarifa.getMonto();
    }
}
