package uy.edu.ort.obligatorio.peajes.interfaces;

import uy.edu.ort.obligatorio.peajes.dominio.Transito;

public interface EstrategiaCalculoDescuento {
    double calcularDescuento(Transito transito);
}
