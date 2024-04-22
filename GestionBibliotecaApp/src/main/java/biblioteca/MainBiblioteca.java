package biblioteca;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import gestion.Consultas;
import gestion.GestorLectores;
import gestion.GestorLibros;
import gestion.GestorPrestamos;

public class MainBiblioteca {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Session session = null;

		try {
			System.out.println("INICIO DE LA APLICACIÓN...\n");
			Configuration cfgHibernate = new Configuration().configure();
			System.out.println("SE HA CARGADO LA CONFIGURACIÓN...");

			// Instancia de SessionFactory en nuestra sesión
			SessionFactory sessionFactory = cfgHibernate
					.buildSessionFactory(new StandardServiceRegistryBuilder().configure().build());
			session = sessionFactory.openSession();// Abrimos la sesión
			System.out.println("CONFIGURACION REALIZADA...");

			int opcionPrincipal;
			do {

				mostrarMenuPrincipal();

				try {
					opcionPrincipal = scanner.nextInt();
					scanner.nextLine(); // Consumir caracter nueva linea
				} catch (InputMismatchException e) {
					System.out.println("Error: Ingresa un número entero.");
					scanner.nextLine(); // Limpiar la entrada incorrecta
					opcionPrincipal = 0; // Asignar valor
					continue; // Volver a empezar el bucle
				}
				
				switch (opcionPrincipal) {
				case 1:
					GestorLibros gestionLibros = new GestorLibros(session);
					gestionLibros.menuGestionLibros();
					break;
				case 2:
					GestorLectores gestionLectores = new GestorLectores(session);
					gestionLectores.menuGestionLectores();
					break;
				case 3:
					GestorPrestamos gestionPrestamos = new GestorPrestamos(session);
					gestionPrestamos.menuGestionPrestamos();
					break;
				case 4:
					Consultas consultas = new Consultas(session);
					consultas.menuConsultas();
					break;
				case 5:
					System.out.println("Saliendo de la aplicación...");
					System.exit(0);
					break;

				default:
					System.out.println("opción incorrecta. Ingresa una opción válida");
				}

			} while (opcionPrincipal != 5); //Salir del bucle
		}

		finally {
			if (session != null) {
				session.close(); // Cerramos la sesión
			}
			scanner.close();

		}

	}

	// Metodo para mostrar menú principal
	private static void mostrarMenuPrincipal() {
		System.out.println("---------------------------------");
		System.out.println("           BIBLIOTECA            ");
		System.out.println("---------------------------------");
		System.out.println("1. Gestión de Libros");
		System.out.println("2. Gestión de Lectores");
		System.out.println("3. Gestión de Prestamos");
		System.out.println("4. Consultas");
		System.out.println("5. Salir");
		System.out.print("Selecciona una opción: ");

	}

}
