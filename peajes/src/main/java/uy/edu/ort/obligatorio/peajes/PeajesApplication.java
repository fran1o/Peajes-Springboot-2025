package uy.edu.ort.obligatorio.peajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioDeshabilitado;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioHabilitado;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;

@SpringBootApplication
public class PeajesApplication {

	public static void main(String[] args) {
		cargarDatosDePrueba();
		SpringApplication.run(PeajesApplication.class, args);
	}

	private static void cargarDatosDePrueba() {
		Usuario admin1 = new Administrador("12345678", "Usuario Administrador", "admin.123");
		Usuario propietario1 = new Propietario("23456789", "Usuario Propietario", "prop.123", 500, 2000, new EstadoPropietarioHabilitado());
		Usuario propietario2 = new Propietario("34567890", "Usuario Propietario 2", "prop2.123", 300, 1500, new EstadoPropietarioDeshabilitado());
		
		Fachada.getInstancia().agregarUsuario(admin1);
		Fachada.getInstancia().agregarUsuario(propietario1);
		Fachada.getInstancia().agregarUsuario(propietario2);
		
	}

}
