package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/accesopropietario")
public class ControladorPropietario {
     


    @PostMapping("/login")
    public List<Respuesta> login(@RequestParam String cedula, @RequestParam String contrasena) {
        Usuario usuario = Fachada.getInstancia().login(cedula, contrasena);

        if(usuario == null){
            return Respuesta.lista(new Respuesta("error", "Acceso denegado"));
        }
        if (usuario != null && usuario instanceof Propietario) {
            Propietario propietario = (Propietario) usuario;

            if (propietario.getEstado().estaDeshabilitado()) {
                return Respuesta.lista(new Respuesta("error", "Usuario deshabilitado, no puede ingresar al sistema"));
            }
        }
        //tableroPropietario(cedula);
        return Respuesta.lista(new Respuesta("loginExitoso", "homePropietario.html"));
    }

    @PostMapping("/tableroPropietario")
    public List<Respuesta> tableroPropietario(@RequestParam String cedula) {
        return Respuesta.lista(new Respuesta("tableroPropietario", "tableroPropietario.html"));
    }

}
