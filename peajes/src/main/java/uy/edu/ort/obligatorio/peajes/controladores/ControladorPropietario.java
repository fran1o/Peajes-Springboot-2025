package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dtos.BonifiacionDto;
import uy.edu.ort.obligatorio.peajes.dtos.NotificacionDto;
import uy.edu.ort.obligatorio.peajes.dtos.TransitoDto;
import uy.edu.ort.obligatorio.peajes.dtos.VehiculoDto;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.observer.Observable;
import uy.edu.ort.obligatorio.peajes.observer.Observador;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.ConexionNavegador;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@Scope("session")
@RequestMapping("/propietario")
public class ControladorPropietario implements Observador{
    private final ConexionNavegador conexionNavegador;
    private Propietario propietario;

    public ControladorPropietario(@Autowired ConexionNavegador conexionNavegador){
        this.conexionNavegador = conexionNavegador;
    }

    @PostMapping("/cargarVistaInicial")
    public List<Respuesta> cargarDashboard(HttpSession session) {
        propietario = (Propietario) session.getAttribute("usuarioPropietario");
        if (propietario == null) {
             return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "loginPropietario.html"));
        }
        if(propietario != null){
            propietario.suscribir(this);
        }

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

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }
    
    

    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(HttpSession session) throws UsuarioException {
        if (propietario.getNotificaciones().isEmpty()) {
            throw new UsuarioException("No hay notificaciones para borrar");
        }

        propietario.borrarNotificaciones();
        return Respuesta.lista(new Respuesta("mensaje" , "Notificaciones borradas correctamente"));
    }
    private String obtenerNombreEstado(Propietario propietario) {
        return propietario.getEstado().getNombreEstado();
    }

    @PostMapping("/vistaDesconectada")
    public void cerrarVista(HttpSession session) {
        Propietario propietario = (Propietario) session.getAttribute("usuarioPropietario");

        if (propietario != null) {
            propietario.desubscribir(this);
        }
        session.removeAttribute("usuarioPropietario");
        session.invalidate();
    }

    

    @Override
    public void actualizar(Observable origen, Object evento) {
        if(evento == Evento.ESTADOPROPIETARIO_ACTUALIZADO){      
            conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("estado", propietario.getEstado().getNombreEstado())));
        }

        if(evento == Evento.ESTADOPROPIETARIO_NUEVOTRANSITO){      
            List<Transito> transitosList = Fachada.getInstancia().getTransitosPorPropietario(propietario);
            List<TransitoDto> transitos = TransitoDto.listaDtos(transitosList, propietario);
            conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("transitos", transitos)));
            conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("saldoActual", propietario.getSaldoActual())));
        }

        if(evento == Evento.ESTADOPROPIETARIO_NUEVANOTIFICACION || evento == Evento.ESTADOPROPIETARIO_NOTIFICACIONESBORRADAS){      
            List<NotificacionDto> notificaciones = NotificacionDto.listaDtos(propietario.getNotificaciones());
            conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("notificaciones", notificaciones)));
        }

        if(evento == Evento.ESTADOPROPIETARIO_NUEVABONIFICACION){      
            List<BonifiacionDto> bonificaciones = BonifiacionDto.listaDtos(propietario.getBonificaciones());
            conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("bonificaciones", bonificaciones)));
        }
    }

}
