package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.dtos.PuestoDto;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/admin")
public class ControladorAdministrador {

    private Usuario usuarioLogueado = null;

    @PostMapping("/login")
    public List<Respuesta> login(HttpSession sessionHttp, @RequestParam String cedula, @RequestParam String contrasena) throws UsuarioException{

        usuarioLogueado = Fachada.getInstancia().login(cedula, contrasena);
        sessionHttp.setAttribute("usuarioLogueado", usuarioLogueado);
        return Respuesta.lista(new Respuesta("loginExitoso", "menuAdmin.html"));

    }

    
    @PostMapping("/cargarPuestos")
    public List<Respuesta> cargarPuestos() {
       return Respuesta.lista(puestos());
    }
    
    private Respuesta puestos() {
        return new Respuesta("puestos", PuestoDto.listaDtos(Fachada.getInstancia().getPuestos()));
    }

    /*
    @PostMapping("/cargarTarifas")
    public List<Respuesta> cargartarifas(@RequestParam PuestoDto puestoDto) {
       return Respuesta.lista(tarifas(puestoDto));
    }

    private Respuesta tarifas(@RequestParam PuestoDto puestoDto) {
        if(puestoDto != null) {
            for(PuestoDto p : PuestoDto.listaDtos(Fachada.getInstancia().getPuestos())) {
                if(p.equals(puestoDto)) {
                    return new Respuesta("tarifas", p.getTarifas());
                }
            }
        }
        return new Respuesta("puestos", "No se encontraron tarifas para el puesto seleccionado");
    } */
    
}