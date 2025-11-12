package uy.edu.ort.obligatorio.peajes.observer;

public interface Observador {

    public enum Evento{
        ESTADOPROPIETARIO_ACTUALIZADO
    }

    void actualizar(Observable origen, Object evento);
}
