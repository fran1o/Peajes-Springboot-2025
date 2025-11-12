package uy.edu.ort.obligatorio.peajes.dominio;

public class BonificacionExonerados extends TipoBonificacion {

    public BonificacionExonerados() {
        super("Exonerados", 100);
    }

    @Override
    public double calcularDescuento(Transito transito) {
        Tarifa tarifa = transito.getPuesto().buscarTarifaPorCategoria(transito.getVehiculo().getCategoria());
        return tarifa.getMonto();
    }
    
}