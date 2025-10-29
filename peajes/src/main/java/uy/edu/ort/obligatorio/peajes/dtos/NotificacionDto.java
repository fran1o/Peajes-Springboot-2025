package uy.edu.ort.obligatorio.peajes.dtos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Notificacion;

public class NotificacionDto {
    @Getter
    private String fechaHora;
    @Getter
    private String mensaje;

    public NotificacionDto(String fechaHora, String mensaje) {
        this.fechaHora = fechaHora;
        this.mensaje = mensaje;
    }

    public static List<NotificacionDto> listaDtos(List<Notificacion> notificacionesList) {
        List<NotificacionDto> lista = new ArrayList<>();
        notificacionesList.sort(Comparator.comparing(Notificacion::getFechaHora).reversed());

        for (Notificacion n : notificacionesList) {
            NotificacionDto dto = new NotificacionDto(n.getFechaHora().toString(),n.getMensaje());
            
            lista.add(dto);
        }

        return lista;
    }

}
