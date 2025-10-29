package uy.edu.ort.obligatorio.peajes.dominio;

public class Tarifa {
    private double monto;
    private Categoria categoria;

    public Tarifa(Categoria categoria, double monto) {
        this.categoria = categoria;
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    
}
