package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dtos.BonifiacionDto;
import uy.edu.ort.obligatorio.peajes.dtos.NotificacionDto;
import uy.edu.ort.obligatorio.peajes.dtos.TransitoDto;
import uy.edu.ort.obligatorio.peajes.dtos.VehiculoDto;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/propietario")
public class ControladorPropietario {


    @PostMapping("/cargarVistaInicial")
    public List<Respuesta> cargarDashboard(HttpSession session) {
        Propietario propietario = (Propietario) session.getAttribute("usuarioLogueado");

        String nombreCompleto = propietario.getNombreCompleto();
        String estado = obtenerNombreEstado(propietario);
        double saldoActual = propietario.getSaldoActual();

        List<BonifiacionDto> bonificaciones = BonifiacionDto.listaDtos(propietario.getBonificaciones());
        List<VehiculoDto> vehiculos = VehiculoDto.listaDtos(propietario.getVehiculos());
        List<Transito> transitosList = Fachada.getInstancia().getTransitosPorPropietario(propietario);
        List<TransitoDto> transitos = TransitoDto.listaDtos(transitosList, propietario);
        List<NotificacionDto> notificaciones = NotificacionDto.listaDtos(propietario.getNotificaciones());

        return Respuesta.lista(
                new Respuesta("nombreCompleto", nombreCompleto),
                new Respuesta("estado", estado),
                new Respuesta("saldoActual", saldoActual),
                new Respuesta("bonificaciones", bonificaciones),
                new Respuesta("vehiculos", vehiculos),
                new Respuesta("transitos", transitos),
                new Respuesta("notificaciones", notificaciones));
    }

    

    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(HttpSession session) throws UsuarioException {
        Propietario propietario = (Propietario) session.getAttribute("usuarioLogueado");

        if (propietario.getNotificaciones().isEmpty()) {
            throw new UsuarioException("No hay notificaciones para borrar");
        }

        propietario.borrarNotificaciones();
        return Respuesta.lista(new Respuesta("notificacionesBorradas", "Notificaciones eliminadas exitosamente"));
    }

    private String obtenerNombreEstado(Propietario propietario) {
        return propietario.getEstado().getNombreEstado();
    }

}
