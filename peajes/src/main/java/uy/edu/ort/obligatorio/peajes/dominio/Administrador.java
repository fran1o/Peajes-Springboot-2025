package uy.edu.ort.obligatorio.peajes.dominio;

import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;

public class Administrador extends Usuario {

    private boolean estaLogueado;

    public Administrador(String cedula, String nombreCompleto, String contrasena) {
        super(cedula, nombreCompleto, contrasena);
        this.estaLogueado = false;
    }

    public boolean estaLogueado() {
        return estaLogueado;
    }

    public void setEstaLogueado(boolean estaLogueado) {
        this.estaLogueado = estaLogueado;
    }

    @Override
    public void logout() {
        this.estaLogueado = false;
    }

    @Override
    public boolean validarLogin() throws UsuarioException {
        
        if(this.estaLogueado) {
            throw new UsuarioException("Ud. ya esta logueado");
        } else {
            this.estaLogueado = true;
        }

        return true;
    }
    

}
