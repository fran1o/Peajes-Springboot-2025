package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dtos.BonificacionDto;
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
        Propietario propietario = obtenerPropietarioDesdeSesion(session);
        return Respuesta.lista(
                nombreCompleto(propietario),
                estado(propietario),
                saldoActual(propietario),
                bonificaciones(propietario),
                vehiculos(propietario),
                transitos(propietario),
                notificaciones(propietario));
    }

    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(HttpSession session) throws UsuarioException {
        Propietario propietario = obtenerPropietarioDesdeSesion(session);

        if (propietario.getNotificaciones().isEmpty()) {
            throw new UsuarioException("No hay notificaciones para borrar");
        }

        propietario.borrarNotificaciones();
        return Respuesta.lista(new Respuesta("notificacionesBorradas", "Notificaciones eliminadas exitosamente"));
    }

    private Propietario obtenerPropietarioDesdeSesion(HttpSession session) {
        return (Propietario) session.getAttribute("usuarioLogueado");
    }

    private Respuesta nombreCompleto(Propietario propietario) {
        return new Respuesta("nombreCompleto", propietario.getNombreCompleto());
    }

    private Respuesta estado(Propietario propietario) {
        return new Respuesta("estado", propietario.getEstado().getNombreEstado());
    }

    private Respuesta saldoActual(Propietario propietario) {
        return new Respuesta("saldoActual", propietario.getSaldoActual());
    }

    private Respuesta bonificaciones(Propietario propietario) {
        List<BonificacionDto> bonificacionesDto = BonificacionDto.listaDtos(propietario.getBonificaciones());
        return new Respuesta("bonificaciones", bonificacionesDto);
    }

    private Respuesta vehiculos(Propietario propietario) {
        List<VehiculoDto> vehiculosDto = VehiculoDto.listaDtos(propietario.getVehiculos());
        return new Respuesta("vehiculos", vehiculosDto);
    }

    private Respuesta transitos(Propietario propietario) {
        List<Transito> transitosList = Fachada.getInstancia().getTransitosPorPropietario(propietario);
        List<TransitoDto> transitosDto = TransitoDto.listaDtos(transitosList, propietario);
        return new Respuesta("transitos", transitosDto);
    }

    private Respuesta notificaciones(Propietario propietario) {
        List<NotificacionDto> notificacionesDto = NotificacionDto.listaDtos(propietario.getNotificaciones());
        return new Respuesta("notificaciones", notificacionesDto);
    }

}
