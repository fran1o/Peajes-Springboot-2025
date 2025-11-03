package uy.edu.ort.obligatorio.peajes.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ControladorError implements ErrorController {

    @RequestMapping("/error")
    public String manejarError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "redirect:/404.html";
            }
            // Puedes agregar más casos para otros códigos de error (500, 403, etc.)
        }

        // Por defecto redirigir a 404
        return "redirect:/404.html";
    }

}
