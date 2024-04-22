package gestion;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import biblioteca.Lector;

public class GestorLectores {

	private Session session;
	private Scanner scanner;

	public GestorLectores(Session session) {
		this.session = session;
		this.scanner = new Scanner(System.in);

	}

	// Menú para gestionar las operaciones con lectores
	public void menuGestionLectores() {

		int opcionGestionLibros = 0;
		while (opcionGestionLibros != 5) {
			mostrarMenuGestionLectores();
			try {
				opcionGestionLibros = scanner.nextInt();
				scanner.nextLine(); // Consumir caracter nueva linea
				switch (opcionGestionLibros) {
				case 1:
					insertarLector();
					break;
				case 2:
					listarLectores();
					break;
				case 3:
					System.out.println("Ingresa el ID del lector a actualizar: ");
					Long idActualizar = scanner.nextLong();
					scanner.nextLine();// Limpiar buffer
					actualizarLector(idActualizar);
					break;
				case 4:
					System.out.println("Ingresa el ID del lector a borrar: ");
					Long idBorrar = scanner.nextLong();
					scanner.nextLine();// Limpiar buffer
					borrarLector(idBorrar);
					break;
				case 5:
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
	 * Método para mostrar menú de gestión de lectores
	 */
	private void mostrarMenuGestionLectores() {
		System.out.println("---------------------------------");
		System.out.println("       GESTIÓN DE LECTORES       ");
		System.out.println("---------------------------------");
		System.out.println("1. Insertar lector");
		System.out.println("2. Listar lectores");
		System.out.println("3. Actualizar lector");
		System.out.println("4. Borrar lector");
		System.out.println("5. Volver al menú principal");
		System.out.print("Selecciona una opción: ");

	}

	/**
	 * Método para insertar un nuevo lector.
	 */
	private void insertarLector() {

		Transaction transaction = null;
		try {

			// Solicitar datos del libro
			System.out.println("Introduce el nombre:");
			String nombre = scanner.nextLine();

			System.out.println("Introduce el apellido:");
			String apellido = scanner.nextLine();

			System.out.println("Introduce el email:");
			String email = scanner.nextLine();

			Lector nuevoLector = new Lector(nombre, apellido, email);

			// Guardar libro en la base de datos
			transaction = session.beginTransaction();
			session.persist(nuevoLector);
			transaction.commit(); // Confirmar transacción
			System.out.println("Lector guardado correctamente");

		} catch (Exception e) {
			// Rollback si ocurre error
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error al guardar el lector: " + e.getMessage());
		}

	}

	/**
	 * Método para listar los lectores de la base de datos
	 */
	public void listarLectores() {

		String hql = "From Lector";
		Query<Lector> query = session.createQuery(hql, Lector.class); // Creo la consulta

		List<Lector> lectores = query.getResultList(); // Obtengo la lista de lectores

		// Imprimo la lista de lectores(toString)
		System.out.println("******Lista de Libros******");
		for (Lector lector : lectores) {
			System.out.println(lector.toString());
		}

	}

	/**
	 * Método para borrar lector po ID
	 * 
	 * @param idLector recibe el id del lector como parámetro
	 */
	public void borrarLector(Long idLector) {

		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			// Obtengo id del lector a borrar.
			Lector lector = session.get(Lector.class, idLector);

			if (lector != null) {
				// Borrar lector
				session.remove(lector);
				System.out.println("Lector borrado correctamente...");
			} else {
				System.out.println("No se encuentra ningún lector con este ID");
				return;
			}

			// Confirmo la transacción
			transaction.commit();

		} catch (Exception e) {
			// Si hay algún error se revierte la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error: no se ha podido borrar el lector." + e.getMessage());
		}
	}

	/**
	 * Método para actualizar un lector en la base de datos
	 * 
	 * @param idLector recibe el id del lector como parámetro
	 */
	public void actualizarLector(Long idLector) {

		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			// Obtengo lector con el id proporcionado
			Lector lector = session.get(Lector.class, idLector);

			if (lector != null) {
				// Mostrar lector a actualizar
				System.out.println("Detalles libro a actualizar:");
				System.out.println(lector.toString());

				// Solicitar los nuevos datos
				System.out.println("\nIngresa los nuevos datos del lector:");

				System.out.print("Nuevo nombre: ");
				String nuevoNombre = scanner.nextLine();

				System.out.print("Nuevo apellido: ");
				String nuevoApellido = scanner.nextLine();

				System.out.print("Nuevo email: ");
				String nuevoEmail = scanner.nextLine();

				// Actualizar con los nuevos datos
				lector.setNombre(nuevoNombre);
				lector.setApellido(nuevoApellido);
				lector.setEmail(nuevoEmail);

				session.merge(lector); // Actualizar el lector en la BD.
				transaction.commit(); // Confirmar la transacción

				System.out.println("Lector actualizado...");
				// Mostrar lector actualizado
				System.out.println(lector.toString());

			} else {
				System.out.println("No existe ningún lector con este ID.");
			}

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error: No se ha podido actualizar el lector..." + e.getMessage());

		}
	}

}
