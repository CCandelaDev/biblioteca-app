package biblioteca;

public class Lector {

	private Long id;
	private String nombre, apellido, email;
	
	//Constructor vacio
	public Lector() {
		
	}

	/**
	 *Constructor con atributos.
	 * @param nombre
	 * @param apellido
	 * @param email
	 */
	public Lector(String nombre, String apellido, String email) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
	}
	
	
	//Métodos getter y setter.
	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//toString
	@Override
	public String toString() {
		return "Lector [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + "]";
	}
		
}
