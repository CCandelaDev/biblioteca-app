package gestion;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import biblioteca.Libro;

public class GestorLibros {
	
	private Session session;
	private Scanner scanner;
	
	public GestorLibros(Session session) {
		this.session = session;
		this.scanner = new Scanner(System.in);
		
	}
	
	//Menú para gestionar las operaciones con libros
	public void menuGestionLibros() {
        
        int opcionGestionLibros = 0;     
        while (opcionGestionLibros != 5) {
        	mostrarMenuGestionLibros();
            try {
				opcionGestionLibros = scanner.nextInt();
				scanner.nextLine(); //Consumir caracter nueva linea
				switch (opcionGestionLibros) {
				case 1:
					insertarLibro();
					break;
				case 2:
					listarLibros();
					break;
				case 3:
					System.out.println("Ingresa el ID del libro a actualizar: ");
					Long idActualizar = scanner.nextLong();
					scanner.nextLine();//Limpiar buffer
					actualizarLibro(idActualizar);
					break;
				case 4:
					System.out.println("Ingresa el ID del libro a borrar: ");
					Long idBorrar = scanner.nextLong();
					scanner.nextLine();//Limpiar buffer
					borrarLibro(idBorrar);
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
	 * Método para mostrar menú de gestión de libros
	 */
    private void mostrarMenuGestionLibros() {
        System.out.println("---------------------------------");
        System.out.println("       GESTIÓN DE LIBROS         ");
        System.out.println("---------------------------------");
        System.out.println("1. Insertar libro");
        System.out.println("2. Listar libros");
        System.out.println("3. Actualizar libro");
        System.out.println("4. Borrar libro");
        System.out.println("5. Volver al menú principal");
        System.out.print("Selecciona una opción: ");
    }
    
	
	/**
	 * Metodo para insertar libro en la base de datos
	 */
	public void insertarLibro() {
		
		Transaction transaction = null;
		try {
			
			//Solicitar datos del libro
			System.out.println("Introduce el título del libro: ");
			String titulo = scanner.nextLine();

			System.out.println("Introduce el autor del libro: ");
			String autor = scanner.nextLine();

			System.out.println("Introduce el año de publicación del libro: ");
			int anioPublicacion = Integer.parseInt(scanner.nextLine());
			
			Libro nuevoLibro = new Libro(titulo, autor, anioPublicacion, true);
			
			//Guardar libro en la base de datos
			transaction = session.beginTransaction();
			session.persist(nuevoLibro);
			transaction.commit(); //Confirmar transacción
			System.out.println("Libro guardado correctamente");
			
		} catch (Exception e) {
			//Rollback si ocurre error
			if(transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error al guardar el libro: " + e.getMessage());			
		}  
 
	}
	
	/**
	 * Metodo para listar los libros de la base de datos
	 */
	public void listarLibros() {
		
		String hql = "From Libro";
		Query<Libro> query = session.createQuery(hql, Libro.class); //Creo la consulta
		
		List<Libro> libros = query.getResultList(); //Obtengo la lista d libros
		
		//Imprimo la lista de libros(toString)
		System.out.println("******Lista de Libros******");
		for(Libro libro : libros) {
			System.out.println(libro.toString());
		}
		
	}
	
	/**
	 * Método para borrar libro po ID
	 * @param idLibro recibe el id del libro como parámetro
	 */
	public void borrarLibro(Long idLibro) {
		
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			//Obtengo id del libro a borrar.
			Libro libro = session.get(Libro.class, idLibro);
			
			if(libro != null) {
				//Borrar libro
				session.remove(libro);
				System.out.println("Libro borrado correctamente...");
			}else {
				System.out.println("No se encuentra ningún libro con este ID");
				return;
			}
			
			//Confirmo la transacción
			transaction.commit();
			
		} catch (Exception e) {
			//Sihay algún error se revierte la transacción
			if(transaction != null) {
				transaction.rollback(); 
			}
			System.out.println("Error: no se ha podido borrar el libro." + e.getMessage());
		}
	}
	
	/**
	 * Método para actualizar un libro en la base de datos
	 * @param idLibro idLibro recibe el id del libro como parámetro
	 */
	public void actualizarLibro(Long idLibro) {
		
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			//Obtengo libro con el id proporcionado
			Libro libro = session.get(Libro.class, idLibro);
			
			if (libro != null) {
				//Mostrar libro a actualizar
				System.out.println("Detalles libro a actualizar: ");
				System.out.println(libro.toString());
				
				//Solicitar los nuevos datos
				System.out.println("\nIngresa los nuevos datos del libro: ");
				
				System.out.print("Nuevo título: ");
				String nuevoTitulo = scanner.nextLine();
				
				System.out.print("Nuevo autor: ");
				String nuevoAutor = scanner.nextLine();
		
				System.out.print("Nuevo año de publicación: ");
				int nuevoAnio = Integer.parseInt(scanner.nextLine());
				
				//Actualizar con los nuevos datos
				libro.setTitulo(nuevoTitulo);
				libro.setAutor(nuevoAutor);
				libro.setAnioPublicacion(nuevoAnio);
				
				session.merge(libro); //Actualizar el libro en la BD.
				transaction.commit(); //Confirmar la transacción
				
				System.out.println("Libro actualizado...");
				//Mostrar libro actualizado
				System.out.println(libro.toString());
				
			} else {
				System.out.println("No existe ningún libro con este ID.");
			}
			
		} catch (Exception e) {
			if(transaction != null) {
				transaction.rollback();
			}
			System.out.println("Error: No se ha podido actualizar el libro..." + e.getMessage());
			
		}
		
	}
		

}
