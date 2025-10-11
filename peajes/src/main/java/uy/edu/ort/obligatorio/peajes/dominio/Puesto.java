package uy.edu.ort.obligatorio.peajes.dominio;

import java.util.ArrayList;
import java.util.List;

public class Puesto {
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifas;
    private List<Transito> transitos;
    

    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        tarifas = new ArrayList<>();
        transitos = new ArrayList<>();
    }
     
    
}
