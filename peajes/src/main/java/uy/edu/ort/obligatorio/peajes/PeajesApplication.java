package uy.edu.ort.obligatorio.peajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
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
		// Datos de prueba usuarios
		Usuario admin1 = new Administrador("12345678", "Usuario Administrador", "admin.123");
		Usuario admin2 = new Administrador("23456789", "Usuario Administrador", "admin.123");
		Usuario propietario1 = new Propietario("23456789", "Usuario Propietario", "prop.123", 500, 2000, new EstadoPropietarioHabilitado());
		Usuario propietario2 = new Propietario("34567890", "Usuario Propietario 2", "prop2.123", 300, 1500, new EstadoPropietarioDeshabilitado());
		
		Fachada.getInstancia().agregarUsuario(admin1);
		Fachada.getInstancia().agregarUsuario(admin2);
		Fachada.getInstancia().agregarUsuario(propietario1);
		Fachada.getInstancia().agregarUsuario(propietario2);

		// Datos de prueba puestos
		Puesto puesto1 = new Puesto("Puesto 101", "Acceso Norte");
		Fachada.getInstancia().agregarPuesto(puesto1);

		Puesto puesto2 = new Puesto("Puesto 102", "Ruta Interbalnearia km 45");
		Fachada.getInstancia().agregarPuesto(puesto2);

		Puesto puesto3 = new Puesto("Puesto 103", "Ruta 5 - Canelones");
		Fachada.getInstancia().agregarPuesto(puesto3);

		Puesto puesto4 = new Puesto("Puesto 104", "Ruta 3 - San José");
		Fachada.getInstancia().agregarPuesto(puesto4);

		Puesto puesto5 = new Puesto("Puesto 105", "Ruta 8 - Minas");
		Fachada.getInstancia().agregarPuesto(puesto5);

		Puesto puesto6 = new Puesto("Puesto 106", "Ruta 9 - Rocha");
		Fachada.getInstancia().agregarPuesto(puesto6);

		Puesto puesto7 = new Puesto("Puesto 107", "Ruta 1 - Colonia");
		Fachada.getInstancia().agregarPuesto(puesto7);

		Puesto puesto8 = new Puesto("Puesto 108", "Ruta 2 - Mercedes");
		Fachada.getInstancia().agregarPuesto(puesto8);

		Puesto puesto9 = new Puesto("Puesto 109", "Ruta 7 - Florida");
		Fachada.getInstancia().agregarPuesto(puesto9);

		Puesto puesto10 = new Puesto("Puesto 110", "Ruta 5 - Rivera");
		Fachada.getInstancia().agregarPuesto(puesto10);
		
	}

}
