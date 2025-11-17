package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/menuPropietario")
public class ControladorMenuPropietario {

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sessionHttp) throws UsuarioException {
        Usuario usuario = (Usuario) sessionHttp.getAttribute("usuarioLogueado");
        Fachada.getInstancia().logout(usuario);
        return Respuesta.lista(new Respuesta("usuarioNoConectado", "loginPropietario.html"));
    }
}
