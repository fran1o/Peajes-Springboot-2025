package uy.edu.ort.obligatorio.peajes.controladores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dtos.BonificacionDto;
import uy.edu.ort.obligatorio.peajes.dtos.PuestoDto;
import uy.edu.ort.obligatorio.peajes.dtos.TarifaDto;
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

    @PostMapping("/cargarPuestos")
    public List<Respuesta> cargarPuestos() {

        return Respuesta.lista(puestos());
    }

    private Respuesta puestos() {
        List<PuestoDto> puestos = PuestoDto.listaDtos(Fachada.getInstancia().getPuestos());
        return new Respuesta("puestos", puestos);
    }

    @PostMapping("/cargarTarifas")
    public List<Respuesta> cargarTarifas(@RequestParam String puestoNombre) {
        Puesto puesto = Fachada.getInstancia().getPuestoPorNombre(puestoNombre);
        List<TarifaDto> tarifas = TarifaDto.listaDtos(puesto.getTarifas());

        if (tarifas.isEmpty()) {
            return Respuesta.lista(new Respuesta("tarifas", "No se encontraron tarifas para el puesto seleccionado"));
        } else {
            return Respuesta.lista(new Respuesta("tarifas", tarifas));
        }
    }

    @PostMapping("/emularTransito")
    public List<Respuesta> emularTransito(@RequestParam String matricula, @RequestParam String puestoNombre,
            @RequestParam String fechaHora) throws UsuarioException {
        Puesto puesto = null;
        for (Puesto p : Fachada.getInstancia().getPuestos()) {
            if (p.getNombre().trim().equalsIgnoreCase(puestoNombre.trim())) {
                puesto = p;
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fecha = LocalDateTime.parse(fechaHora, formatter);
        Transito transito = Fachada.getInstancia().emularTransito(matricula, puesto, fecha);

        Propietario propietario = transito.getVehiculo().getPropietario();
        String estado = propietario.getEstado().getNombreEstado();
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

    @PostMapping("/cargarBonificacionesYPuestos")
    public List<Respuesta> cargarBonificaciones() {
        List<BonificacionDto> bonificaciones = BonificacionDto.listaDtos(Fachada.getInstancia().getBonificaciones());
        List<PuestoDto> puestos = PuestoDto.listaDtos(Fachada.getInstancia().getPuestos());

        return Respuesta.lista(
                new Respuesta("bonificaciones", bonificaciones),
                new Respuesta("puestos", puestos));
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(@RequestParam String cedula, @RequestParam String bonificacionNombre,
            @RequestParam String puestoNombre) throws UsuarioException {

        Bonificacion bonificacion = buscarBonificacionPorNombre(bonificacionNombre);
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

    private Bonificacion buscarBonificacionPorNombre(String bonificacionNombre) {
        for (Bonificacion b : Fachada.getInstancia().getBonificaciones()) {
            if (b.getNombre().equals(bonificacionNombre)) {
                return b;
            }
        }
        return null;
    }

    @PostMapping("/buscarPropietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws UsuarioException {
        Propietario propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new UsuarioException("No existe el propietario");
        }

        String estado = propietario.getEstado().getNombreEstado();
        List<BonificacionDto> bonificacionesDeProp = BonificacionDto.listaDtos(propietario.getBonificaciones());

        return Respuesta.lista(
                new Respuesta("nombreCompleto", propietario.getNombreCompleto()),
                new Respuesta("estado", estado),
                new Respuesta("bonificacionesDeProp", bonificacionesDeProp));
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

}