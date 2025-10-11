package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDateTime;

public class Transito {
    private Vehiculo vehiculo;
    private Puesto puesto;
    private LocalDateTime fechaHora;
    private double montoACobrar; //Se buscaria en el Puesto la tarifa que corresponde a la categoría del vehículo y asigna el monto a cobrar.
    

}
