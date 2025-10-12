package uy.edu.ort.obligatorio.peajes.servicios;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;

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

    public Usuario login(String cedula, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getCedula().equals(cedula) && u.getContrasena().equals(contrasena)) {
                if(u instanceof Administrador) {
                    Administrador admin = (Administrador) u;
                    if(administradoresLogueados.contains(admin)) {
                        admin.setEstaLogueado(true);
                        
                    }else{
                        administradoresLogueados.add(admin);
                    }
                    
                }
                return u;
            }
        }
        return null;
    }
    
}
