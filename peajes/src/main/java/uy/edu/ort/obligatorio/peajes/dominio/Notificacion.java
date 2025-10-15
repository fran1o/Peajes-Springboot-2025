package uy.edu.ort.obligatorio.peajes.dominio;

import java.time.LocalDateTime;

public class Notificacion {
    private LocalDateTime fechaHora;
    private String mensaje;
    private Propietario propietario;

    public Notificacion(LocalDateTime fechaHora, String mensaje, Propietario propietario) {
        this.fechaHora = fechaHora;
        this.mensaje = mensaje;
        this.propietario = propietario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Propietario getPropietario() {
        return propietario;
    }
}
