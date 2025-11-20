package uy.edu.ort.obligatorio.peajes;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;

import uy.edu.ort.obligatorio.peajes.dominio.Administrador;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionExonerados;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionFrecuentes;
import uy.edu.ort.obligatorio.peajes.dominio.BonificacionTrabajadores;
import uy.edu.ort.obligatorio.peajes.dominio.Categoria;
import uy.edu.ort.obligatorio.peajes.dominio.Propietario;
import uy.edu.ort.obligatorio.peajes.dominio.Puesto;
import uy.edu.ort.obligatorio.peajes.dominio.Tarifa;
import uy.edu.ort.obligatorio.peajes.dominio.TipoBonificacion;
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
		Administrador admin1 = new Administrador("12345678", "Usuario Administrador", "admin.123");
		Administrador admin2 = new Administrador("87654321", "Admin Secundario", "admin.456");
		Propietario propietario1 = new Propietario("23456789", "Usuario Propietario 1", "prop.123", 500, 1000,
				new EstadoPropietarioHabilitado());
		Propietario propietario2 = new Propietario("34567890", "Usuario Propietario 2", "prop2.123", 300, 1000,
				new EstadoPropietarioSuspendido());
		Propietario propietario3 = new Propietario("45678901", "Usuario Propietario 3", "prop3.123", 400, 1000,
				new EstadoPropietarioPenalizado());
		Propietario propietario4 = new Propietario("49429904", "Usuario Propietario 4", "prop4.123", 400, 1000,
				new EstadoPropietarioDeshabilitado());

				Propietario propietario5 = new Propietario("98765432", "Usuario Propietario 5", "prop5.123", 400, 15,
				new EstadoPropietarioHabilitado());

		Fachada.getInstancia().agregarAdministrador(admin1);
		Fachada.getInstancia().agregarAdministrador(admin2);
		Fachada.getInstancia().agregarPropietario(propietario1);
		Fachada.getInstancia().agregarPropietario(propietario2);
		Fachada.getInstancia().agregarPropietario(propietario3);
		Fachada.getInstancia().agregarPropietario(propietario4);
		Fachada.getInstancia().agregarPropietario(propietario5);

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
		puesto1.agregarTarifa(new Tarifa( automovil, 100));
		puesto1.agregarTarifa(new Tarifa(camion, 200));
		puesto1.agregarTarifa(new Tarifa(motocicleta, 50));

		puesto2.agregarTarifa(new Tarifa(automovil,120));
		puesto2.agregarTarifa(new Tarifa(camion, 240));
		puesto2.agregarTarifa(new Tarifa(motocicleta, 60));

		puesto3.agregarTarifa(new Tarifa(automovil, 110));
		puesto3.agregarTarifa(new Tarifa(camion, 220));
		puesto3.agregarTarifa(new Tarifa(motocicleta, 55));

		puesto4.agregarTarifa(new Tarifa(automovil, 130));
		puesto4.agregarTarifa(new Tarifa(camion, 260));
		puesto4.agregarTarifa(new Tarifa(motocicleta, 65));

		puesto5.agregarTarifa(new Tarifa(automovil,140));
		puesto5.agregarTarifa(new Tarifa(camion, 280));
		puesto5.agregarTarifa(new Tarifa(motocicleta, 70));

		puesto6.agregarTarifa(new Tarifa(automovil,150));
		puesto6.agregarTarifa(new Tarifa(camion,300));
		puesto6.agregarTarifa(new Tarifa(motocicleta,75));

		puesto7.agregarTarifa(new Tarifa(automovil,160));
		puesto7.agregarTarifa(new Tarifa(camion, 320));
		puesto7.agregarTarifa(new Tarifa(motocicleta, 80));

		puesto8.agregarTarifa(new Tarifa(automovil, 170));
		puesto8.agregarTarifa(new Tarifa(camion, 340));
		puesto8.agregarTarifa(new Tarifa(motocicleta, 85));

		puesto9.agregarTarifa(new Tarifa(automovil, 180));
		puesto9.agregarTarifa(new Tarifa(camion, 360));
		puesto9.agregarTarifa(new Tarifa(motocicleta, 90));

		puesto10.agregarTarifa(new Tarifa(automovil, 190));
		puesto10.agregarTarifa(new Tarifa(camion, 380));
		puesto10.agregarTarifa(new Tarifa(motocicleta, 95));

		// Vehiculos
		Vehiculo vehiculo1 = new Vehiculo("ABC1234", "Toyota Corolla", "Blanco", automovil, propietario1);
		Vehiculo vehiculo2 = new Vehiculo("DEF5678", "Honda Civic", "Negro", automovil, propietario1);
		Vehiculo vehiculo3 = new Vehiculo("GHI9012", "Yamaha MT-07", "Rojo", motocicleta, propietario1);
		Vehiculo vehiculo4 = new Vehiculo("JKL3456", "Mercedes Actros", "Azul", camion, propietario2);
		Vehiculo vehiculo5 = new Vehiculo("MNO7890", "Ford Ranger", "Gris", automovil, propietario2);
		Vehiculo vehiculo6 = new Vehiculo("UYU1234", "Fiat 147", "Azul", automovil, propietario3);
		Vehiculo vehiculo7 = new Vehiculo("ARG1234", "Fiat Uno Sport", "Blanco", automovil, propietario4);
		Vehiculo vehiculo8 = new Vehiculo("AAA1234", "Nissan Tida", "Negro", automovil, propietario5);
		Vehiculo vehiculo9 = new Vehiculo("BBB1234", "Zanella", "Gris", motocicleta, propietario5);

		propietario1.agregarVehiculo(vehiculo1);
		propietario1.agregarVehiculo(vehiculo2);
		propietario1.agregarVehiculo(vehiculo3);
		propietario2.agregarVehiculo(vehiculo4);
		propietario2.agregarVehiculo(vehiculo5);
		propietario3.agregarVehiculo(vehiculo6);
		propietario4.agregarVehiculo(vehiculo7);
		propietario5.agregarVehiculo(vehiculo8);
		propietario5.agregarVehiculo(vehiculo9);

		// Bonificaciones
		TipoBonificacion bonifExonerados = new BonificacionExonerados();
		Fachada.getInstancia().agregarBonificacion(bonifExonerados);

		TipoBonificacion bonifFrecuentes = new BonificacionFrecuentes();
		Fachada.getInstancia().agregarBonificacion(bonifFrecuentes);

		TipoBonificacion bonifTrabajadores = new BonificacionTrabajadores();
		Fachada.getInstancia().agregarBonificacion(bonifTrabajadores);


	}

}
