package uy.edu.ort.obligatorio.peajes.dominio;

import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

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

    public String getContrasena() {
        return contrasena;
    }

    
}
