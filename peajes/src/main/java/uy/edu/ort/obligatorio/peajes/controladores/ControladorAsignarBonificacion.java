package uy.edu.ort.obligatorio.peajes.controladores;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
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
    public List<Respuesta> cargarBonificaciones() {
        System.out.println("se ejecuta");
        List<TipoBonificacionDto> bonificaciones = TipoBonificacionDto.listaDtos(Fachada.getInstancia().getTipoBonificaciones());
        List<PuestoDto> puestos = PuestoDto.listaDtos(Fachada.getInstancia().getPuestos());
        return Respuesta.lista(
            new Respuesta("bonificaciones", bonificaciones),
            new Respuesta("puestos", puestos));
    }

    
    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws UsuarioException {
        if(cedula.isEmpty() || cedula == null){
            throw new UsuarioException("Ingrese una cedula");
        }
        Propietario propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new UsuarioException("No existe el propietario");
        }

        List<BonifiacionDto> bonificacionesDeProp = BonifiacionDto.listaDtos(propietario.getBonificaciones());
        return Respuesta.lista(
                new Respuesta("nombreCompleto", propietario.getNombreCompleto()),
                new Respuesta("estado", propietario.getEstado().getNombreEstado()),
                new Respuesta("bonificacionesDeProp", bonificacionesDeProp));
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(@RequestParam String cedula, @RequestParam String bonificacionNombre,@RequestParam String puestoNombre) throws UsuarioException {

        TipoBonificacion tipoBonificacion = Fachada.getInstancia().getTipoBonificacion(bonificacionNombre);
        Puesto puesto = Fachada.getInstancia().getPuestoPorNombre(puestoNombre);
    
        Fachada.getInstancia().asignarBonificacion(cedula, tipoBonificacion, puesto, LocalDateTime.now());
        List<BonifiacionDto> bonificacionesDeProp = BonifiacionDto.listaDtos(Fachada.getInstancia().buscarPropietarioPorCedula(cedula).getBonificaciones());
        return Respuesta.lista(new Respuesta("mensaje", "Bonificacion asignada con exito"),new Respuesta("resultado", bonificacionesDeProp));
    }

}
