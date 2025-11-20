package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/admin/propietarios")
public class ControladorCambiarEstadoPropietario{

    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(HttpSession sessionHttp, @RequestParam String cedula, @RequestParam(required = false) String contexto) throws UsuarioException {
        if(cedula.isEmpty() || cedula == null){
            throw new UsuarioException("Ingrese una cedula");
        }
        Propietario propietarioEncontrado = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietarioEncontrado == null) {
            throw new UsuarioException("No existe el propietario");
        }
        if (contexto != null) {
            sessionHttp.setAttribute(contexto + "_propietarioSeleccionado", propietarioEncontrado);
        }
        return Respuesta.lista(
                new Respuesta("nombreCompleto", propietarioEncontrado.getNombreCompleto()),
                new Respuesta("estado", propietarioEncontrado.getEstado().getNombreEstado()));
    }

    @PostMapping("/cargarEstados")
    public List<Respuesta> cargarEstados(HttpSession sessionHttp) {
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

        Propietario prop = (Propietario) sessionHttp.getAttribute("cambiarEstado_propietarioSeleccionado");
         if (prop == null) {
       
            return Respuesta.lista(new Respuesta("estados", estados) );
        }

        return Respuesta.lista(new Respuesta("nombreCompleto", prop.getNombreCompleto()),
                            new Respuesta("estado", prop.getEstado().getNombreEstado()),
                        new Respuesta("estados", estados));
    }

    @PostMapping("/cambiarEstado")
    public List<Respuesta> cambiarEstado(HttpSession sessionHttp, @RequestParam String cedula, @RequestParam String nuevoEstado) throws UsuarioException {
        Propietario prop = (Propietario) sessionHttp.getAttribute("cambiarEstado_propietarioSeleccionado");
        if(prop != null){
            cedula = (String) prop.getCedula();
        }
        Fachada.getInstancia().cambiarEstadoPropietario(cedula, nuevoEstado);
        return Respuesta.lista(new Respuesta("mensaje", "Estado cambiado con exito"),new Respuesta("estado", nuevoEstado));
    }
}
