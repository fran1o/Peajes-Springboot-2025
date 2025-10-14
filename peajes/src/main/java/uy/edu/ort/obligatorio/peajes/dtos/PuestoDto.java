package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;

public class PuestoDto {
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifas;
    private List<Transito> transitos;
    

    public PuestoDto(String nombre, String direccion) {
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
    public static List<PuestoDto> listaDtos(List<Puesto> puestos) {
        List<PuestoDto> lista = new ArrayList<>();
        for (Puesto p : puestos) {
            PuestoDto dto = new PuestoDto(p.getNombre(), p.getDireccion());
            lista.add(dto);
            dto.tarifas = p.getTarifas();
            dto.transitos = p.getTransitos(); 
        }
        return lista;
        
    }

}
