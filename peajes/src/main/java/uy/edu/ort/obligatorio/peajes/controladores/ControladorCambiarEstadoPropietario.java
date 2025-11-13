package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.observer.Observable;
import uy.edu.ort.obligatorio.peajes.observer.Observador;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.ConexionNavegador;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/admin/propietarios")
public class ControladorCambiarEstadoPropietario implements Observador{

    private Propietario propietarioEncontrado = null;

    private final ConexionNavegador conexionNavegador;

    public ControladorCambiarEstadoPropietario(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }

    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws UsuarioException {
        if(cedula.isEmpty() || cedula == null){
            throw new UsuarioException("Ingrese una cedula");
        }
        propietarioEncontrado = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietarioEncontrado == null) {
            throw new UsuarioException("No existe el propietario");
        }

   
        propietarioEncontrado.getManejador().suscribir(this);
        return Respuesta.lista(
                new Respuesta("nombreCompleto", propietarioEncontrado.getNombreCompleto()),
                new Respuesta("estado", propietarioEncontrado.getEstado().getNombreEstado()));
    }

    @PostMapping("/cargarEstados")
    public List<Respuesta> cargarEstados() {
        List<Map<String, String>> estados = new ArrayList<>();

        Map<String, String> habilitado = new HashMap<>();
        habilitado.put("nombre", "Habilitado");
        estados.add(habilitado);

        Map<String, String> deshabilitado = new HashMap<>();
        deshabilitado.put("nombre", "Deshabilitado");
        estados.add(deshabilitado);

        Map<String, String> suspendido = new HashMap<>();
        suspendido.put("nombre", "Suspendido");
        estados.add(suspendido);

        Map<String, String> penalizado = new HashMap<>();
        penalizado.put("nombre", "Penalizado");
        estados.add(penalizado);

        return Respuesta.lista(new Respuesta("estados", estados));
    }

    @PostMapping("/cambiarEstado")
    public List<Respuesta> cambiarEstado(@RequestParam String cedula, @RequestParam String nuevoEstado) throws UsuarioException {
        Fachada.getInstancia().cambiarEstadoPropietario(cedula, nuevoEstado);
        return Respuesta.lista(new Respuesta("estado", nuevoEstado));
    }

    @Override
    public void actualizar(Observable origen, Object evento) {
        conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("estado", propietarioEncontrado.getEstado().getNombreEstado())));
    }
}
