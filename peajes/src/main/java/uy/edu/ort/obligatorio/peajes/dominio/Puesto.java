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

    public void registrarTransito(Transito transito) {
        transitos.add(transito);
    }

    public Tarifa buscarTarifaPorCategoria(Categoria categoria) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getCategoria().equals(categoria)) {
                return tarifa;
            }
        }
        return null;
    }

    public void agregarTarifa(Tarifa tarifa) {
        tarifas.add(tarifa);
    }
    
     
    
}
