package biblioteca;

public class Libro {
	
	private Long id;
	private String titulo, autor;
	private int anioPublicacion;
	private boolean disponible;
	
	//Contructor vacio
	public Libro() {
		
	}

	/**
	 * Constructor con atributos
	 * @param titulo
	 * @param autor
	 * @param anioPublicacion
	 * @param disponible
	 */
	public Libro(String titulo, String autor, int anioPublicacion, boolean disponible) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.anioPublicacion = anioPublicacion;
		this.disponible = disponible;
	}
	
	
	//Getters y setters
	public Long getId() {
		return id;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public int getAnioPublicacion() {
		return anioPublicacion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setAnioPublicacion(int anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	//toString
	@Override
	public String toString() {
		return "Libro [id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", anioPublicacion=" + anioPublicacion
				+ ", disponible=" + disponible + "]";
	}
	
	
	
	
	

}
