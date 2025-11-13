package uy.edu.ort.obligatorio.peajes.controladores;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/admin")
public class ControladorAdministrador {    

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession session) {
        Administrador admin = (Administrador) session.getAttribute("usuarioLogueado");
        if (admin != null) {
            admin.setEstaLogueado(false);
        }
        session.invalidate();
        return Respuesta.lista(new Respuesta("resultado", "Logout exitoso"));
    }

}