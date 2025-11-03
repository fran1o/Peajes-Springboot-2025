package uy.edu.ort.obligatorio.peajes.servicios;

import java.time.LocalDateTime;
import java.util.List;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Sesion;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;

public class Fachada {

    private static Fachada instancia;
    private ServicioPuestos servicioPuestos;
    private ServicioUsuarios servicioUsuarios;
    private ServicioTransitos servicioTransitos;

    private Fachada() {
        servicioPuestos = new ServicioPuestos();
        servicioUsuarios = new ServicioUsuarios();
        servicioTransitos = new ServicioTransitos();
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    public void agregarAdministrador(Administrador admin) {
        servicioUsuarios.agregarAdministrador(admin);
    }

    public void agregarPropietario(Propietario propietario) {
        servicioUsuarios.agregarPropietario(propietario);
    }

    public Administrador loginAdministrador(String cedula, String contrasenia) throws UsuarioException {
        return servicioUsuarios.loginAdministrador(cedula, contrasenia);
    }

    public Propietario loginPropietario(String cedula, String contrasenia) throws UsuarioException {
        return servicioUsuarios.loginPropietario(cedula, contrasenia);
    }

    public List<Puesto> getPuestos() {
        return servicioPuestos.getPuestos();
    }

    public List<Sesion> getSesionesActivas() {
        return servicioUsuarios.getSesionesActivas();
    }

    public void agregarPuesto(Puesto puesto) {
        servicioPuestos.agregarPuesto(puesto);
    }

    public void logout(Usuario usuario) throws UsuarioException {
        servicioUsuarios.logout(usuario);

    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        return servicioUsuarios.buscarVehiculoPorMatricula(matricula);
    }

    public Propietario buscarPropietarioPorCedula(String cedula) {
        return servicioUsuarios.buscarPropietarioPorCedula(cedula);
    }

    public void cambiarEstadoPropietario(String cedula, EstadoPropietario nuevoEstado) throws UsuarioException {
        servicioUsuarios.cambiarEstadoPropietario(cedula, nuevoEstado);
    }

    public void asignarBonificacion(String cedulaPropietario, Bonificacion bonificacion, Puesto puesto,
            LocalDateTime fecha) throws UsuarioException {
        servicioUsuarios.asignarBonificacion(cedulaPropietario, bonificacion, puesto, fecha);
    }

    public Transito emularTransito(String matricula, Puesto puesto, LocalDateTime fechaHora) throws UsuarioException {
        Vehiculo vehiculo = servicioUsuarios.buscarVehiculoPorMatricula(matricula);
        if (vehiculo == null) {
            throw new UsuarioException("No se encontró el vehículo con matrícula: " + matricula);
        }
        Propietario propietario = vehiculo.getPropietario();
        if (propietario == null) {
            throw new UsuarioException("No se encontró el propietario del vehículo con matrícula: " + matricula);
        }
        return servicioTransitos.emularTransito(matricula, puesto, fechaHora, vehiculo, propietario);
    }

    public List<Transito> getTransitosPorPropietario(Propietario propietario) {
        return servicioTransitos.getTransitosPorPropietario(propietario);
    }

}