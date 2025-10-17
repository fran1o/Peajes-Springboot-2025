package uy.edu.ort.obligatorio.peajes.dominio;

import java.util.ArrayList;
import java.util.List;

public class Puesto {

    private int id;
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

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }
    public List<Transito> getTransitos(){
        return transitos;
    }
    
     
    
}
