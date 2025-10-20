package uy.edu.ort.obligatorio.peajes.controladores;

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

    @PostMapping("/cargarMenu")
    public List<Respuesta> cargarMenu(HttpSession session) {
        Administrador admin = (Administrador) session.getAttribute("usuarioLogueado");

        if (admin == null) {
            return Respuesta.lista(new Respuesta("error", "No hay sesión activa"));
        }

        List<Map<String, String>> opciones = new ArrayList<>();

        Map<String, String> emularTransito = new HashMap<>();
        emularTransito.put("nombre", "Emular Tránsito");
        emularTransito.put("url", "emularTransito.html");
        opciones.add(emularTransito);

        Map<String, String> asignarBonificacion = new HashMap<>();
        asignarBonificacion.put("nombre", "Asignar Bonificación");
        asignarBonificacion.put("url", "asignarBonificaciones.html");
        opciones.add(asignarBonificacion);

        Map<String, String> cambiarEstado = new HashMap<>();
        cambiarEstado.put("nombre", "Cambiar Estado Propietario");
        cambiarEstado.put("url", "cambiarEstado.html");
        opciones.add(cambiarEstado);

        return Respuesta.lista(
                new Respuesta("nombreAdmin", admin.getNombreCompleto()),
                new Respuesta("opciones", opciones));
    }

}
