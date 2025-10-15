package uy.edu.ort.obligatorio.peajes.controladores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Notificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/propietario")
public class ControladorPropietario {

    @PostMapping("/login")
    public List<Respuesta> login(HttpSession session, @RequestParam String cedula, @RequestParam String contrasena)
            throws UsuarioException {
        Usuario usuarioLogueado = Fachada.getInstancia().login(cedula, contrasena);
        session.setAttribute("usuarioLogueado", usuarioLogueado);
        return Respuesta.lista(new Respuesta("loginExitoso", "homePropietario.html"));
    }

    @PostMapping("/cargarDashboard")
    public List<Respuesta> cargarDashboard(HttpSession session) {
        Propietario propietario = (Propietario) session.getAttribute("usuarioLogueado");

        String nombreCompleto = propietario.getNombreCompleto();
        String estado = obtenerNombreEstado(propietario);
        double saldoActual = propietario.getSaldoActual();

        List<Map<String, Object>> bonificaciones = new ArrayList<>();
        for (Bonificacion bonif : propietario.getBonificaciones()) {
            Map<String, Object> bonifMap = new HashMap<>();
            bonifMap.put("nombre", bonif.getNombre());
            bonifMap.put("puesto", bonif.getPuesto().getNombre());
            bonifMap.put("fechaAsignacion", bonif.getFechaAsignacion().toString());
            bonificaciones.add(bonifMap);
        }

        List<Map<String, Object>> vehiculos = new ArrayList<>();
        for (Vehiculo vehiculo : propietario.getVehiculos()) {
            Map<String, Object> vehMap = new HashMap<>();
            vehMap.put("matricula", vehiculo.getMatricula());
            vehMap.put("modelo", vehiculo.getModelo());
            vehMap.put("color", vehiculo.getColor());
            vehMap.put("cantidadTransitos", vehiculo.getTransitos().size());
            vehMap.put("montoTotal", vehiculo.calculateMontoTotalGastado());
            vehiculos.add(vehMap);
        }

        List<Transito> transitosList = Fachada.getInstancia().getTransitosPorPropietario(propietario);
        transitosList.sort(Comparator.comparing(Transito::getFechaHora).reversed());
        List<Map<String, Object>> transitos = new ArrayList<>();
        for (Transito transito : transitosList) {
            Map<String, Object> transitoMap = new HashMap<>();
            transitoMap.put("puesto", transito.getPuesto().getNombre());
            transitoMap.put("matricula", transito.getVehiculo().getMatricula());
            transitoMap.put("categoria", transito.getVehiculo().getCategoria().getNombre());

            double montoTarifa = transito.getMontoACobrar() + transito.getMontoBonificacion();
            transitoMap.put("montoTarifa", montoTarifa);

            Bonificacion bonif = propietario.buscarBonificacionPorPuesto(transito.getPuesto());
            transitoMap.put("bonificacionNombre", bonif != null ? bonif.getNombre() : "");
            transitoMap.put("montoBonificacion", transito.getMontoBonificacion());
            transitoMap.put("montoPagado", transito.getMontoACobrar());
            transitoMap.put("fechaHora", transito.getFechaHora().toString());
            transitos.add(transitoMap);
        }

        List<Notificacion> notificacionesList = new ArrayList<>(propietario.getNotificaciones());
        notificacionesList.sort(Comparator.comparing(Notificacion::getFechaHora).reversed());
        List<Map<String, Object>> notificaciones = new ArrayList<>();
        for (Notificacion notif : notificacionesList) {
            Map<String, Object> notifMap = new HashMap<>();
            notifMap.put("fechaHora", notif.getFechaHora().toString());
            notifMap.put("mensaje", notif.getMensaje());
            notificaciones.add(notifMap);
        }

        return Respuesta.lista(
                new Respuesta("nombreCompleto", nombreCompleto),
                new Respuesta("estado", estado),
                new Respuesta("saldoActual", saldoActual),
                new Respuesta("bonificaciones", bonificaciones),
                new Respuesta("vehiculos", vehiculos),
                new Respuesta("transitos", transitos),
                new Respuesta("notificaciones", notificaciones));
    }

    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(HttpSession session) throws UsuarioException {
        Propietario propietario = (Propietario) session.getAttribute("usuarioLogueado");

        if (propietario.getNotificaciones().isEmpty()) {
            throw new UsuarioException("No hay notificaciones para borrar");
        }

        propietario.borrarNotificaciones();
        return Respuesta.lista(new Respuesta("notificacionesBorradas", "Notificaciones eliminadas exitosamente"));
    }

    private String obtenerNombreEstado(Propietario propietario) {
        if (propietario.getEstado().estaHabilitado()) {
            return "Habilitado";
        } else if (propietario.getEstado().estaDeshabilitado()) {
            return "Deshabilitado";
        } else if (propietario.getEstado().estaSuspendido()) {
            return "Suspendido";
        } else if (propietario.getEstado().estaPenalizado()) {
            return "Penalizado";
        }
        return "Desconocido";
    }

}
