package uy.edu.ort.obligatorio.peajes.controladores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Transito;
import uy.edu.ort.obligatorio.peajes.dtos.PuestoDto;
import uy.edu.ort.obligatorio.peajes.dtos.TarifaDto;
import uy.edu.ort.obligatorio.peajes.excepciones.UsuarioException;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;
import uy.edu.ort.obligatorio.peajes.utils.Respuesta;

@RestController
@RequestMapping("admin/emular")
public class ControladorEmularTransito {
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
        List<PuestoDto> puestos = PuestoDto.listaDtos(Fachada.getInstancia().getPuestos());
        for (PuestoDto puesto : puestos) {
            if (puesto.getNombre().equals(puestoNombre)) {
                List<TarifaDto> tarifas = TarifaDto.listaDtos(Fachada.getInstancia().getPuestoPorNombre(puestoNombre).getTarifas());
                return Respuesta.lista(new Respuesta("tarifas", tarifas));
            }
        }

        return Respuesta.lista(new Respuesta("puestos", "No se encontraron tarifas para el puesto seleccionado"));
    } 

    @PostMapping("/emularTransito")
    public List<Respuesta> emularTransito(@RequestParam String matricula, @RequestParam String puestoNombre, @RequestParam String fechaHora) throws UsuarioException {
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

        Bonificacion bonificacionPropietarioPuesto = propietario.buscarBonificacionPorPuesto(puesto);
        String bonificacionNombre = bonificacionPropietarioPuesto != null && transito.getMontoBonificacion() > 0
                ? bonificacionPropietarioPuesto.getTipoBonificacion().getNombre()
                : "";

        return Respuesta.lista(new Respuesta("mensaje", "Transito realizado con exito"),
                new Respuesta("propietarioNombre", propietario.getNombreCompleto()),
                new Respuesta("estado", estado),
                new Respuesta("categoria", categoria),
                new Respuesta("bonificacion", bonificacionNombre),
                new Respuesta("costo", transito.getMontoACobrar()),
                new Respuesta("saldo", propietario.getSaldoActual()));
    }

}
