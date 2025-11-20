package uy.edu.ort.obligatorio.peajes.controladores;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;
import uy.edu.ort.obligatorio.peajes.dtos.TipoBonificacionDto;
import uy.edu.ort.obligatorio.peajes.dtos.BonifiacionDto;
import uy.edu.ort.obligatorio.peajes.dtos.PuestoDto;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/admin/bonificaciones")
public class ControladorAsignarBonificacion {

    @PostMapping("/cargarBonificacionesYPuestos")
    public List<Respuesta> cargarBonificaciones(HttpSession sessionHttp) {
        List<TipoBonificacionDto> bonificaciones = TipoBonificacionDto.listaDtos(Fachada.getInstancia().getTipoBonificaciones());
        List<PuestoDto> puestos = PuestoDto.listaDtos(Fachada.getInstancia().getPuestos());

        Propietario prop = (Propietario) sessionHttp.getAttribute("asignarBonificacion_propietarioSeleccionado");
         if (prop == null) {
            return Respuesta.lista(new Respuesta("bonificaciones", bonificaciones),
                                new Respuesta("puestos", puestos) );
        }

        return Respuesta.lista(
            new Respuesta("nombreCompleto", prop.getNombreCompleto()),
                new Respuesta("estado", prop.getEstado().getNombreEstado()),
                new Respuesta("bonificaciones", bonificaciones),
            new Respuesta("puestos", puestos));
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(HttpSession sessionHttp, @RequestParam String cedula, @RequestParam String bonificacionNombre,@RequestParam String puestoNombre) throws UsuarioException {

        TipoBonificacion tipoBonificacion = Fachada.getInstancia().getTipoBonificacion(bonificacionNombre);
        Puesto puesto = Fachada.getInstancia().getPuestoPorNombre(puestoNombre);
    
        Propietario prop = (Propietario) sessionHttp.getAttribute("asignarBonificacion_propietarioSeleccionado");
        if(prop != null){
            cedula = (String) prop.getCedula();
        }

        Fachada.getInstancia().asignarBonificacion(cedula, tipoBonificacion, puesto, LocalDateTime.now());
        List<BonifiacionDto> bonificacionesDeProp = BonifiacionDto.listaDtos(Fachada.getInstancia().buscarPropietarioPorCedula(cedula).getBonificaciones());
        return Respuesta.lista(new Respuesta("mensaje", "Bonificacion asignada con exito"),new Respuesta("resultado", bonificacionesDeProp));
    }

}
