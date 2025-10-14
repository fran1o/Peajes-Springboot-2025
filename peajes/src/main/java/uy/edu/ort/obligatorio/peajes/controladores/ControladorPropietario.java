package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/propietario")
public class ControladorPropietario {
     private Usuario usuarioLogueado = null;


    @PostMapping("/login")
    public List<Respuesta> login(@RequestParam String cedula, @RequestParam String contrasena) throws UsuarioException{

        usuarioLogueado = Fachada.getInstancia().login(cedula, contrasena);
        return Respuesta.lista(new Respuesta("loginExitoso", "homePropietario.html"));
        
    }

    @PostMapping("/tableroPropietario")
    public List<Respuesta> tableroPropietario(@RequestParam String cedula) {
        return Respuesta.lista(new Respuesta("tableroPropietario", "tableroPropietario.html"));
    }

}
