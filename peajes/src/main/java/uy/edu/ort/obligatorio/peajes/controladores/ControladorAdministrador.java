package uy.edu.ort.obligatorio.peajes.controladores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionExonerados;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionFrecuentes;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionTrabajadores;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.dtos.PuestoDto;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioDeshabilitado;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioHabilitado;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioPenalizado;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioSuspendido;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.interfaces.EstadoPropietario;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("/admin")
public class ControladorAdministrador {

    @PostMapping("/login")
    public List<Respuesta> login(HttpSession session, @RequestParam String cedula, @RequestParam String contrasena)
            throws UsuarioException {
        Usuario usuarioLogueado = Fachada.getInstancia().login(cedula, contrasena);
        session.setAttribute("usuarioLogueado", usuarioLogueado);
        return Respuesta.lista(new Respuesta("loginExitoso", "menuAdmin.html"));
    }

    @PostMapping("/cargarPuestos")
    public List<Respuesta> cargarPuestos() {
        return Respuesta.lista(new Respuesta("puestos", PuestoDto.listaDtos(Fachada.getInstancia().getPuestos())));
    }

    @PostMapping("/cargarTarifas")
    public List<Respuesta> cargarTarifas(@RequestParam String puestoNombre) {
        for (Puesto puesto : Fachada.getInstancia().getPuestos()) {
            if (puesto.getNombre().equals(puestoNombre)) {
                List<Map<String, Object>> tarifas = new ArrayList<>();
                for (Tarifa tarifa : puesto.getTarifas()) {
                    Map<String, Object> tarifaMap = new HashMap<>();
                    tarifaMap.put("categoria", tarifa.getCategoria().getNombre());
                    tarifaMap.put("monto", tarifa.getMonto());
                    tarifas.add(tarifaMap);
                }
                return Respuesta.lista(new Respuesta("tarifas", tarifas));
            }
        }
        return Respuesta.lista(new Respuesta("tarifas", new ArrayList<>()));
    }

    @PostMapping("/emularTransito")
    public List<Respuesta> emularTransito(@RequestParam String matricula, @RequestParam String puestoNombre,
            @RequestParam String fechaHora) throws UsuarioException {
        Puesto puesto = null;
        for (Puesto p : Fachada.getInstancia().getPuestos()) {
            if (p.getNombre().equals(puestoNombre)) {
                puesto = p;
                break;
            }
        }

        LocalDateTime fecha = LocalDateTime.parse(fechaHora);
        Transito transito = Fachada.getInstancia().emularTransito(matricula, puesto, fecha);

        Propietario propietario = transito.getVehiculo().getPropietario();
        String estado = obtenerNombreEstado(propietario.getEstado());
        String categoria = transito.getVehiculo().getCategoria().getNombre();

        Bonificacion bonificacion = propietario.buscarBonificacionPorPuesto(puesto);
        String bonificacionNombre = bonificacion != null && transito.getMontoBonificacion() > 0
                ? bonificacion.getNombre()
                : "";

        return Respuesta.lista(
                new Respuesta("propietarioNombre", propietario.getNombreCompleto()),
                new Respuesta("estado", estado),
                new Respuesta("categoria", categoria),
                new Respuesta("bonificacion", bonificacionNombre),
                new Respuesta("costo", transito.getMontoACobrar()),
                new Respuesta("saldo", propietario.getSaldoActual()));
    }

    @PostMapping("/cargarBonificaciones")
    public List<Respuesta> cargarBonificaciones() {
        List<Map<String, String>> bonificaciones = new ArrayList<>();

        Map<String, String> exonerados = new HashMap<>();
        exonerados.put("tipo", "Exonerados");
        exonerados.put("nombre", "Exonerados");
        bonificaciones.add(exonerados);

        Map<String, String> frecuentes = new HashMap<>();
        frecuentes.put("tipo", "Frecuentes");
        frecuentes.put("nombre", "Bonificación para vehículos frecuentes");
        bonificaciones.add(frecuentes);

        Map<String, String> trabajadores = new HashMap<>();
        trabajadores.put("tipo", "Trabajadores");
        trabajadores.put("nombre", "Bonificación para trabajadores del peaje");
        bonificaciones.add(trabajadores);

        return Respuesta.lista(new Respuesta("bonificaciones", bonificaciones));
    }

    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws UsuarioException {
        Propietario propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new UsuarioException("no existe el propietario");
        }

        String estado = obtenerNombreEstado(propietario.getEstado());

        List<Map<String, String>> bonificaciones = new ArrayList<>();
        for (Bonificacion bonif : propietario.getBonificaciones()) {
            Map<String, String> bonifMap = new HashMap<>();
            bonifMap.put("bonificacion", bonif.getNombre());
            bonifMap.put("puesto", bonif.getPuesto().getNombre());
            bonificaciones.add(bonifMap);
        }

        return Respuesta.lista(
                new Respuesta("nombreCompleto", propietario.getNombreCompleto()),
                new Respuesta("estado", estado),
                new Respuesta("bonificaciones", bonificaciones));
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(@RequestParam String cedula, @RequestParam String bonificacionTipo,
            @RequestParam String puestoNombre) throws UsuarioException {
        Bonificacion bonificacion = null;
        if (bonificacionTipo.equals("Exonerados")) {
            bonificacion = new BonificacionExonerados();
        } else if (bonificacionTipo.equals("Frecuentes")) {
            bonificacion = new BonificacionFrecuentes();
        } else if (bonificacionTipo.equals("Trabajadores")) {
            bonificacion = new BonificacionTrabajadores();
        }

        Puesto puesto = null;
        for (Puesto p : Fachada.getInstancia().getPuestos()) {
            if (p.getNombre().equals(puestoNombre)) {
                puesto = p;
                break;
            }
        }

        Fachada.getInstancia().asignarBonificacion(cedula, bonificacion, puesto, LocalDateTime.now());
        return Respuesta.lista(new Respuesta("resultado", "Bonificación asignada exitosamente"));
    }

    @PostMapping("/cargarEstados")
    public List<Respuesta> cargarEstados() {
        List<Map<String, String>> estados = new ArrayList<>();

        Map<String, String> habilitado = new HashMap<>();
        habilitado.put("nombre", "Habilitado");
        estados.add(habilitado);

        Map<String, String> deshabilitado = new HashMap<>();
        deshabilitado.put("nombre", "Deshabilitado");
        estados.add(deshabilitado);

        Map<String, String> suspendido = new HashMap<>();
        suspendido.put("nombre", "Suspendido");
        estados.add(suspendido);

        Map<String, String> penalizado = new HashMap<>();
        penalizado.put("nombre", "Penalizado");
        estados.add(penalizado);

        return Respuesta.lista(new Respuesta("estados", estados));
    }

    @PostMapping("/cambiarEstado")
    public List<Respuesta> cambiarEstado(@RequestParam String cedula, @RequestParam String nuevoEstado)
            throws UsuarioException {
        EstadoPropietario estado = null;
        if (nuevoEstado.equals("Habilitado")) {
            estado = new EstadoPropietarioHabilitado();
        } else if (nuevoEstado.equals("Deshabilitado")) {
            estado = new EstadoPropietarioDeshabilitado();
        } else if (nuevoEstado.equals("Suspendido")) {
            estado = new EstadoPropietarioSuspendido();
        } else if (nuevoEstado.equals("Penalizado")) {
            estado = new EstadoPropietarioPenalizado();
        }

        Fachada.getInstancia().cambiarEstadoPropietario(cedula, estado);
        return Respuesta.lista(new Respuesta("resultado", "Estado cambiado exitosamente"));
    }

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession session) {
        Administrador admin = (Administrador) session.getAttribute("usuarioLogueado");
        if (admin != null) {
            admin.setEstaLogueado(false);
        }
        session.invalidate();
        return Respuesta.lista(new Respuesta("resultado", "Logout exitoso"));
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