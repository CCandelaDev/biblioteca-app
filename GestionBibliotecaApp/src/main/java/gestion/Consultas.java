package gestion;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import biblioteca.Libro;
import biblioteca.Prestamo;

public class Consultas {

	private Session session;
	private Scanner scanner;

	public Consultas(Session session) {
		this.session = session;
		this.scanner = new Scanner(System.in);

	}

	// Método para gestionar la opciones de consultas del menú
	public void menuConsultas() {

		int opcionConsultas = 0;
		while (opcionConsultas != 4) {
			mostrarMenuConsultas();
			try {
				opcionConsultas = scanner.nextInt();
				scanner.nextLine(); // Consumir caracter nueva linea
				switch (opcionConsultas) {
				case 1:
					librosPrestadosLector();
					break;
				case 2:
					librosDisponiblesParaPrestamo();
					break;
				case 3:
					historialPrestamosPorLector();
					break;
				case 4:
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
	 * Método para mostrar el menú de las consultas
	 */
	private void mostrarMenuConsultas() {
		System.out.println("---------------------------------");
		System.out.println("            CONSULTAS            ");
		System.out.println("---------------------------------");
		System.out.println("1. Libros prestados a un lector");
		System.out.println("2. Libros disponibles para préstamo");
		System.out.println("3. Historial de préstamos por lector");
		System.out.println("4. Volver al menú principal");
		System.out.print("Selecciona una opción: ");

	}

	/**
	 * Método para mostrar el historial de préstamos por lector a partir de su ID
	 */
	private void historialPrestamosPorLector() {
		System.out.println("Ingresa el ID del lector:");
		Long idLector = scanner.nextLong();
		scanner.nextLine(); // Limpiar Buffer
		
		// Consulta hql para obtener el historial de préstamos del lector
		String consulta = "FROM Prestamo WHERE lector.id = :idLector";
		Query<Prestamo> query = session.createQuery(consulta, Prestamo.class);
		query.setParameter("idLector", idLector);
		List<Prestamo> prestamos = query.getResultList(); //Obtenemos libros prestados 
		
		//Mostrar los resultados
		if (!prestamos.isEmpty()) {
			
			System.out.println("****Historial Prestamos****");
			for(Prestamo prestamo : prestamos) {
				System.out.println("Libro: " + prestamo.getLibro().getTitulo()
						+ ", Fecha Préstamo: " + prestamo.getFechaPrestamo()
						+ ", Fecha Devolución" + prestamo.getFechaDevolucion());
			}
			
		} else {
			System.out.println("El lector no tiene historial de préstamos.");
		}
	}

	/**
	 * Método para listar los libros disponibles para préstamo.
	 */
	private void librosDisponiblesParaPrestamo() {
		//Consulta hql para ver los libros disponibles
		String consulta = "FROM Libro WHERE disponible = true";
		Query<Libro> query = session.createQuery(consulta, Libro.class);
		List<Libro> libros = query.getResultList(); //Obtenemos lista libros disponibles
		
		//Mostrar los resultados
		System.out.println("****Libros Disponibles****");
		for(Libro libro : libros) {
			System.out.println(libro.getTitulo());
		}
	}

	/**
	 * Método para listar los libros prestados a un lector a partir de su ID
	 */
	private void librosPrestadosLector() {

		System.out.println("Ingresa el ID del lector:");
		Long idLector = scanner.nextLong();
		scanner.nextLine(); // Limpiar Buffer

		// Consulta hql para obtener los préstamos del lector que están activos
		String consulta = "FROM Prestamo WHERE lector.id = :idLector AND fechaDevolucion IS NULL";

		Query<Prestamo> query = session.createQuery(consulta, Prestamo.class);
		query.setParameter("idLector", idLector);// Filtrar resultados consulta por ID
		List<Prestamo> prestamos = query.getResultList(); // Obtenemos la lista

		// Para mostrar los resultados de libros prestados al lector
		if (!prestamos.isEmpty()) {

			System.out.println("*****Libros Prestados*****");
			for (Prestamo prestamo : prestamos) {
				System.out.println(prestamo.getLibro().getTitulo());
			}

		} else {
			System.out.println("El lector no tiene libros prestados actualmente...");
		}
	}

}
