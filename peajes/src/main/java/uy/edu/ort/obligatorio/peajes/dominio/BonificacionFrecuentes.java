package uy.edu.ort.obligatorio.peajes.dominio;

public class BonificacionFrecuentes extends Bonificacion {


    public BonificacionFrecuentes() {
        super("Bonificación para vehículos frecuentes", 50);
    }

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
