package uy.edu.ort.obligatorio.peajes.servicios;

import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;

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

    public Usuario login(String cedula, String contrasena) throws UsuarioException {
        Usuario usuario = servicioUsuarios.login(cedula, contrasena);
        return usuario;
    }

    public List<Puesto> getPuestos() {
        return servicioPuestos.getPuestos();
    }

    public void agregarPuesto(Puesto puesto) {
        servicioPuestos.agregarPuesto(puesto);
    }

    
}