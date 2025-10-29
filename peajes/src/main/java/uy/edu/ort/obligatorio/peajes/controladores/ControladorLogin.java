package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/acceso")
public class ControladorLogin {

    @PostMapping("/loginAdministrador")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String contrasena) throws UsuarioException {
        Administrador usuarioLogueado = Fachada.getInstancia().loginAdministrador(cedula,contrasena);

        sesionHttp.setAttribute("usuarioLogueado", usuarioLogueado);
        return Respuesta.lista(new Respuesta("loginExitoso", "menuAdmin.html"));
    }


    @PostMapping("/loginPropietario")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula,@RequestParam String contrasena) throws UsuarioException {
        Propietario usuarioLogueado = Fachada.getInstancia().loginPropietario(cedula,contrasena);

        sesionHttp.setAttribute("usuarioLogueado", usuarioLogueado);
        return Respuesta.lista(new Respuesta("loginExitoso", "menuPropietario.html"));
    }
}
