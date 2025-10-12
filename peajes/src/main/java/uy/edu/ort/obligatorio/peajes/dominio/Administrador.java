package uy.edu.ort.obligatorio.peajes.dominio;

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

}
