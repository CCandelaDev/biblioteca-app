package gestion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;

import org.junit.Test;

import biblioteca.Libro;
 

public class GestorLibrosTest {

    
    private Session HibernateUtil;

	@Test
    public void testInsertarLibro() {

		//Preparar (Arrange)
        Session session = HibernateUtil.getSessionFactory().openSession(); //instancia real de la sesión de Hibernate
        GestorLibros gestorLibros = new GestorLibros(session); // Creamos una instancia de GestorLibros con la sesión real
        
        //Se simula la entrada del usuario con los detalles del libro
        String entradaUsuario = "Título del libro\nAutor del libro\n2022\n";
        InputStream inputStream = new ByteArrayInputStream(entradaUsuario.getBytes());
        System.setIn(inputStream);

	    // Actuar(Act)
	    Transaction transaction = session.beginTransaction();
	    gestorLibros.insertarLibro();
	    transaction.commit();

        // Verificar(Assert)
        // Verifico si el libro se ha guardado correctamente en la base de datos
        Libro libro = session.createQuery("from Libro where titulo = :titulo", Libro.class)
                              .setParameter("titulo", "Título del libro")
                              .uniqueResult();
        Assert.assertNotNull(libro); // Verifico que el libro no sea nulo
        Assert.assertEquals("Título del libro", libro.getTitulo()); // Verifico el título del libro
        Assert.assertEquals("Autor del libro", libro.getAutor()); // Verifico el autor del libro
        Assert.assertEquals(2022, libro.getAnioPublicacion()); // Verifico el año de publicación del libro
    }

}
