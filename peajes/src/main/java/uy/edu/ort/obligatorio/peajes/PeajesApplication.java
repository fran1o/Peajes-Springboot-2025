package uy.edu.ort.obligatorio.peajes;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.Bonificacion;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionExonerados;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionFrecuentes;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionTrabajadores;
import uy.edu.ort.obligatorio.peajes.dominio.Categoria;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.Usuario;
import uy.edu.ort.obligatorio.peajes.dominio.Vehiculo;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioDeshabilitado;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioHabilitado;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioPenalizado;
import uy.edu.ort.obligatorio.peajes.estados.EstadoPropietarioSuspendido;
import uy.edu.ort.obligatorio.peajes.servicios.Fachada;

@SpringBootApplication
public class PeajesApplication {

	public static void main(String[] args) {
		cargarDatosDePrueba();
		SpringApplication.run(PeajesApplication.class, args);
	}

	private static void cargarDatosDePrueba() {
		// Categorias
		Categoria automovil = new Categoria("Automóvil");
		Categoria camion = new Categoria("Camión");
		Categoria motocicleta = new Categoria("Motocicleta");

		// Datos de prueba usuarios
		Usuario admin1 = new Administrador("12345678", "Usuario Administrador", "admin.123");
		Usuario admin2 = new Administrador("87654321", "Admin Secundario", "admin.456");
		Propietario propietario1 = new Propietario("23456789", "Usuario Propietario", "prop.123", 500, 2000,
				new EstadoPropietarioHabilitado());
		Propietario propietario2 = new Propietario("34567890", "Usuario Propietario 2", "prop2.123", 300, 1500,
				new EstadoPropietarioSuspendido());
		Propietario propietario3 = new Propietario("45678901", "Usuario Propietario 3", "prop3.123", 400, 1000,
				new EstadoPropietarioPenalizado());

		Fachada.getInstancia().agregarUsuario(admin1);
		Fachada.getInstancia().agregarUsuario(admin2);
		Fachada.getInstancia().agregarUsuario(propietario1);
		Fachada.getInstancia().agregarUsuario(propietario2);
		Fachada.getInstancia().agregarUsuario(propietario3);

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

		// Tarifas para cada puesto
		puesto1.agregarTarifa(new Tarifa(100, automovil));
		puesto1.agregarTarifa(new Tarifa(200, camion));
		puesto1.agregarTarifa(new Tarifa(50, motocicleta));

		puesto2.agregarTarifa(new Tarifa(120, automovil));
		puesto2.agregarTarifa(new Tarifa(240, camion));
		puesto2.agregarTarifa(new Tarifa(60, motocicleta));

		puesto3.agregarTarifa(new Tarifa(110, automovil));
		puesto3.agregarTarifa(new Tarifa(220, camion));
		puesto3.agregarTarifa(new Tarifa(55, motocicleta));

		puesto4.agregarTarifa(new Tarifa(130, automovil));
		puesto4.agregarTarifa(new Tarifa(260, camion));
		puesto4.agregarTarifa(new Tarifa(65, motocicleta));

		puesto5.agregarTarifa(new Tarifa(140, automovil));
		puesto5.agregarTarifa(new Tarifa(280, camion));
		puesto5.agregarTarifa(new Tarifa(70, motocicleta));

		puesto6.agregarTarifa(new Tarifa(150, automovil));
		puesto6.agregarTarifa(new Tarifa(300, camion));
		puesto6.agregarTarifa(new Tarifa(75, motocicleta));

		puesto7.agregarTarifa(new Tarifa(160, automovil));
		puesto7.agregarTarifa(new Tarifa(320, camion));
		puesto7.agregarTarifa(new Tarifa(80, motocicleta));

		puesto8.agregarTarifa(new Tarifa(170, automovil));
		puesto8.agregarTarifa(new Tarifa(340, camion));
		puesto8.agregarTarifa(new Tarifa(85, motocicleta));

		puesto9.agregarTarifa(new Tarifa(180, automovil));
		puesto9.agregarTarifa(new Tarifa(360, camion));
		puesto9.agregarTarifa(new Tarifa(90, motocicleta));

		puesto10.agregarTarifa(new Tarifa(190, automovil));
		puesto10.agregarTarifa(new Tarifa(380, camion));
		puesto10.agregarTarifa(new Tarifa(95, motocicleta));

		// Vehiculos
		Vehiculo vehiculo1 = new Vehiculo("ABC1234", "Toyota Corolla", "Blanco", automovil, propietario1);
		Vehiculo vehiculo2 = new Vehiculo("DEF5678", "Honda Civic", "Negro", automovil, propietario1);
		Vehiculo vehiculo3 = new Vehiculo("GHI9012", "Yamaha MT-07", "Rojo", motocicleta, propietario1);
		Vehiculo vehiculo4 = new Vehiculo("JKL3456", "Mercedes Actros", "Azul", camion, propietario2);
		Vehiculo vehiculo5 = new Vehiculo("MNO7890", "Ford Ranger", "Gris", automovil, propietario2);

		propietario1.agregarVehiculo(vehiculo1);
		propietario1.agregarVehiculo(vehiculo2);
		propietario1.agregarVehiculo(vehiculo3);
		propietario2.agregarVehiculo(vehiculo4);
		propietario2.agregarVehiculo(vehiculo5);

		// Bonificaciones
		Bonificacion bonifExonerados = new BonificacionExonerados();
		bonifExonerados.setPuesto(puesto1);
		bonifExonerados.setFechaAsignacion(LocalDateTime.now().minusDays(30));
		propietario1.agregarBonificacion(bonifExonerados);

		Bonificacion bonifFrecuentes = new BonificacionFrecuentes();
		bonifFrecuentes.setPuesto(puesto2);
		bonifFrecuentes.setFechaAsignacion(LocalDateTime.now().minusDays(15));
		propietario1.agregarBonificacion(bonifFrecuentes);

		Bonificacion bonifTrabajadores = new BonificacionTrabajadores();
		bonifTrabajadores.setPuesto(puesto3);
		bonifTrabajadores.setFechaAsignacion(LocalDateTime.now().minusDays(10));
		propietario1.agregarBonificacion(bonifTrabajadores);

	}

}
