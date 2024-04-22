package biblioteca;

import java.util.Date;

public class Prestamo {

	private Long id;
	private Date fechaPrestamo, fechaDevolucion;
	
	//Referencias a las entidades Libro y Lector
	private Libro libro;
	private Lector lector;
	
	//Constructor vacio
	public Prestamo() {
		
	}
	
	/**
	 * Constructor con atributos
	 * @param fechaPrestamo
	 * @param fechaDevolucion
	 * @param libro
	 * @param lector
	 */
	public Prestamo(Date fechaPrestamo, Date fechaDevolucion, Libro libro, Lector lector) {
		super();
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.libro = libro;
		this.lector = lector;
	}
	
	
	//Métodos setter y getter
	public Long getId() {
		return id;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public Libro getLibro() {
		return libro;
	}

	public Lector getLector() {
		return lector;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}
	
	
	//toString
	@Override
	public String toString() {
		return "Prestamo [id=" + id + ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucion=" + fechaDevolucion
				+ ", libro=" + libro + ", lector=" + lector + "]";
	}
		
	
}
