package uy.edu.ort.obligatorio.peajes.servicios;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;

public class ServicioUsuarios {

    private List<Usuario> usuarios;
    private List<Usuario> administradoresLogueados;

    public ServicioUsuarios() {
        this.usuarios = new ArrayList<>();
        this.administradoresLogueados = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario login(String cedula, String contrasena) throws UsuarioException {
        Usuario usuarioEncontrado = null;
        
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula) && u.getContrasena().equals(contrasena)) {
                usuarioEncontrado = u;
                if(u instanceof Administrador) {
                    Administrador admin = (Administrador) u;
                    if(administradoresLogueados.contains(admin)) {
                        admin.setEstaLogueado(true);
                        
                    }else{
                        administradoresLogueados.add(admin);
                    }
                    
                }
                break;
            }
        }

        if(usuarioEncontrado == null){
            throw new UsuarioException("Acceso denegado");
        }
        if (usuarioEncontrado != null && usuarioEncontrado instanceof Propietario) {
            Propietario propietario = (Propietario) usuarioEncontrado;

            if (propietario.getEstado().estaDeshabilitado()) {
                throw new UsuarioException("Usuario deshabilitado, no puede ingresar al sistema");
            }
        }

        if (usuarioEncontrado != null && usuarioEncontrado instanceof Administrador) {
            Administrador admin = (Administrador) usuarioEncontrado;

            if (admin.estaLogueado()) {
                throw new UsuarioException("Ud. ya esta logueado");
            }
        }

        return usuarioEncontrado;   
    }
    
}
