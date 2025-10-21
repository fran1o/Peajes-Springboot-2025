package uy.edu.ort.obligatorio.peajes.controladores;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/menuAdmin")
public class ControladorMenuAdmin {


    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute (name = "usuarioLogueado", required = false) Usuario usuarioLogueado) {
        if(usuarioLogueado == null) {
            return Respuesta.lista(new Respuesta("usuarioNoConectado", "loginAdmin.html"));
            
        } 
        
        return Respuesta.lista(new Respuesta("vistaConectada", usuarioLogueado.getNombreCompleto()));
        
        
    }


    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sessionHttp) throws UsuarioException {
        Usuario usuario = (Usuario) sessionHttp.getAttribute("usuarioLogueado");
        if(usuario != null) {
            sessionHttp.removeAttribute("usuarioLogueado");
            sessionHttp.invalidate();
            Fachada.getInstancia().logout(usuario);
        }
        return Respuesta.lista(new Respuesta("usuarioNoConectado", "loginAdmin.html"));
    }

}
