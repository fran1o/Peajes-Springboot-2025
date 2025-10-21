package uy.edu.ort.obligatorio.peajes.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Notificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

public class ServicioUsuarios {

    private List<Usuario> usuarios;
    private List<Usuario> usuariosLogueados;

    public ServicioUsuarios() {
        this.usuarios = new ArrayList<>();
        this.usuariosLogueados = new ArrayList<>();
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
                break;
            }
        }

        if (usuarioEncontrado == null) {
            throw new UsuarioException("Acceso denegado");
        }

        if(usuarioEncontrado.validarLogin()){
            usuariosLogueados.add(usuarioEncontrado);
        }

        return usuarioEncontrado;
    }

    public void logout(Usuario usuario) throws UsuarioException {
        usuario.logout();
        usuariosLogueados.remove(usuario);
    }
    
    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Propietario) {
                Propietario propietario = (Propietario) usuario;
                Vehiculo vehiculo = propietario.buscarVehiculo(matricula);
                if (vehiculo != null) {
                    return vehiculo;
                }
            }
        }
        return null;
    }

    public Propietario buscarPropietarioPorVehiculo(Vehiculo vehiculo) {
        return vehiculo.getPropietario();
    }

    public Propietario buscarPropietarioPorCedula(String cedula) {
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Propietario && usuario.getCedula().equals(cedula)) {
                return (Propietario) usuario;
            }
        }
        return null;
    }

    public void cambiarEstadoPropietario(String cedula, EstadoPropietario nuevoEstado) throws UsuarioException {
        Propietario propietario = buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new UsuarioException("no existe el propietario");
        }

        String nombreEstadoActual = obtenerNombreEstado(propietario.getEstado());
        String nombreNuevoEstado = obtenerNombreEstado(nuevoEstado);

        if (propietario.getEstado().getClass().equals(nuevoEstado.getClass())) {
            throw new UsuarioException("El propietario ya esta en estado " + nombreEstadoActual);
        }

        propietario.setEstado(nuevoEstado);
        Notificacion notificacion = new Notificacion(
                LocalDateTime.now(),
                "Se ha cambiado tu estado en el sistema. Tu estado actual es " + nombreNuevoEstado,
                propietario);
        propietario.agregarNotificacion(notificacion);
    }

    public void asignarBonificacion(String cedulaPropietario, Bonificacion bonificacion, Puesto puesto,
            LocalDateTime fecha) throws UsuarioException {
        if (bonificacion == null) {
            throw new UsuarioException("Debe especificar una bonificación");
        }
        if (puesto == null) {
            throw new UsuarioException("Debe especificar un puesto");
        }

        Propietario propietario = buscarPropietarioPorCedula(cedulaPropietario);
        if (propietario == null) {
            throw new UsuarioException("no existe el propietario");
        }

        if (propietario.getEstado().estaDeshabilitado()) {
            throw new UsuarioException("El propietario esta deshabilitado. No se pueden asignar bonificaciones");
        }

        if (propietario.tieneBonificacionEnPuesto(puesto)) {
            throw new UsuarioException("Ya tiene una bonificación asignada para ese puesto");
        }

        bonificacion.setPuesto(puesto);
        bonificacion.setFechaAsignacion(fecha);
        propietario.agregarBonificacion(bonificacion);
    }

    private String obtenerNombreEstado(EstadoPropietario estado) {
        if (estado.estaHabilitado()) {
            return "Habilitado";
        } else if (estado.estaDeshabilitado()) {
            return "Deshabilitado";
        } else if (estado.estaSuspendido()) {
            return "Suspendido";
        } else if (estado.estaPenalizado()) {
            return "Penalizado";
        }
        return "Desconocido";
    }

}
