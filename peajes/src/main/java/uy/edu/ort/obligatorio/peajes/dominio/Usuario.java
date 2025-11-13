package uy.edu.ort.obligatorio.peajes.dominio;

import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;

public abstract class Usuario {
    private String cedula;
    private String nombreCompleto;
    private String contrasena;

    public Usuario(String cedula, String nombreCompleto, String contrasena) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.contrasena = contrasena;
    }

    public String getCedula() {
        return cedula;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public boolean esContrasenaValida(String contrasenia) {
        return this.contrasena.equals(contrasenia);
    }

    public abstract void logout() throws UsuarioException;

    public abstract boolean validarLogin() throws UsuarioException;
    

    
}
