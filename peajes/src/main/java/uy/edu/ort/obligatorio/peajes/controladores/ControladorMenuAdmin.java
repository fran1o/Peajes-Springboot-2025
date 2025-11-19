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


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/menuAdmin")
public class ControladorMenuAdmin {


    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute (name = "usuarioAdmin", required = false) Usuario usuarioAdmin) {
        if(usuarioAdmin == null) {
            return Respuesta.lista(new Respuesta("usuarioNoConectado", "loginAdmin.html"));
            
        } 
        
        return Respuesta.lista(new Respuesta("vistaConectada", usuarioAdmin.getNombreCompleto()));
        
        
    }


    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sessionHttp) throws UsuarioException {
        Usuario usuario = (Usuario) sessionHttp.getAttribute("usuarioAdmin");
        if(usuario != null) {
            Fachada.getInstancia().logout(usuario);
            sessionHttp.removeAttribute("usuarioAdmin");
            sessionHttp.invalidate();
        }
        return Respuesta.lista(new Respuesta("usuarioNoConectado", "loginAdmin.html"));
    }

}
