package uy.edu.ort.obligatorio.peajes.excepciones;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final int errorCodeStatus = 299;
    @ExceptionHandler(UsuarioException.class)

    public ResponseEntity<String> manejarException(UsuarioException ex) {
        return ResponseEntity.status(errorCodeStatus).body(ex.getMessage());
    }

}
