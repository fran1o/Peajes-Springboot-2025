package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Vehiculo {
    private String matricula;
    private String modelo;
    private String color;
    private Categoria categoria;
    private Propietario propietario;
    private List<Transito> transitos;

    public Vehiculo(String matricula, String modelo, String color, Categoria categoria, Propietario propietario) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
        this.propietario = propietario;
        this.transitos = new ArrayList<>();
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public int calculateTransitosDelDia(LocalDate fecha) {
        int count = 0;
        for (Transito transito : transitos) {
            if (transito.getFechaHora().toLocalDate().equals(fecha)) {
                count++;
            }
        }
        return count;
    }

    public double calculateMontoTotalGastado() {
        double total = 0;
        for (Transito transito : transitos) {
            total += transito.getMontoACobrar();
        }
        return total;
    }

    public void agregarTransito(Transito transito) {
        transitos.add(transito);
    }

}
