package uy.edu.ort.obligatorio.peajes.servicios;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;

public class Fachada {

    private static Fachada instancia;
    private ServicioPuestos servicioPuestos;
    private ServicioUsuarios servicioUsuarios;

    private Fachada(){

        servicioPuestos = new ServicioPuestos();
        servicioUsuarios = new ServicioUsuarios();
    }

    public static Fachada getInstancia(){
        if(instancia == null){
            instancia = new Fachada();
        }
        return instancia;
    }

    public void agregarUsuario(Usuario usuario) {
        servicioUsuarios.agregarUsuario(usuario);
    }

    public Usuario login(String cedula, String contrasena) {
        Usuario usuario = servicioUsuarios.login(cedula, contrasena);
        return usuario;
    }

    
}