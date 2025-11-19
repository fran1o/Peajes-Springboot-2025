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

    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(HttpSession session) {
        Usuario usuarioPropietario = (Usuario) session.getAttribute("usuarioPropietario");
        if (usuarioPropietario == null) {
            return Respuesta.lista(new Respuesta("usuarioNoConectado", "loginPropietario.html"));
        }

        return Respuesta.lista(new Respuesta("vistaConectada", usuarioPropietario.getNombreCompleto()));
    }

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sessionHttp) throws UsuarioException {
        Usuario usuario = (Usuario) sessionHttp.getAttribute("usuarioPropietario");
        if (usuario != null) {
            Fachada.getInstancia().logout(usuario);
            sessionHttp.removeAttribute("usuarioPropietario");
            sessionHttp.invalidate();
        }
        return Respuesta.lista(new Respuesta("usuarioNoConectado", "loginPropietario.html"));
    }
}
