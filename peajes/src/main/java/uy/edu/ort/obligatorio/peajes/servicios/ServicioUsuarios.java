package uy.edu.ort.obligatorio.peajes.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionPropietarioPuesto;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Sesion;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

public class ServicioUsuarios {
    @Getter
    private List<Sesion> sesionesActivas;
    @Getter
    private List<Administrador> administradores;
    @Getter
    private List<Propietario> propietarios;

    public ServicioUsuarios() {
        this.propietarios = new ArrayList<>();
        this.sesionesActivas = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    public void agregarAdministrador(Administrador admin) {
        administradores.add(admin);
    }

    public void agregarPropietario(Propietario propietario) {
        propietarios.add(propietario);
    }

    public Propietario getPropietario(String cedula){
        for(Propietario p : propietarios){
            if(p.getCedula().equals(cedula)){
                return p;
            }
        }

        return null;
    }
    public Administrador loginAdministrador(String cedula, String contrasenia) throws UsuarioException {
        Administrador usuario = (Administrador) login(cedula, contrasenia, administradores, "Acceso denegado");
        if (usuario.validarLogin()) {
            Sesion sesion = new Sesion(usuario);
            sesion.setFechaInicio(new Date());
            sesionesActivas.add(sesion);
        }
        return usuario;
    }

    public Propietario loginPropietario(String cedula, String contrasenia) throws UsuarioException {
        Propietario usuario =  (Propietario) login(cedula, contrasenia, propietarios, "Acceso denegado");
    
        if (usuario.validarLogin()) {
            Sesion sesion = new Sesion(usuario);
            sesion.setFechaInicio(new Date());
            sesionesActivas.add(sesion);
        }

        return usuario;
    }

    private Usuario login(String cedula, String contrasenia, List<? extends Usuario> usuarios,
            String mensajeLoginIncorrecto) throws UsuarioException {
        for (Usuario usuario : usuarios) {
            if (usuario.getCedula().equals(cedula) && usuario.esContrasenaValida(contrasenia)) {
                return usuario;
            }
        }
        throw new UsuarioException(mensajeLoginIncorrecto);
    }

    public void logout(Usuario usuario) throws UsuarioException {
        usuario.logout();
        sesionesActivas.removeIf(sesion -> sesion.getUsuario().equals(usuario));
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        for (Propietario propietario : propietarios) {
            Vehiculo vehiculo = propietario.buscarVehiculo(matricula);
            if (vehiculo != null) {
                return vehiculo;
            }
        }
        return null;
    }

    public Propietario buscarPropietarioPorCedula(String cedula) throws UsuarioException{
        for (Propietario propietario : propietarios) {
            if (propietario.getCedula().equals(cedula)) {
                return propietario;
            }
        }
        return null;
    }

    public void cambiarEstadoPropietario(String cedula, EstadoPropietario nuevoEstado) throws UsuarioException {
        Propietario propietario = buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new UsuarioException("No existe el propietario");
        }

        if (nuevoEstado == null) {
            throw new UsuarioException("Por favor seleccione un nuevo estado");
        }

        propietario.setEstado(nuevoEstado);
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
            throw new UsuarioException("No existe el propietario");
        }

        if (propietario.getEstado().estaDeshabilitado()) {
            throw new UsuarioException("El propietario esta deshabilitado. No se pueden asignar bonificaciones");
        }

        if (propietario.tieneBonificacionEnPuesto(puesto)) {
            throw new UsuarioException("Ya tiene una bonificación asignada para ese puesto");
        }

        BonificacionPropietarioPuesto boniPropPuesto = new BonificacionPropietarioPuesto(bonificacion, propietario, puesto);
        propietario.agregarBonificacion(boniPropPuesto);
    }



}
