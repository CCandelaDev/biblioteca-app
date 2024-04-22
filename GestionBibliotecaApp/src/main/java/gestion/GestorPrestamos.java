package gestion;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;

import biblioteca.Lector;
import biblioteca.Libro;
import biblioteca.Prestamo;

public class GestorPrestamos {

	private Session session;
	private Scanner scanner;

	public GestorPrestamos(Session session) {
		this.session = session;
		this.scanner = new Scanner(System.in);

	}

	// Menú para gestionar las operaciones de los prestamos
	public void menuGestionPrestamos() {

		int opcionGestionPrestamos = 0;
		while (opcionGestionPrestamos != 3) {
			mostrarMenuGestionPrestamos();
			try {
				opcionGestionPrestamos = scanner.nextInt();
				scanner.nextLine(); // Consumir caracter nueva linea
				switch (opcionGestionPrestamos) {
				case 1:
					asignarLibroLector();
					break;
				case 2:
					registrarDevolucion();
					break;
				case 3:
					System.out.println("Salir al menú principal...");
					break;
				default:
					System.out.println("Opción incorrecta. Por favor, seleccione una opción válida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Ingresa un número entero.");
				scanner.next(); // Limpiar la entrada incorrecta
			}
		}

	}

	/**
	 * Método para mostrar el menú de la gestión de los préstamos
	 */
	private void mostrarMenuGestionPrestamos() {
		System.out.println("---------------------------------");
		System.out.println("      GESTIÓN DE PRÉSTAMOS       ");
		System.out.println("---------------------------------");
		System.out.println("1. Asignar libro a lector");
		System.out.println("2. Registrar devolución");
		System.out.println("3. Volver al menú principal");
		System.out.print("Selecciona una opción: ");

	}

	/**
	 * Método que asigna un libro a un lector Comprueba que existe Libro y lector, y
	 * que está disponible
	 */
	private void asignarLibroLector() {

		Transaction transaction = null;
		try {
			// Pedir el id del libro a prestar
			System.out.println("Ingresa el ID del libro a prestar:");
			Long idLibro = scanner.nextLong();
			scanner.nextLine(); // Limpiar buffer

			// Pedir id del lector que recibe el préstamo
			System.out.println("Ingresa el ID del lector que recibe el préstamo:");
			Long idLector = scanner.nextLong();
			scanner.nextLine(); // Limpiar buffer

			// Obtengo libro y lector de la BD
			Libro libro = session.get(Libro.class, idLibro);
			Lector lector = session.get(Lector.class, idLector);

			// Comprobar que existe Libro, Lector y está disponible
			if (libro != null && lector != null && libro.isDisponible()) {

				// Se crea el objeto prestamo
				Prestamo prestamo = new Prestamo();
				prestamo.setLibro(libro);
				prestamo.setLector(lector);
				prestamo.setFechaPrestamo(new Date(System.currentTimeMillis())); // Establecer fecha actual prestamo

				// Actualizo disponibilidad del libro
				libro.setDisponible(false);

				// Guardo prestamo y actualizo disponibilidad de libro
				transaction = session.beginTransaction();
				session.persist(prestamo);
				session.merge(libro);
				transaction.commit(); // Confirmar transacción
				System.out.println("Prestamo completado con éxito...");

			} else {
				System.out.println("Libro no disponible o ID de libro/lector no válido.");
			}

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback(); // Rollback si ocurre error
			}
			System.out.println("Error: No se pudo realizar el préstamo" + e.getMessage());
		}

	}

	private void registrarDevolucion() {

		Transaction transaction = null;
		try {

			// Se solicita el ID del préstamo a devolver
			System.out.println("Ingresa el ID del prestamo a devolver:");
			Long idPrestamo = scanner.nextLong();
			scanner.nextLine(); // Limpiar Buffer

			// Obtengo prestamo de la BD
			Prestamo prestamo = session.get(Prestamo.class, idPrestamo);

			if (prestamo != null) {

				// Actualizo la fecha de devolución.
				prestamo.setFechaDevolucion(new java.sql.Date(System.currentTimeMillis())); // Fecha actual
				
				//Actualizo la disponibilidad del libro
				Libro libro = prestamo.getLibro();
				libro.setDisponible(true);

				// Guardo la actualización en la BD y hago el commit
				transaction = session.beginTransaction();
				session.merge(prestamo);
				transaction.commit();
				System.out.println("Devolución completada correctamente...");

			} else {
				System.out.println("No existe el préstamo con el ID proporcionado.");
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback(); // Rollback si ocurre error
			}
			System.out.println("Error: No se pudo realizar el préstamo" + e.getMessage());
		}
	}

}
