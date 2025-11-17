package uy.edu.ort.obligatorio.peajes.observer;

public interface Observador {

    public enum Evento{
        ESTADOPROPIETARIO_ACTUALIZADO,
        ESTADOPROPIETARIO_NUEVOTRANSITO,
        ESTADOPROPIETARIO_NUEVABONIFICACION,
        ESTADOPROPIETARIO_NUEVANOTIFICACION,
        ESTADOPROPIETARIO_NOTIFICACIONESBORRADAS
    }

    void actualizar(Observable origen, Object evento);
}
